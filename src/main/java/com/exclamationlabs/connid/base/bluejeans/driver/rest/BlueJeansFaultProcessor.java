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

package com.exclamationlabs.connid.base.bluejeans.driver.rest;

import com.exclamationlabs.connid.base.bluejeans.model.response.ErrorResponse;
import com.exclamationlabs.connid.base.connector.driver.rest.RestFaultProcessor;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.common.exceptions.AlreadyExistsException;
import org.identityconnectors.framework.common.exceptions.ConnectorException;

import java.io.IOException;

public class BlueJeansFaultProcessor implements RestFaultProcessor {

    private static final Log LOG = Log.getLog(BlueJeansFaultProcessor.class);

    private static final BlueJeansFaultProcessor instance = new BlueJeansFaultProcessor();

    public static BlueJeansFaultProcessor getInstance() {
        return instance;
    }

    public void process(HttpResponse httpResponse, GsonBuilder gsonBuilder) {
        String rawResponse;
        try {
            rawResponse = EntityUtils.toString(httpResponse.getEntity(), Charsets.UTF_8);
            LOG.info("Raw Fault response {0}", rawResponse);

            Header responseType = httpResponse.getFirstHeader("Content-Type");
            String responseTypeValue = responseType.getValue();
            if (!StringUtils.contains(responseTypeValue, ContentType.APPLICATION_JSON.getMimeType())) {
                // received non-JSON error response from BlueJeans unable to process
                String errorMessage = "Unable to parse BlueJeans response, not valid JSON: ";
                LOG.info("{0} {1}", errorMessage, rawResponse);
                throw new ConnectorException(errorMessage + rawResponse);
            }

            handleFaultResponse(rawResponse, gsonBuilder);

        } catch (IOException e) {
            throw new ConnectorException("Unable to read fault response from BlueJeans response. " +
                    "Status: " + httpResponse.getStatusLine().getStatusCode() + ", " +
                    httpResponse.getStatusLine().getReasonPhrase(), e);
        }
    }

    private void handleFaultResponse(String rawResponse, GsonBuilder gsonBuilder) {
        ErrorResponse faultData = gsonBuilder.create().fromJson(rawResponse, ErrorResponse.class);
        if (faultData != null) {
            if (faultData.getCode() != null) {
                if(checkRecognizedFaultMessages(faultData)) {
                    // other fault condition
                    throw new ConnectorException("Unknown fault received from BlueJeans.  Code: " +
                            faultData.getCode() + "; Message: " +
                            faultData.getMessage());
                } else {
                    return;
                }
            }
        }
        throw new ConnectorException("Unknown fault received from BlueJeans. Raw response JSON: " + rawResponse);

    }


    private Boolean checkRecognizedFaultMessages(ErrorResponse faultData) {
        String message = faultData.getMessage();

        if (StringUtils.containsIgnoreCase(message, "Email address is already taken")) {
            throw new AlreadyExistsException(message);
        }

        return true;
    }

}
