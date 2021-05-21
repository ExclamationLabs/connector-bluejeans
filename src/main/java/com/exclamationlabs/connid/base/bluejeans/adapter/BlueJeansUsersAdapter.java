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

package com.exclamationlabs.connid.base.bluejeans.adapter;

import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansUser;
import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansUserRoomSettings;
import com.exclamationlabs.connid.base.connector.adapter.AdapterValueTypeConverter;
import com.exclamationlabs.connid.base.connector.adapter.BaseAdapter;
import com.exclamationlabs.connid.base.connector.attribute.ConnectorAttribute;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.ObjectClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.exclamationlabs.connid.base.bluejeans.attribute.BlueJeansUserAttribute.*;
import static com.exclamationlabs.connid.base.connector.attribute.ConnectorAttributeDataType.*;
import static org.identityconnectors.framework.common.objects.AttributeInfo.Flags.NOT_CREATABLE;
import static org.identityconnectors.framework.common.objects.AttributeInfo.Flags.NOT_UPDATEABLE;

public class BlueJeansUsersAdapter extends BaseAdapter<BlueJeansUser> {

    @Override
    public ObjectClass getType() {
        return ObjectClass.ACCOUNT;
    }

    @Override
    public Class<BlueJeansUser> getIdentityModelClass() {
        return BlueJeansUser.class;
    }

    @Override
    public List<ConnectorAttribute> getConnectorAttributes() {
        List<ConnectorAttribute> result = new ArrayList<>();
        result.add(new ConnectorAttribute(USER_ID.name(), LONG, NOT_UPDATEABLE));
        result.add(new ConnectorAttribute(FIRST_NAME.name(), STRING));
        result.add(new ConnectorAttribute(LAST_NAME.name(), STRING));
        result.add(new ConnectorAttribute(MIDDLE_NAME.name(), STRING));
        result.add(new ConnectorAttribute(EMAIL_ID.name(), STRING));
        result.add(new ConnectorAttribute(USERNAME.name(), STRING));
        result.add(new ConnectorAttribute(ENTERPRISE_JOIN_DATE.name(), LONG, NOT_CREATABLE, NOT_UPDATEABLE));
        result.add(new ConnectorAttribute(IS_ENTERPRISE_ADMIN.name(), BOOLEAN, NOT_CREATABLE, NOT_UPDATEABLE));
        result.add(new ConnectorAttribute(URI.name(), STRING, NOT_CREATABLE, NOT_UPDATEABLE));
        result.add(new ConnectorAttribute(PASSWORD.name(), STRING, NOT_UPDATEABLE));
        result.add(new ConnectorAttribute(COMPANY.name(), STRING));
        result.add(new ConnectorAttribute(TITLE.name(), STRING));

        result.add(new ConnectorAttribute(ROOM_ID.name(), STRING, NOT_CREATABLE, NOT_UPDATEABLE));
        result.add(new ConnectorAttribute(ROOM_NAME.name(), STRING));
        result.add(new ConnectorAttribute(ORIGIN_POP_ID.name(), INTEGER));
        result.add(new ConnectorAttribute(IS_LARGE_MEETING.name(), BOOLEAN));
        result.add(new ConnectorAttribute(BACKGROUND_IMAGE.name(), STRING));
        result.add(new ConnectorAttribute(IS_MODERATOR_LESS.name(), BOOLEAN));
        result.add(new ConnectorAttribute(WELCOME_MESSAGE.name(), STRING));
        result.add(new ConnectorAttribute(DISALLOW_CHAT.name(), BOOLEAN));
        result.add(new ConnectorAttribute(ENCRYPTION_TYPE.name(), STRING));
        result.add(new ConnectorAttribute(SHOW_ALL_PARTICIPANTS_IN_ICS.name(), BOOLEAN));
        result.add(new ConnectorAttribute(PARTICIPANT_PASSCODE.name(), STRING));
        result.add(new ConnectorAttribute(PUBLISH_MEETING.name(), BOOLEAN));
        result.add(new ConnectorAttribute(VIDEO_BEST_FIT.name(), BOOLEAN));
        result.add(new ConnectorAttribute(MUTE_PARTICIPANTS_ON_ENTRY.name(), BOOLEAN));
        result.add(new ConnectorAttribute(ENFORCE_MEETING_ENCRYPTION.name(), BOOLEAN));
        result.add(new ConnectorAttribute(ENFORCE_MEETING_ENCRYPTION_ALLOW_PSTN.name(), BOOLEAN));
        result.add(new ConnectorAttribute(IDLE_TIMEOUT.name(), INTEGER));
        result.add(new ConnectorAttribute(DEFAULT_LAYOUT.name(), STRING));
        result.add(new ConnectorAttribute(PLAY_AUDIO_ALERTS.name(), BOOLEAN));
        result.add(new ConnectorAttribute(PERSONAL_MEETING_ID.name(), INTEGER));
        result.add(new ConnectorAttribute(MODERATOR_PASSCODE.name(), STRING));

        return result;
    }

    @Override
    protected List<Attribute> constructAttributes(BlueJeansUser user) {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(AttributeBuilder.build(USER_ID.name(), user.getId()));
        attributes.add(AttributeBuilder.build(EMAIL_ID.name(), user.getEmailId()));
        attributes.add(AttributeBuilder.build(FIRST_NAME.name(), user.getFirstName()));
        attributes.add(AttributeBuilder.build(MIDDLE_NAME.name(), user.getMiddleName()));
        attributes.add(AttributeBuilder.build(LAST_NAME.name(), user.getLastName()));
        attributes.add(AttributeBuilder.build(ENTERPRISE_JOIN_DATE.name(), user.getEnterpriseJoinDate()));
        attributes.add(AttributeBuilder.build(USERNAME.name(), user.getUsername()));
        attributes.add(AttributeBuilder.build(URI.name(), user.getUri()));
        attributes.add(AttributeBuilder.build(COMPANY.name(), user.getCompany()));
        attributes.add(AttributeBuilder.build(TITLE.name(), user.getTitle()));

        if (user.getRoomSettings() != null) {

            attributes.add(AttributeBuilder.build(ROOM_ID.name(), user.getRoomSettings().getNumericId()));
            attributes.add(AttributeBuilder.build(ROOM_NAME.name(), user.getRoomSettings().getName()));
            attributes.add(AttributeBuilder.build(ORIGIN_POP_ID.name(), user.getRoomSettings().getOriginPopId()));
            attributes.add(AttributeBuilder.build(IS_LARGE_MEETING.name(), user.getRoomSettings().getLargeMeeting()));
            attributes.add(AttributeBuilder.build(SHOW_VIDEO_ANIMATIONS.name(), user.getRoomSettings().getShowVideoAnimations()));
            attributes.add(AttributeBuilder.build(BACKGROUND_IMAGE.name(), user.getRoomSettings().getBackgroundImage()));
            attributes.add(AttributeBuilder.build(IS_MODERATOR_LESS.name(), user.getRoomSettings().getModeratorLess()));
            attributes.add(AttributeBuilder.build(WELCOME_MESSAGE.name(), user.getRoomSettings().getWelcomeMessage()));
            attributes.add(AttributeBuilder.build(DISALLOW_CHAT.name(), user.getRoomSettings().getDisallowChat()));
            attributes.add(AttributeBuilder.build(ENCRYPTION_TYPE.name(), user.getRoomSettings().getEncryptionType()));
            attributes.add(AttributeBuilder.build(SHOW_ALL_PARTICIPANTS_IN_ICS.name(), user.getRoomSettings().getShowAllParticipantsInIcs()));
            attributes.add(AttributeBuilder.build(PARTICIPANT_PASSCODE.name(), user.getRoomSettings().getParticipantPasscode()));
            attributes.add(AttributeBuilder.build(PUBLISH_MEETING.name(), user.getRoomSettings().getPublishMeeting()));
            attributes.add(AttributeBuilder.build(VIDEO_BEST_FIT.name(), user.getRoomSettings().getVideoBestFit()));
            attributes.add(AttributeBuilder.build(MUTE_PARTICIPANTS_ON_ENTRY.name(), user.getRoomSettings().getMuteParticipantsOnEntry()));
            attributes.add(AttributeBuilder.build(ENFORCE_MEETING_ENCRYPTION.name(), user.getRoomSettings().getEnforceMeetingEncryption()));
            attributes.add(AttributeBuilder.build(ENFORCE_MEETING_ENCRYPTION_ALLOW_PSTN.name(), user.getRoomSettings().getEnforceMeetingEncryptionAllowPSTN()));
            attributes.add(AttributeBuilder.build(IDLE_TIMEOUT.name(), user.getRoomSettings().getIdleTimeout()));
            attributes.add(AttributeBuilder.build(DEFAULT_LAYOUT.name(), user.getRoomSettings().getDefaultLayout()));
            attributes.add(AttributeBuilder.build(PLAY_AUDIO_ALERTS.name(), user.getRoomSettings().getPlayAudioAlerts()));
            attributes.add(AttributeBuilder.build(PERSONAL_MEETING_ID.name(), user.getRoomSettings().getPersonalMeetingId()));
            attributes.add(AttributeBuilder.build(MODERATOR_PASSCODE.name(), user.getRoomSettings().getModeratorPasscode()));
        }

        return attributes;
    }

    @Override
    protected BlueJeansUser constructModel(Set<Attribute> attributes, boolean creation) {
        BlueJeansUser user = new BlueJeansUser();
        String idValue = AdapterValueTypeConverter.getIdentityIdAttributeValue(attributes);
        if (idValue != null) {
            user.setId(Long.parseLong(idValue));
        }

        user.setFirstName(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, FIRST_NAME));
        user.setLastName(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, LAST_NAME));
        user.setMiddleName(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, MIDDLE_NAME));
        user.setEmailId(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, EMAIL_ID));
        user.setCompany(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, COMPANY));
        user.setTitle(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, TITLE));
        user.setUsername(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, USERNAME));

        if (creation) {
            user.setPassword(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, PASSWORD));
        }

        BlueJeansUserRoomSettings room = new BlueJeansUserRoomSettings();
        room.setNumericId(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, ROOM_ID));
        room.setName(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, ROOM_NAME));
        room.setOriginPopId(AdapterValueTypeConverter.getSingleAttributeValue(Integer.class, attributes, ORIGIN_POP_ID));
        room.setLargeMeeting(AdapterValueTypeConverter.getSingleAttributeValue(Boolean.class, attributes, IS_LARGE_MEETING));
        room.setShowVideoAnimations(AdapterValueTypeConverter.getSingleAttributeValue(Boolean.class, attributes, SHOW_VIDEO_ANIMATIONS));
        room.setBackgroundImage(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, BACKGROUND_IMAGE));
        room.setModeratorLess(AdapterValueTypeConverter.getSingleAttributeValue(Boolean.class, attributes, IS_MODERATOR_LESS));
        room.setWelcomeMessage(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, WELCOME_MESSAGE));
        room.setDisallowChat(AdapterValueTypeConverter.getSingleAttributeValue(Boolean.class, attributes, DISALLOW_CHAT));
        room.setEncryptionType(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, ENCRYPTION_TYPE));
        room.setShowAllParticipantsInIcs(AdapterValueTypeConverter.getSingleAttributeValue(Boolean.class, attributes, SHOW_ALL_PARTICIPANTS_IN_ICS));
        room.setParticipantPasscode(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, PARTICIPANT_PASSCODE));
        room.setPublishMeeting(AdapterValueTypeConverter.getSingleAttributeValue(Boolean.class, attributes, PUBLISH_MEETING));
        room.setVideoBestFit(AdapterValueTypeConverter.getSingleAttributeValue(Boolean.class, attributes, VIDEO_BEST_FIT));
        room.setMuteParticipantsOnEntry(AdapterValueTypeConverter.getSingleAttributeValue(Boolean.class, attributes, MUTE_PARTICIPANTS_ON_ENTRY));
        room.setEnforceMeetingEncryption(AdapterValueTypeConverter.getSingleAttributeValue(Boolean.class, attributes, ENFORCE_MEETING_ENCRYPTION));
        room.setEnforceMeetingEncryptionAllowPSTN(AdapterValueTypeConverter.getSingleAttributeValue(Boolean.class, attributes, ENFORCE_MEETING_ENCRYPTION_ALLOW_PSTN));
        room.setIdleTimeout(AdapterValueTypeConverter.getSingleAttributeValue(Integer.class, attributes, IDLE_TIMEOUT));
        room.setDefaultLayout(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, DEFAULT_LAYOUT));
        room.setPlayAudioAlerts(AdapterValueTypeConverter.getSingleAttributeValue(Boolean.class, attributes, PLAY_AUDIO_ALERTS));
        room.setPersonalMeetingId(AdapterValueTypeConverter.getSingleAttributeValue(Integer.class, attributes, PERSONAL_MEETING_ID));
        room.setModeratorPasscode(AdapterValueTypeConverter.getSingleAttributeValue(String.class, attributes, MODERATOR_PASSCODE));

        user.setRoomSettings(room);
        return user;
    }
}
