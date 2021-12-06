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

import com.exclamationlabs.connid.base.bluejeans.configuration.BlueJeansConfiguration;
import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansUser;
import com.exclamationlabs.connid.base.bluejeans.model.response.EnterpriseProfileResponse;
import com.exclamationlabs.connid.base.connector.driver.rest.BaseRestDriver;
import com.exclamationlabs.connid.base.connector.driver.rest.RestFaultProcessor;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.exceptions.ConnectorSecurityException;


public class BlueJeansDriver extends BaseRestDriver<BlueJeansConfiguration> {

    private static final Log LOG = Log.getLog(BlueJeansDriver.class);

    private Long enterpriseId;

    public BlueJeansDriver() {
        super();
        addInvocator(BlueJeansUser.class, new BlueJeansUserInvocator());
    }

    @Override
    protected RestFaultProcessor getFaultProcessor() {
        return BlueJeansFaultProcessor.getInstance();
    }

    @Override
    protected boolean usesBearerAuthorization() {
        return true;
    }

    @Override
    protected String getBaseServiceUrl() {
        return getConfiguration().getServiceUrl();
    }

    @Override
    public void test() throws ConnectorException {
        getEnterpriseId();
    }

    @Override
    public void close() {
    }

    private String getUserIdFromOAuthResponse() {
        if (configuration.getOauth2Information() == null || configuration.getOauth2Information().get("scope") == null) {
            throw new ConnectorSecurityException(
                    "BlueJeans security error, OAuth response invalid or did not contain user id");
        }
        return configuration.getOauth2Information().get("scope");
    }

    Long getEnterpriseId() {
        if (enterpriseId == null) {
            String userId = getUserIdFromOAuthResponse();
            EnterpriseProfileResponse response = executeGetRequest("user/" + userId + "/enterprise_profile",
                    EnterpriseProfileResponse.class).getResponseObject();

            if (response.getEnterprise() == null) {
                throw new ConnectorException("No enterprise id in enterprise profile response for user " + userId);
            }
            LOG.info("Enterprise id {0} retrieved from user id {1}", userId, response.getEnterprise());
            enterpriseId = response.getEnterprise();
        }
        return enterpriseId;
    }

}
