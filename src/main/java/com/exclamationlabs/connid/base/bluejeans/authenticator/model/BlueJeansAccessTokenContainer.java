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

package com.exclamationlabs.connid.base.bluejeans.authenticator.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model holding possible OAuth2 response data.
 * Not all fields may be populated from response, but accessToken
 * should be expected to have a value from an OAuth2 response.
 */
public class BlueJeansAccessTokenContainer {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private Long expiresIn;

    @SerializedName("refresh_token")
    private String refreshToken;

    private ScopeInformation scope;

    public ScopeInformation getScope() {
        return scope;
    }

    public void setScope(ScopeInformation scope) {
        this.scope = scope;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
