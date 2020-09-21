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

import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansGroup;
import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansUser;
import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansUserRoomSettings;
import com.exclamationlabs.connid.base.connector.adapter.AdapterValueTypeConverter;
import com.exclamationlabs.connid.base.connector.adapter.BaseUsersAdapter;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.ConnectorObject;

import java.util.Set;

import static com.exclamationlabs.connid.base.bluejeans.attribute.BlueJeansUserAttribute.*;

public class BlueJeansUsersAdapter extends BaseUsersAdapter<BlueJeansUser, BlueJeansGroup> {
    @Override
    protected BlueJeansUser constructUser(Set<Attribute> attributes, boolean creation) {

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

    @Override
    protected ConnectorObject constructConnectorObject(BlueJeansUser user) {
        return getConnectorObjectBuilder(user)
                .addAttribute(AttributeBuilder.build(USER_ID.name(), user.getId()))
                .addAttribute(AttributeBuilder.build(EMAIL_ID.name(), user.getEmailId()))
                .addAttribute(AttributeBuilder.build(FIRST_NAME.name(), user.getFirstName()))
                .addAttribute(AttributeBuilder.build(MIDDLE_NAME.name(), user.getMiddleName()))
                .addAttribute(AttributeBuilder.build(LAST_NAME.name(), user.getLastName()))
                .addAttribute(AttributeBuilder.build(ENTERPRISE_JOIN_DATE.name(), user.getEnterpriseJoinDate()))
                .addAttribute(AttributeBuilder.build(USERNAME.name(), user.getUsername()))
                .addAttribute(AttributeBuilder.build(URI.name(), user.getUri()))
                .addAttribute(AttributeBuilder.build(COMPANY.name(), user.getCompany()))
                .addAttribute(AttributeBuilder.build(TITLE.name(), user.getTitle()))

                .addAttribute(AttributeBuilder.build(ROOM_ID.name(), user.getRoomSettings().getNumericId()))
                .addAttribute(AttributeBuilder.build(ROOM_NAME.name(), user.getRoomSettings().getName()))
                .addAttribute(AttributeBuilder.build(ORIGIN_POP_ID.name(), user.getRoomSettings().getOriginPopId()))
                .addAttribute(AttributeBuilder.build(IS_LARGE_MEETING.name(), user.getRoomSettings().getLargeMeeting()))
                .addAttribute(AttributeBuilder.build(SHOW_VIDEO_ANIMATIONS.name(), user.getRoomSettings().getShowVideoAnimations()))
                .addAttribute(AttributeBuilder.build(BACKGROUND_IMAGE.name(), user.getRoomSettings().getBackgroundImage()))
                .addAttribute(AttributeBuilder.build(IS_MODERATOR_LESS.name(), user.getRoomSettings().getModeratorLess()))
                .addAttribute(AttributeBuilder.build(WELCOME_MESSAGE.name(), user.getRoomSettings().getWelcomeMessage()))
                .addAttribute(AttributeBuilder.build(DISALLOW_CHAT.name(), user.getRoomSettings().getDisallowChat()))
                .addAttribute(AttributeBuilder.build(ENCRYPTION_TYPE.name(), user.getRoomSettings().getEncryptionType()))
                .addAttribute(AttributeBuilder.build(SHOW_ALL_PARTICIPANTS_IN_ICS.name(), user.getRoomSettings().getShowAllParticipantsInIcs()))
                .addAttribute(AttributeBuilder.build(PARTICIPANT_PASSCODE.name(), user.getRoomSettings().getParticipantPasscode()))
                .addAttribute(AttributeBuilder.build(PUBLISH_MEETING.name(), user.getRoomSettings().getPublishMeeting()))
                .addAttribute(AttributeBuilder.build(VIDEO_BEST_FIT.name(), user.getRoomSettings().getVideoBestFit()))
                .addAttribute(AttributeBuilder.build(MUTE_PARTICIPANTS_ON_ENTRY.name(), user.getRoomSettings().getMuteParticipantsOnEntry()))
                .addAttribute(AttributeBuilder.build(ENFORCE_MEETING_ENCRYPTION.name(), user.getRoomSettings().getEnforceMeetingEncryption()))
                .addAttribute(AttributeBuilder.build(ENFORCE_MEETING_ENCRYPTION_ALLOW_PSTN.name(), user.getRoomSettings().getEnforceMeetingEncryptionAllowPSTN()))
                .addAttribute(AttributeBuilder.build(IDLE_TIMEOUT.name(), user.getRoomSettings().getIdleTimeout()))
                .addAttribute(AttributeBuilder.build(DEFAULT_LAYOUT.name(), user.getRoomSettings().getDefaultLayout()))
                .addAttribute(AttributeBuilder.build(PLAY_AUDIO_ALERTS.name(), user.getRoomSettings().getPlayAudioAlerts()))
                .addAttribute(AttributeBuilder.build(PERSONAL_MEETING_ID.name(), user.getRoomSettings().getPersonalMeetingId()))
                .addAttribute(AttributeBuilder.build(MODERATOR_PASSCODE.name(), user.getRoomSettings().getModeratorPasscode()))
                .build();
    }
}
