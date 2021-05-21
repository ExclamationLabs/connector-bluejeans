package com.exclamationlabs.connid.base.bluejeans.driver.rest;

import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansUser;
import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansUserRoomSettings;
import com.exclamationlabs.connid.base.bluejeans.model.response.AllUsersResponse;
import com.exclamationlabs.connid.base.bluejeans.model.response.CreateUserResponse;
import com.exclamationlabs.connid.base.connector.driver.DriverInvocator;
import org.identityconnectors.framework.common.exceptions.ConnectorException;

import java.util.List;
import java.util.Map;

public class BlueJeansUserInvocator implements DriverInvocator<BlueJeansDriver, BlueJeansUser> {

    @Override
    public String create(BlueJeansDriver driver, BlueJeansUser blueJeansUser) throws ConnectorException {
        BlueJeansUserRoomSettings roomSettings = blueJeansUser.getRoomSettings();
        blueJeansUser.setRoomSettings(null);
        Long currentEnterpriseId = driver.getEnterpriseId();
        CreateUserResponse response = driver.executePostRequest(
                "enterprise/" + currentEnterpriseId + "/users", CreateUserResponse.class, blueJeansUser).getResponseObject();

        if (response.getId() == null) {
            throw new ConnectorException("User id for new user was not returned by BlueJeans service");
        }
        String newId = "" + response.getId();

        // Create room settings needed for new user account
        driver.executePostRequest("user/" + newId + "/room", BlueJeansUserRoomSettings.class, roomSettings);

        return newId;
    }

    @Override
    public void update(BlueJeansDriver driver, String userId, BlueJeansUser blueJeansUser) throws ConnectorException {
        BlueJeansUserRoomSettings roomSettings = blueJeansUser.getRoomSettings();
        blueJeansUser.setRoomSettings(null);

        driver.executePutRequest("user/" + userId, BlueJeansUser.class, blueJeansUser);
        driver.executePutRequest("user/" + userId + "/room", BlueJeansUserRoomSettings.class, roomSettings);
    }

    @Override
    public void delete(BlueJeansDriver driver, String userId) throws ConnectorException {
        Long currentEnterpriseId = driver.getEnterpriseId();
        driver.executeDeleteRequest("enterprise/" + currentEnterpriseId + "/users/" + userId, null);
    }

    @Override
    public List<BlueJeansUser> getAll(BlueJeansDriver driver, Map<String, Object> map) throws ConnectorException {
        Long currentEnterpriseId = driver.getEnterpriseId();
        AllUsersResponse response = driver.executeGetRequest("enterprise/" + currentEnterpriseId +
                "/users?fields=email", AllUsersResponse.class).getResponseObject();
        return response.getUsers();
    }

    @Override
    public BlueJeansUser getOne(BlueJeansDriver driver, String userId, Map<String, Object> map) throws ConnectorException {
        BlueJeansUser user = driver.executeGetRequest("user/" + userId, BlueJeansUser.class).getResponseObject();

        BlueJeansUserRoomSettings settings = driver.executeGetRequest("user/" + userId + "/room",
                BlueJeansUserRoomSettings.class).getResponseObject();

        user.setRoomSettings(settings);
        return user;
    }

}
