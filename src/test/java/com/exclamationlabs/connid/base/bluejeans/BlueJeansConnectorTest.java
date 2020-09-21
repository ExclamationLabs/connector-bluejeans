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

package com.exclamationlabs.connid.base.bluejeans;

import com.exclamationlabs.connid.base.bluejeans.attribute.BlueJeansUserAttribute;
import com.exclamationlabs.connid.base.bluejeans.configuration.BlueJeansConfiguration;
import com.exclamationlabs.connid.base.bluejeans.driver.rest.BlueJeansDriver;
import com.exclamationlabs.connid.base.connector.authenticator.model.OAuth2AccessTokenContainer;
import com.exclamationlabs.connid.base.connector.test.util.ConnectorMockRestTest;
import com.exclamationlabs.connid.base.connector.test.util.ConnectorTestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.spi.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BlueJeansConnectorTest extends ConnectorMockRestTest {

    private BlueJeansConnector connector;

    private static final String ENTERPRISE_PROFILE_RESPONSE =
            "{\"id\":4261490,\"enterprise\":679125,\"enterpriseName\":\"Exclamation Labs\",\"enterpriseAdmin\":true,\"joinDate\":1600166480000,\"expiryDate\":null}";

    @Before
    public void setup() {
        connector = new BlueJeansConnector() {
            @Override
            public void init(Configuration configuration) {
                setAuthenticator(null);
                setDriver(new BlueJeansDriver() {
                    @Override
                    protected HttpClient createClient() {
                        return stubClient;
                    }
                });
                super.init(configuration);
            }
        };
        BlueJeansConfiguration configuration = new BlueJeansConfiguration();
        configuration.setTestConfiguration();
        OAuth2AccessTokenContainer tokenContainer = new OAuth2AccessTokenContainer();
        tokenContainer.setScope("testuser");
        configuration.setOauth2Information(tokenContainer);
        connector.init(configuration);
    }


    @Test
    public void test110UserCreate() {
        final String responseData = "{\"id\":4270840}";

        final String responseData2 = "{\"autoRecord\":false,\"addParticipantPasscode\":false,\"allowModeratorScreenShare\":true,\"backgroundImage\":\"\",\"allowHighlights\":false,\"encryptionType\":\"NO_ENCRYPTION\",\"participantPasscode\":\"6677\",\"allowClosedCaptioning\":false,\"videoBestFit\":false,\"editability\":{\"enforceMeetingEncryption\":true,\"allowModeratorScreenShare\":true,\"videoBestFit\":true,\"allowLiveTranscription\":false,\"enforceMeetingEncryptionAllowPSTN\":true,\"moderatorLess\":true,\"allowParticipantsStartScreenShare\":true,\"allowHighlights\":false,\"allowClosedCaptioning\":false,\"disallowChat\":true,\"showAllParticipantsInIcs\":true,\"allowParticipantsHijackScreenShare\":true,\"autoRecord\":true,\"videoMuteParticipantsOnEntry\":true,\"addParticipantPasscode\":true,\"muteParticipantsOnEntry\":true},\"inviteeJoinOption\":0,\"id\":3596431,\"defaultLayout\":\"5\",\"moderatorPasscode\":\"4455\",\"meetingAccessType\":\"EVERYONE\",\"allowParticipantsHijackScreenShare\":true,\"numericId\":\"6582315513\",\"isLargeMeeting\":false,\"showVideoAnimations\":false,\"isModeratorLess\":false,\"welcomeMessage\":\"Welcome!!!\",\"disallowChat\":false,\"showAllParticipantsInIcs\":false,\"publishMeeting\":false,\"idleTimeout\":1800,\"videoMuteParticipantsOnEntry\":false,\"allowParticipantsStartScreenShare\":true,\"name\":\"fredflin6367269150035423294\",\"muteParticipantsOnEntry\":false,\"allowLiveTranscription\":false,\"allow720p\":null,\"personalMeetingUUId\":\"296c8d6f-9627-461f-9051-5c9d3c25af65\",\"playAudioAlerts\":false,\"personalMeetingId\":72065372}";
        prepareMockResponseMultiple(ENTERPRISE_PROFILE_RESPONSE, responseData, responseData2);

        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new AttributeBuilder().setName(BlueJeansUserAttribute.USERNAME.name()).addValue("test@tester.com").build());
        attributes.add(new AttributeBuilder().setName(BlueJeansUserAttribute.EMAIL_ID.name()).addValue("test@tester.com").build());

        attributes.add(new AttributeBuilder().setName(BlueJeansUserAttribute.FIRST_NAME.name()).addValue("John").build());
        attributes.add(new AttributeBuilder().setName(BlueJeansUserAttribute.LAST_NAME.name()).addValue("Doe").build());

        Uid newId = connector.create(ObjectClass.ACCOUNT, attributes, new OperationOptionsBuilder().build());
        assertNotNull(newId);
        assertNotNull(newId.getUidValue());
    }

    @Test
    public void test120UserModify() {
        // modify requires a get prior to execution
        String responseData = "{}";
        prepareMockResponseMultiple(responseData, responseData, null);

        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new AttributeBuilder().setName(BlueJeansUserAttribute.MIDDLE_NAME.name()).addValue("Johnny").build());

        Uid newId = connector.update(ObjectClass.ACCOUNT, new Uid("1234"), attributes, new OperationOptionsBuilder().build());
        assertNotNull(newId);
        assertNotNull(newId.getUidValue());
    }

    @Test
    public void test130UsersGet() {
        String responseData = "{\"count\":8,\"users\":[{\"id\":4261490,\"uri\":\"/v1/user/4261490\",\"email\":\"biff@buff.com\"},{\"id\":4265117,\"uri\":\"/v1/user/4265117\",\"email\":\"fred8@rubble.com\"},{\"id\":4266804,\"uri\":\"/v1/user/4266804\",\"email\":\"afred-3918920784226561786@rubble.com\"},{\"id\":4268542,\"uri\":\"/v1/user/4268542\",\"email\":\"tom@thumb.com\"},{\"id\":4268572,\"uri\":\"/v1/user/4268572\",\"email\":\"tommy@thumb.com\"},{\"id\":4268574,\"uri\":\"/v1/user/4268574\",\"email\":\"doctor@pepper.com\"},{\"id\":4268576,\"uri\":\"/v1/user/4268576\",\"email\":\"seven@up.com\"},{\"id\":4270840,\"uri\":\"/v1/user/4270840\",\"email\":\"fred6367269150035423294@rubble.com\"}]}";
        prepareMockResponseMultiple(ENTERPRISE_PROFILE_RESPONSE, responseData, null);

        List<String> idValues = new ArrayList<>();
        List<String> nameValues = new ArrayList<>();
        ResultsHandler resultsHandler = ConnectorTestUtils.buildResultsHandler(idValues, nameValues);

        connector.executeQuery(ObjectClass.ACCOUNT, "", resultsHandler, new OperationOptionsBuilder().build());
        assertTrue(idValues.size() >= 1);
        assertTrue(StringUtils.isNotBlank(idValues.get(0)));
        assertTrue(StringUtils.isNotBlank(nameValues.get(0)));
    }

    @Test
    public void test140UserGet() {
        String responseData = "{\"id\":4270840,\"username\":\"fredflin6367269150035423294\",\"firstName\":\"Freddy6367269150035423294\",\"lastName\":\"Flinstone\",\"emailId\":\"fred6367269150035423294@rubble.com\",\"company\":\"Hanna Barbera\",\"middleName\":\"Bedrock\",\"title\":\"Construction Worker\",\"phone\":\"\",\"profilePicture\":\"default\",\"timezone\":\"US/Pacific\",\"timeFormat\":12,\"language\":\"en\",\"skypeId\":null,\"gtalkId\":null,\"defaultEndpoint\":\"DESKTOP\",\"passwordChangeRequired\":false,\"marketoId\":0,\"optOutOffers\":false,\"optOutNews\":false,\"geoInfo\":null,\"howDidYouHear\":\"\",\"sfdcToken\":null,\"linkedinProfileUrl\":null,\"lastLogin\":1600609296000,\"dateJoined\":1600609296000,\"jid\":null,\"primaryPhone\":null,\"failedLoginCount\":0,\"passwordChangeDate\":1600609296000,\"isInitialSetupDone\":false,\"channel_id\":1,\"isVerified\":true,\"passwordChangeReason\":null}";
        String responseData2 = "{\"autoRecord\":false,\"addParticipantPasscode\":false,\"allowModeratorScreenShare\":true,\"backgroundImage\":\"\",\"allowHighlights\":false,\"encryptionType\":\"NO_ENCRYPTION\",\"participantPasscode\":\"8899\",\"allowClosedCaptioning\":false,\"videoBestFit\":false,\"editability\":{\"enforceMeetingEncryption\":true,\"allowModeratorScreenShare\":true,\"videoBestFit\":true,\"allowLiveTranscription\":false,\"enforceMeetingEncryptionAllowPSTN\":true,\"moderatorLess\":true,\"allowParticipantsStartScreenShare\":true,\"allowHighlights\":false,\"allowClosedCaptioning\":false,\"disallowChat\":true,\"showAllParticipantsInIcs\":true,\"allowParticipantsHijackScreenShare\":true,\"autoRecord\":true,\"videoMuteParticipantsOnEntry\":true,\"addParticipantPasscode\":true,\"muteParticipantsOnEntry\":true},\"inviteeJoinOption\":0,\"id\":3596431,\"defaultLayout\":\"5\",\"moderatorPasscode\":\"4455\",\"meetingAccessType\":\"EVERYONE\",\"allowParticipantsHijackScreenShare\":true,\"numericId\":\"6582315513\",\"isLargeMeeting\":false,\"showVideoAnimations\":false,\"isModeratorLess\":false,\"welcomeMessage\":\"Welcome!!!\",\"disallowChat\":false,\"showAllParticipantsInIcs\":false,\"publishMeeting\":false,\"idleTimeout\":1800,\"videoMuteParticipantsOnEntry\":false,\"allowParticipantsStartScreenShare\":true,\"name\":\"fredflin6367269150035423294\",\"muteParticipantsOnEntry\":false,\"allowLiveTranscription\":false,\"allow720p\":null,\"personalMeetingUUId\":\"296c8d6f-9627-461f-9051-5c9d3c25af65\",\"playAudioAlerts\":false,\"personalMeetingId\":72065372}";
        prepareMockResponseMultiple(responseData, responseData2, null);

        List<String> idValues = new ArrayList<>();
        List<String> nameValues = new ArrayList<>();
        ResultsHandler resultsHandler = ConnectorTestUtils.buildResultsHandler(idValues, nameValues);

        connector.executeQuery(ObjectClass.ACCOUNT, "1234", resultsHandler, new OperationOptionsBuilder().build());
        assertEquals(1, idValues.size());
        assertTrue(StringUtils.isNotBlank(idValues.get(0)));
    }


    @Test
    public void test390UserDelete() {
        prepareMockResponseMultiple(ENTERPRISE_PROFILE_RESPONSE, "", null);
        connector.delete(ObjectClass.ACCOUNT, new Uid("1234"), new OperationOptionsBuilder().build());
    }

    protected void prepareMockResponseMultiple(String responseData, String responseData2, String responseData3) {
        try {
            if (responseData3 != null) {
                Mockito.when(this.stubResponseEntity.getContent()).thenReturn(new ByteArrayInputStream(responseData.getBytes()), new ByteArrayInputStream(responseData2.getBytes()), new ByteArrayInputStream(responseData2.getBytes()));
                Mockito.when(this.stubResponse.getEntity()).thenReturn(this.stubResponseEntity, this.stubResponseEntity, this.stubResponseEntity);
                Mockito.when(this.stubResponse.getStatusLine()).thenReturn(this.stubStatusLine, this.stubStatusLine, this.stubStatusLine);
                Mockito.when(this.stubStatusLine.getStatusCode()).thenReturn(200, 200, 200);
                Mockito.when(this.stubClient.execute( ArgumentMatchers.any(HttpRequestBase.class))).thenReturn(this.stubResponse, this.stubResponse, this.stubResponse);

            } else {
                Mockito.when(this.stubResponseEntity.getContent()).thenReturn(new ByteArrayInputStream(responseData.getBytes()), new ByteArrayInputStream(responseData2.getBytes()));
                Mockito.when(this.stubResponse.getEntity()).thenReturn(this.stubResponseEntity, this.stubResponseEntity);
                Mockito.when(this.stubResponse.getStatusLine()).thenReturn(this.stubStatusLine, this.stubStatusLine);
                Mockito.when(this.stubStatusLine.getStatusCode()).thenReturn(200, 200);
                Mockito.when(this.stubClient.execute( ArgumentMatchers.any(HttpRequestBase.class))).thenReturn(this.stubResponse, this.stubResponse);
            }
        } catch (IOException var3) {
            Assert.fail("call failed");
        }

    }


 }