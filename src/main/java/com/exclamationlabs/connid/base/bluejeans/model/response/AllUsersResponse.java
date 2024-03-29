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

import com.exclamationlabs.connid.base.bluejeans.model.BlueJeansUser;

import java.util.Set;

public class AllUsersResponse {

    private Integer count;

    private Set<BlueJeansUser> users;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Set<BlueJeansUser> getUsers() {
        return users;
    }

    public void setUsers(Set<BlueJeansUser> users) {
        this.users = users;
    }
}
