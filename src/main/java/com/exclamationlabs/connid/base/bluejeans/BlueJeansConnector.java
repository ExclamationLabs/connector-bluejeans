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

import com.exclamationlabs.connid.base.bluejeans.adapter.BlueJeansUsersAdapter;
import com.exclamationlabs.connid.base.bluejeans.authenticator.BlueJeansAuthenticator;
import com.exclamationlabs.connid.base.bluejeans.configuration.BlueJeansConfiguration;
import com.exclamationlabs.connid.base.bluejeans.driver.rest.BlueJeansDriver;
import com.exclamationlabs.connid.base.connector.BaseFullAccessConnector;

import com.exclamationlabs.connid.base.connector.authenticator.Authenticator;
import org.identityconnectors.framework.spi.ConnectorClass;

@ConnectorClass(displayNameKey = "bluejeans.connector.display", configurationClass = BlueJeansConfiguration.class)
public class BlueJeansConnector extends BaseFullAccessConnector<BlueJeansConfiguration> {

    public BlueJeansConnector() {
        super(BlueJeansConfiguration.class);
        setAuthenticator((Authenticator) new BlueJeansAuthenticator());
        setDriver(new BlueJeansDriver());
        setAdapters(new BlueJeansUsersAdapter());
    }

}
