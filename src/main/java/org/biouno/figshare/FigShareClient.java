/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 The BioUno team, Bruno P. Kinoshita
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.biouno.figshare;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.biouno.figshare.v1.model.Article;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The FigShare client. You probably need to use only this class in your program
 * to interface with FigShare API, and the models.
 *
 * <p>#Thread-safe#</p>
 *
 * @since 0.1
 */
public final class FigShareClient {

	private static final String JSON_CONTENT_TYPE = "application/json";
	// constants
	private static final String VERSION_PREFIX = "v";
	private static final String FORWARD_SLASH = "/";

	// parameters
	/**
	 * API endpoint (e.g. http://api.figshare.com/)
	 */
	private final String endpoint;
	/**
	 * API version (e.g. 1)
	 */
	private final int version;
	/**
	 * OAuth consumer, used to sign requests to the API.
	 */
	private final OAuthConsumer consumer;

	/**
	 * Internal constructor.
	 *
	 * @param endpoint URL to the service
	 * @param version API version
	 * @param clientKey consumer key
	 * @param clientSecret consumer secret
	 * @param tokenKey token key
	 * @param tokenSecret token secret
	 */
	FigShareClient(String endpoint, int version, String clientKey, String clientSecret, String tokenKey,
			String tokenSecret) {
		this.endpoint = endpoint;
		this.version = version;
		// create a consumer object and configure it with the access
        // token and token secret obtained from the service provider
		consumer = new CommonsHttpOAuthConsumer(clientKey, clientSecret);
		consumer.setTokenWithSecret(tokenKey, tokenSecret);
	}

	/**
	 * @return the endpoint URL
	 */
	public String getEndpoint() {
		return endpoint;
	}

	/**
	 * @return the API version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Create the FigShare API URL using the endpoint, version and API method.
	 *
	 * @param endpoint endpoint URL
	 * @param version API version
	 * @param method API operation
	 * @return the URL with the version and method
	 */
	private String getURL(String endpoint, int version, String method) {
		StringBuilder sb = new StringBuilder();
		sb.append(endpoint);
		if (!endpoint.endsWith(FORWARD_SLASH)) {
			sb.append(FORWARD_SLASH);
		}
		sb.append(VERSION_PREFIX + version + FORWARD_SLASH + method);
		return sb.toString();
	}

	/**
	 * Create a FigShareClient to interface to the FigShare API.
	 *
	 * @param endpoint API endpoint
	 * @param version API version
	 * @param clientKey OAuth client key
	 * @param clientSecret OAuth client secret
	 * @param tokenKey OAuth token key
	 * @param tokenSecret OAuth token secret
	 * @return a {@link FigShareClient}
	 */
	public static FigShareClient to(String endpoint, int version, String clientKey, String clientSecret,
			String tokenKey, String tokenSecret) {
		return new FigShareClient(endpoint, version, clientKey, clientSecret, tokenKey, tokenSecret);
	}

	// --- API methods
	
	/**
	 * Get full listing/details of their articles.
	 *
	 * @return List of {@link Article}s
	 * @throws FigShareClientException
	 */
	public List<Article> articles() throws FigShareClientException {
		HttpClient httpClient = null;
		try {
			final String method = "my_data/articles";
			// create an HTTP request to a protected resource
			final String url = getURL(endpoint, version, method);
			// create an HTTP request to a protected resource
	        final HttpGet request = new HttpGet(url);

			// sign the request
	        consumer.sign(request);

	        // send the request
	        httpClient = HttpClientBuilder.create().build();
	        HttpResponse response = httpClient.execute(request);
	        HttpEntity responseEntity = response.getEntity();
	        String json = EntityUtils.toString(responseEntity);
	        List<Article> articles = readArticlesFromJson(json);
	        return Collections.unmodifiableList(articles);
		} catch (OAuthCommunicationException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (OAuthMessageSignerException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (OAuthExpectationFailedException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (ClientProtocolException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		}
	}

	/**
	 * Get the articles objects from JSON.
	 *
	 * @param json JSON
	 * @return articles objects
	 */
	protected List<Article> readArticlesFromJson(String json) {
		Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject array = parser.parse(json).getAsJsonObject();
        JsonElement items = array.get("items");
        JsonArray itemsArray = items.getAsJsonArray();
        List<Article> articles = new LinkedList<>();
        for (JsonElement element : itemsArray) {
        	Article article = gson.fromJson(element, Article.class);
        	articles.add(article);
        }
        return articles;
	}

	/**
	 * Create an article.
	 *
	 * @param title title
	 * @param description description 
	 * @param definedType defined type (e.g. dataset)
	 * @return an {@link Article}
	 */
	public Article createArticle(final String title, final String description, final String definedType) {
		HttpClient httpClient = null;
		try {
			final String method = "my_data/articles";
			// create an HTTP request to a protected resource
			final String url = getURL(endpoint, version, method);
			// create an HTTP request to a protected resource
	        final HttpPost request = new HttpPost(url);
	        Gson gson = new Gson();
	        JsonObject payload = new JsonObject();
	        payload.addProperty("title", title);
	        payload.addProperty("description", description);
	        payload.addProperty("defined_type", definedType);
	        String jsonRequest = gson.toJson(payload);
	        StringEntity entity = new StringEntity(jsonRequest);
	        entity.setContentType(JSON_CONTENT_TYPE);
			request.setEntity(entity);

			// sign the request
	        consumer.sign(request);

	        // send the request
	        httpClient = HttpClientBuilder.create().build();
	        HttpResponse response = httpClient.execute(request);
	        HttpEntity responseEntity = response.getEntity();
	        String json = EntityUtils.toString(responseEntity);
	        Article article = readArticleFromJson(json);
	        return article;
		} catch (OAuthCommunicationException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (OAuthMessageSignerException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (OAuthExpectationFailedException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (ClientProtocolException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		}
	}

	/**
	 * Get the article object from JSON.
	 *
	 * @param json JSON
	 * @return article object
	 */
	protected Article readArticleFromJson(String json) {
		Gson gson = new Gson();
        Article article = gson.fromJson(json, Article.class);
        return article;
	}

	/**
	 * Upload a file to an article.
	 *
	 * @param articleId article ID
	 * @param file java.io.File file
	 * @return org.biouno.figshare.v1.model.File uploaded file, without the thumbnail URL
	 */
	public org.biouno.figshare.v1.model.File uploadFile(long articleId, File file) {
		HttpClient httpClient = null;
		try {
			final String method = String.format("my_data/articles/%d/files", articleId);
			// create an HTTP request to a protected resource
			final String url = getURL(endpoint, version, method);
			// create an HTTP request to a protected resource
			final HttpPut request = new HttpPut(url);

			MultipartEntityBuilder builder  = MultipartEntityBuilder.create();
        	ContentBody body = new FileBody(file);
        	FormBodyPart part = FormBodyPartBuilder.create("filedata", body).build();
        	builder.addPart(part);

	        HttpEntity entity = builder.build();
			request.setEntity(entity);

			// sign the request
	        consumer.sign(request);

	        // send the request
	        httpClient = HttpClientBuilder.create().build();
	        HttpResponse response = httpClient.execute(request);
	        HttpEntity responseEntity = response.getEntity();
	        String json = EntityUtils.toString(responseEntity);
	        org.biouno.figshare.v1.model.File uploadedFile = readFileFromJson(json);
	        return uploadedFile;
		} catch (OAuthCommunicationException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (OAuthMessageSignerException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (OAuthExpectationFailedException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (ClientProtocolException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new FigShareClientException("Failed to get articles: " + e.getMessage(), e);
		}
	}

	/**
	 * Get a file from a JSON.
	 *
	 * @param json JSON
	 * @return a file
	 */
	protected org.biouno.figshare.v1.model.File readFileFromJson(String json) {
		Gson gson = new Gson();
		org.biouno.figshare.v1.model.File file = gson.fromJson(json, org.biouno.figshare.v1.model.File.class);
        return file;
	}

}
