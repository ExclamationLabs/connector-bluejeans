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

import com.exclamationlabs.connid.base.bluejeans.adapter.BlueJeansGroupsAdapter;
import com.exclamationlabs.connid.base.bluejeans.adapter.BlueJeansUsersAdapter;
import com.exclamationlabs.connid.base.bluejeans.attribute.BlueJeansGroupAttribute;
import com.exclamationlabs.connid.base.bluejeans.attribute.BlueJeansUserAttribute;
import com.exclamationlabs.connid.base.bluejeans.authenticator.BlueJeansAuthenticator;
import com.exclamationlabs.connid.base.bluejeans.configuration.BlueJeansConfiguration;
import com.exclamationlabs.connid.base.bluejeans.driver.rest.BlueJeansDriver;
import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansGroup;
import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansUser;
import com.exclamationlabs.connid.base.connector.BaseConnector;
import com.exclamationlabs.connid.base.connector.attribute.ConnectorAttributeMapBuilder;

import org.identityconnectors.framework.spi.ConnectorClass;

import static com.exclamationlabs.connid.base.connector.attribute.ConnectorAttributeDataType.*;
import static com.exclamationlabs.connid.base.bluejeans.attribute.BlueJeansGroupAttribute.*;
import static com.exclamationlabs.connid.base.bluejeans.attribute.BlueJeansUserAttribute.*;
import static org.identityconnectors.framework.common.objects.AttributeInfo.Flags.*;

@ConnectorClass(displayNameKey = "bluejeans.connector.display", configurationClass = BlueJeansConfiguration.class)
public class BlueJeansConnector extends BaseConnector<BlueJeansUser, BlueJeansGroup> {

    public BlueJeansConnector() {

        setAuthenticator(new BlueJeansAuthenticator());
        setDriver(new BlueJeansDriver());
        setUsersAdapter(new BlueJeansUsersAdapter());
        setGroupsAdapter(new BlueJeansGroupsAdapter());
        setUserAttributes( new ConnectorAttributeMapBuilder<>(BlueJeansUserAttribute.class)
                .add(USER_ID, LONG, NOT_UPDATEABLE)
                .add(FIRST_NAME, STRING)
                .add(LAST_NAME, STRING)
                .add(MIDDLE_NAME, STRING)
                .add(EMAIL_ID, STRING)
                .add(USERNAME, STRING)
                .add(ENTERPRISE_JOIN_DATE, LONG, NOT_CREATABLE, NOT_UPDATEABLE)
                .add(IS_ENTERPRISE_ADMIN, BOOLEAN, NOT_CREATABLE, NOT_UPDATEABLE)
                .add(URI, STRING, NOT_CREATABLE, NOT_UPDATEABLE)
                .add(PASSWORD, STRING, NOT_UPDATEABLE)
                .add(COMPANY, STRING)
                .add(TITLE, STRING)

                .add(ROOM_ID, STRING, NOT_CREATABLE, NOT_UPDATEABLE)
                .add(ROOM_NAME, STRING)
                .add(ORIGIN_POP_ID, INTEGER)
                .add(IS_LARGE_MEETING, BOOLEAN)
                .add(SHOW_VIDEO_ANIMATIONS, BOOLEAN)
                .add(BACKGROUND_IMAGE, STRING)
                .add(IS_MODERATOR_LESS, BOOLEAN)
                .add(WELCOME_MESSAGE, STRING)
                .add(DISALLOW_CHAT, BOOLEAN)
                .add(ENCRYPTION_TYPE, STRING)
                .add(SHOW_ALL_PARTICIPANTS_IN_ICS, BOOLEAN)
                .add(PARTICIPANT_PASSCODE, STRING)
                .add(PUBLISH_MEETING, BOOLEAN)
                .add(MODERATOR_LESS, BOOLEAN)
                .add(VIDEO_BEST_FIT, BOOLEAN)
                .add(MUTE_PARTICIPANTS_ON_ENTRY, BOOLEAN)
                .add(ENFORCE_MEETING_ENCRYPTION, BOOLEAN)
                .add(ENFORCE_MEETING_ENCRYPTION_ALLOW_PSTN, BOOLEAN)
                .add(IDLE_TIMEOUT, INTEGER)
                .add(DEFAULT_LAYOUT, STRING)
                .add(PLAY_AUDIO_ALERTS, BOOLEAN)
                .add(PERSONAL_MEETING_ID, INTEGER)
                .add(MODERATOR_PASSCODE, STRING)
                .build());

        setGroupAttributes(new ConnectorAttributeMapBuilder<>(BlueJeansGroupAttribute.class)
                .add(GROUP_ID, STRING, NOT_UPDATEABLE)
                .add(GROUP_NAME, STRING, REQUIRED, NOT_UPDATEABLE)
                .add(GROUP_DESCRIPTION, STRING)
                .build());
    }

}
