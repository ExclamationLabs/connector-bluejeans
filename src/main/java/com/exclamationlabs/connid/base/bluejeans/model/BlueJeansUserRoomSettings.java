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

package com.exclamationlabs.connid.base.bluejeans.model;

public class BlueJeansUserRoomSettings {

    private String numericId;
    private String name;
    private Integer originPopId;
    private Boolean isLargeMeeting;
    private Boolean showVideoAnimations;
    private String backgroundImage;
    private Boolean isModeratorLess;
    private String welcomeMessage;
    private Boolean disallowChat;
    private String encryptionType;
    private Boolean showAllParticipantsInIcs;
    private String participantPasscode;
    private Boolean publishMeeting;
    private Boolean moderatorLess;
    private Boolean videoBestFit;
    private Boolean muteParticipantsOnEntry;
    private Boolean enforceMeetingEncryption;
    private Boolean enforceMeetingEncryptionAllowPSTN;
    private Integer idleTimeout;
    private String defaultLayout;
    private Boolean playAudioAlerts;
    private Integer personalMeetingId;
    private String moderatorPasscode;

    public String getNumericId() {
        return numericId;
    }

    public void setNumericId(String numericId) {
        this.numericId = numericId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOriginPopId() {
        return originPopId;
    }

    public void setOriginPopId(Integer originPopId) {
        this.originPopId = originPopId;
    }

    public Boolean getLargeMeeting() {
        return isLargeMeeting;
    }

    public void setLargeMeeting(Boolean largeMeeting) {
        isLargeMeeting = largeMeeting;
    }

    public Boolean getShowVideoAnimations() {
        return showVideoAnimations;
    }

    public void setShowVideoAnimations(Boolean showVideoAnimations) {
        this.showVideoAnimations = showVideoAnimations;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Boolean getModeratorLess() {
        return isModeratorLess;
    }

    public void setModeratorLess(Boolean moderatorLess) {
        isModeratorLess = moderatorLess;
    }

    public Boolean getVideoBestFit() {
        return videoBestFit;
    }

    public void setVideoBestFit(Boolean videoBestFit) {
        this.videoBestFit = videoBestFit;
    }

    public Boolean getMuteParticipantsOnEntry() {
        return muteParticipantsOnEntry;
    }

    public void setMuteParticipantsOnEntry(Boolean muteParticipantsOnEntry) {
        this.muteParticipantsOnEntry = muteParticipantsOnEntry;
    }

    public Boolean getEnforceMeetingEncryption() {
        return enforceMeetingEncryption;
    }

    public void setEnforceMeetingEncryption(Boolean enforceMeetingEncryption) {
        this.enforceMeetingEncryption = enforceMeetingEncryption;
    }

    public Boolean getEnforceMeetingEncryptionAllowPSTN() {
        return enforceMeetingEncryptionAllowPSTN;
    }

    public void setEnforceMeetingEncryptionAllowPSTN(Boolean enforceMeetingEncryptionAllowPSTN) {
        this.enforceMeetingEncryptionAllowPSTN = enforceMeetingEncryptionAllowPSTN;
    }

    public Integer getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public String getDefaultLayout() {
        return defaultLayout;
    }

    public void setDefaultLayout(String defaultLayout) {
        this.defaultLayout = defaultLayout;
    }

    public Boolean getPlayAudioAlerts() {
        return playAudioAlerts;
    }

    public void setPlayAudioAlerts(Boolean playAudioAlerts) {
        this.playAudioAlerts = playAudioAlerts;
    }

    public Integer getPersonalMeetingId() {
        return personalMeetingId;
    }

    public void setPersonalMeetingId(Integer personalMeetingId) {
        this.personalMeetingId = personalMeetingId;
    }

    public String getModeratorPasscode() {
        return moderatorPasscode;
    }

    public void setModeratorPasscode(String moderatorPasscode) {
        this.moderatorPasscode = moderatorPasscode;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public Boolean getDisallowChat() {
        return disallowChat;
    }

    public void setDisallowChat(Boolean disallowChat) {
        this.disallowChat = disallowChat;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public Boolean getShowAllParticipantsInIcs() {
        return showAllParticipantsInIcs;
    }

    public void setShowAllParticipantsInIcs(Boolean showAllParticipantsInIcs) {
        this.showAllParticipantsInIcs = showAllParticipantsInIcs;
    }

    public String getParticipantPasscode() {
        return participantPasscode;
    }

    public void setParticipantPasscode(String participantPasscode) {
        this.participantPasscode = participantPasscode;
    }

    public Boolean getPublishMeeting() {
        return publishMeeting;
    }

    public void setPublishMeeting(Boolean publishMeeting) {
        this.publishMeeting = publishMeeting;
    }
}
