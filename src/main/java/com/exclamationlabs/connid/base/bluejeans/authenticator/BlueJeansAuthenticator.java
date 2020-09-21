/*
    Copyright 2020 Exclamation Labs
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.exclamationlabs.connid.base.bluejeans.authenticator;

import com.exclamationlabs.connid.base.bluejeans.authenticator.model.BlueJeansAccessTokenContainer;
import com.exclamationlabs.connid.base.bluejeans.authenticator.model.OAuthPasswordEntity;
import com.exclamationlabs.connid.base.connector.authenticator.OAuth2TokenPasswordAuthenticator;
import com.exclamationlabs.connid.base.connector.authenticator.model.OAuth2AccessTokenContainer;
import com.exclamationlabs.connid.base.connector.configuration.ConnectorConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.Charsets;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.exceptions.ConnectorSecurityException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.exclamationlabs.connid.base.connector.configuration.ConnectorProperty.*;

/**
 * The BlueJeans auth response has a complex 'scope' object that has the user id in it that we need
 * to retrieve to properly integrate with BlueJeans.
 */
public class BlueJeansAuthenticator extends OAuth2TokenPasswordAuthenticator {

    private static final Log LOG = Log.getLog(BlueJeansAuthenticator.class);

    @Override
    protected String executeRequest(ConnectorConfiguration configuration, HttpClient client, HttpPost request, UrlEncodedFormEntity entity, GsonBuilder gsonBuilder) throws IOException {
        OAuthPasswordEntity postEntity = new OAuthPasswordEntity();
        postEntity.setGrantType("password");
        postEntity.setUsername(configuration.getProperty(CONNECTOR_BASE_AUTH_OAUTH2_USERNAME));
        postEntity.setPassword(configuration.getProperty(CONNECTOR_BASE_AUTH_OAUTH2_PASSWORD));
        setupJsonRequestBody(request, postEntity);

        getAdditionalAuthenticationHeaders(configuration).forEach(request::setHeader);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        LOG.info("OAuth2 Received {0} response for {1} {2}", statusCode,
                request.getMethod(), request.getURI());
        String rawJson = EntityUtils.toString(response.getEntity(), Charsets.UTF_8.name());
        LOG.info("Received raw JSON: {0}", rawJson);
        if (statusCode >= HttpStatus.SC_BAD_REQUEST) {
            throw new ConnectorSecurityException("OAuth2 assertion failed, status code is HTTP " +
                    statusCode);
        }

        BlueJeansAccessTokenContainer authResponse = gsonBuilder.create().fromJson(rawJson,
                BlueJeansAccessTokenContainer.class);

        if (authResponse != null && authResponse.getAccessToken() != null) {
            OAuth2AccessTokenContainer normalContainer = new OAuth2AccessTokenContainer();
            normalContainer.setAccessToken(authResponse.getAccessToken());
            normalContainer.setRefreshToken(authResponse.getRefreshToken());
            normalContainer.setExpiresIn(authResponse.getExpiresIn());

            // Set the 'scope' string of the normal container to the BlueJeans user id
            normalContainer.setScope("" + authResponse.getScope().getUser());

            configuration.setOauth2Information(normalContainer);
            return authResponse.getAccessToken();
        } else {
            throw new ConnectorSecurityException("Invalid/empty response received from OAuth2: " + rawJson);
        }
    }


    @Override
    public String authenticate(ConnectorConfiguration configuration) throws ConnectorSecurityException {
        initializeForHttp();
        HttpClient client = createClient();
        try {
            HttpPost request = new HttpPost(configuration.getProperty(CONNECTOR_BASE_AUTH_OAUTH2_TOKEN_URL));
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            return executeRequest(configuration, client, request, null, gsonBuilder);

        } catch (IOException e) {
            throw new ConnectorSecurityException(
                    "Unexpected error occurred during OAuth2 call: " + e.getMessage(), e);
        }

    }

    private void setupJsonRequestBody(HttpEntityEnclosingRequestBase request, Object requestBody) {
        if (requestBody != null) {
            Gson gson = gsonBuilder.create();
            String json = gson.toJson(requestBody);
            LOG.info("JSON formatted request for {0}: {1}", requestBody.getClass().getName(), json);
            try {
                request.setEntity(new StringEntity(json));
            } catch (UnsupportedEncodingException e) {
                throw new ConnectorException("Request body encoding failed for data: " + json, e);
            }
        }
    }

}
