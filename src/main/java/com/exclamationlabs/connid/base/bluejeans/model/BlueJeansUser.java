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

import com.exclamationlabs.connid.base.connector.model.IdentityModel;

public class BlueJeansUser implements IdentityModel {

    private String firstName;
    private String lastName;
    private String middleName;
    private Long id;
    private Boolean isEnterpriseAdmin;
    private String uri;
    private String email;
    private String username;

    private String password;
    private String company;
    private String title;
    private String emailId;

    private Long enterpriseJoinDate;

    private BlueJeansUserRoomSettings roomSettings;

    @Override
    public String getIdentityIdValue() {
        return "" + getId();
    }

    @Override
    public String getIdentityNameValue() {
        return getEmail() == null ? getEmailId() : getEmail();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnterpriseAdmin() {
        return isEnterpriseAdmin;
    }

    public void setEnterpriseAdmin(Boolean enterpriseAdmin) {
        isEnterpriseAdmin = enterpriseAdmin;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getEnterpriseJoinDate() {
        return enterpriseJoinDate;
    }

    public void setEnterpriseJoinDate(Long enterpriseJoinDate) {
        this.enterpriseJoinDate = enterpriseJoinDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public BlueJeansUserRoomSettings getRoomSettings() {
        return roomSettings;
    }

    public void setRoomSettings(BlueJeansUserRoomSettings roomSettings) {
        this.roomSettings = roomSettings;
    }

    @Override
    public boolean equals(Object input) {
        return identityEquals(BlueJeansUser.class, this, input);
    }

    @Override
    public int hashCode() {
        return identityHashCode();
    }

}
