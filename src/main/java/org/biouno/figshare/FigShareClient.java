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

	// constants
	private static final String VERSION_PREFIX = "v";
	private static final String FORWARD_SLASH = "/";

	// parameters
	private final String endpoint;
	private final int version;
	private final OAuthConsumer consumer;

	/**
	 * Internal constructor.
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
	        HttpEntity entity = response.getEntity();
	        String json = EntityUtils.toString(entity);
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
	 * Gets the 
	 * @param json
	 * @return
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

}
