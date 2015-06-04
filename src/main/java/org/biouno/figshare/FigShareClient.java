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

import com.google.api.client.auth.oauth.OAuthParameters;

/**
 * The FigShare client. You probably need to use only this class in your program
 * to interface with FigShare API. The other classes are for internal use.
 *
 * @since 0.1
 */
public final class FigShareClient {

	private final String endpoint;
	private final int version;
	private final OAuthParameters oauthParameters;

	FigShareClient(String endpoint, int version, String clientKey, String clientSecret, String tokenKey,
			String tokenSecret) {
		this.endpoint = endpoint;
		this.version = version;
		oauthParameters = new OAuthParameters();
		oauthParameters.consumerKey = clientKey;
		oauthParameters.nonce = clientSecret;
		oauthParameters.token = tokenKey;
		oauthParameters.signature = tokenSecret;
		oauthParameters.signatureMethod = "AUTH_HEADER";
	}

	public String getEndpoint() {
		return endpoint;
	}

	public int getVersion() {
		return version;
	}

	public static FigShareClient to(String endpoint, int version, String clientKey, String clientSecret,
			String tokenKey, String tokenSecret) {
		return new FigShareClient(endpoint, version, clientKey, clientSecret, tokenKey, tokenSecret);
	}
	
	// --- API methods
	
	
}
