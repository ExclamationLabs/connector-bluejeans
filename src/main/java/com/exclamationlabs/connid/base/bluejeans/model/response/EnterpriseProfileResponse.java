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

package com.exclamationlabs.connid.base.bluejeans.model.response;

public class EnterpriseProfileResponse {

    private Long id;
    private Long enterprise;
    private String enterpriseName;
    private Boolean enterpriseAdmin;
    private Long joinDate;
    private Long expiryDate;
    private String customLandingDomain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Long enterprise) {
        this.enterprise = enterprise;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Boolean getEnterpriseAdmin() {
        return enterpriseAdmin;
    }

    public void setEnterpriseAdmin(Boolean enterpriseAdmin) {
        this.enterpriseAdmin = enterpriseAdmin;
    }

    public Long getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Long joinDate) {
        this.joinDate = joinDate;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCustomLandingDomain() {
        return customLandingDomain;
    }

    public void setCustomLandingDomain(String customLandingDomain) {
        this.customLandingDomain = customLandingDomain;
    }
}
