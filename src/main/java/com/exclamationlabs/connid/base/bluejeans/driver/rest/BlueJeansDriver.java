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

import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansGroup;
import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansUser;
import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansUserRoomSettings;
import com.exclamationlabs.connid.base.bluejeans.model.response.AllUsersResponse;
import com.exclamationlabs.connid.base.bluejeans.model.response.CreateUserResponse;
import com.exclamationlabs.connid.base.bluejeans.model.response.EnterpriseProfileResponse;
import com.exclamationlabs.connid.base.connector.configuration.ConnectorProperty;
import com.exclamationlabs.connid.base.connector.driver.rest.BaseRestDriver;
import com.exclamationlabs.connid.base.connector.driver.rest.RestFaultProcessor;
import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.exceptions.ConnectorSecurityException;

import java.util.List;
import java.util.Set;

public class BlueJeansDriver extends BaseRestDriver<BlueJeansUser, BlueJeansGroup> {

    private static final Log LOG = Log.getLog(BlueJeansDriver.class);

    private Long enterpriseId;

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
        return "https://api.bluejeans.com/v1/";
    }

    @Override
    public Set<ConnectorProperty> getRequiredPropertyNames() {
        return null;
    }

    @Override
    public void test() throws ConnectorException {
        getEnterpriseId();
    }

    @Override
    public void close() {
    }

    @Override
    public String createUser(BlueJeansUser blueJeansUser) throws ConnectorException {
        BlueJeansUserRoomSettings roomSettings = blueJeansUser.getRoomSettings();
        blueJeansUser.setRoomSettings(null);
        Long currentEnterpriseId = getEnterpriseId();
        CreateUserResponse response = executePostRequest(
                "enterprise/" + currentEnterpriseId + "/users", CreateUserResponse.class, blueJeansUser);

        if (response.getId() == null) {
            throw new ConnectorException("User id for new user was not returned by BlueJeans service");
        }
        String newId = "" + response.getId();

        // Create room settings needed for new user account
        executePostRequest("user/" + newId + "/room", BlueJeansUserRoomSettings.class, roomSettings);

        return newId;
    }

    @Override
    public String createGroup(BlueJeansGroup blueJeansGroup) throws ConnectorException {
        throw new ConnectorException("Groups not supported for BlueJeans connector");
    }

    @Override
    public void updateUser(String userId, BlueJeansUser blueJeansUser) throws ConnectorException {
        BlueJeansUserRoomSettings roomSettings = blueJeansUser.getRoomSettings();
        blueJeansUser.setRoomSettings(null);

        executePutRequest("user/" + userId, BlueJeansUser.class, blueJeansUser);
        executePutRequest("user/" + userId + "/room", BlueJeansUserRoomSettings.class, roomSettings);
    }

    @Override
    public void updateGroup(String s, BlueJeansGroup blueJeansGroup) throws ConnectorException {
        throw new ConnectorException("Groups not supported for BlueJeans connector");
    }

    @Override
    public void deleteUser(String userId) throws ConnectorException {
        Long currentEnterpriseId = getEnterpriseId();
        executeDeleteRequest("enterprise/" + currentEnterpriseId + "/users/" + userId, null);
    }

    @Override
    public void deleteGroup(String s) throws ConnectorException {
        throw new ConnectorException("Groups not supported for BlueJeans connector");
    }

    @Override
    public List<BlueJeansUser> getUsers() throws ConnectorException {
        Long currentEnterpriseId = getEnterpriseId();
        AllUsersResponse response = executeGetRequest("enterprise/" + currentEnterpriseId +
                "/users?fields=email", AllUsersResponse.class);
        return response.getUsers();
    }

    @Override
    public List<BlueJeansGroup> getGroups() throws ConnectorException {
        throw new ConnectorException("Groups not supported for BlueJeans connector");
    }

    @Override
    public BlueJeansUser getUser(String userId) throws ConnectorException {
        BlueJeansUser user = executeGetRequest("user/" + userId, BlueJeansUser.class);

        BlueJeansUserRoomSettings settings = executeGetRequest("user/" + userId + "/room", BlueJeansUserRoomSettings.class);

        user.setRoomSettings(settings);
        return user;
    }

    @Override
    public BlueJeansGroup getGroup(String s) throws ConnectorException {
        throw new ConnectorException("Groups not supported for BlueJeans connector");
    }

    @Override
    public void addGroupToUser(String s, String s1) throws ConnectorException {

    }

    @Override
    public void removeGroupFromUser(String s, String s1) throws ConnectorException {

    }

    private String getUserIdFromOAuthResponse() {
        if (configuration.getOauth2Information() == null || configuration.getOauth2Information().getScope() == null) {
            throw new ConnectorSecurityException(
                    "BlueJeans security error, OAuth response invalid or did not contain user id");
        }
        return configuration.getOauth2Information().getScope();
    }

    private Long getEnterpriseId() {
        if (enterpriseId != null) {
            return enterpriseId;
        } else {
            String userId = getUserIdFromOAuthResponse();
            EnterpriseProfileResponse response = executeGetRequest("user/" + userId + "/enterprise_profile", EnterpriseProfileResponse.class);

            if (response.getEnterprise() == null) {
                throw new ConnectorException("No enterprise id in enterprise profile response for user " + userId);
            }
            LOG.info("Enterprise id {0} retrieved from user id {1}", userId, response.getEnterprise());
            enterpriseId = response.getEnterprise();
            return enterpriseId;
        }
    }
}
