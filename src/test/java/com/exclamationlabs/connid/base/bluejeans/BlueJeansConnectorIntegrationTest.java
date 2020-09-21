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

import com.exclamationlabs.connid.base.bluejeans.attribute.BlueJeansGroupAttribute;
import com.exclamationlabs.connid.base.bluejeans.configuration.BlueJeansConfiguration;
import com.exclamationlabs.connid.base.connector.configuration.ConfigurationNameBuilder;
import com.exclamationlabs.connid.base.connector.test.IntegrationTest;
import com.exclamationlabs.connid.base.connector.test.util.ConnectorTestUtils;
import org.apache.commons.lang3.StringUtils;
import org.identityconnectors.framework.common.exceptions.AlreadyExistsException;
import org.identityconnectors.framework.common.exceptions.ConnectorException;
import org.identityconnectors.framework.common.objects.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

import static com.exclamationlabs.connid.base.bluejeans.attribute.BlueJeansUserAttribute.*;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlueJeansConnectorIntegrationTest extends IntegrationTest {

    private BlueJeansConnector connector;

    private static String generatedUserId;
    private static long randomSuffix;

    static {
        Random random = new Random();
        randomSuffix = random.nextLong();
    }

    @Override
    public String getConfigurationName() {
        return new ConfigurationNameBuilder().withConnector(() -> "BLUEJEANS").build();
    }

    @Before
    public void setup() {
        connector = new BlueJeansConnector();
        setup(connector, new BlueJeansConfiguration(getConfigurationName()));
    }

    @Test
    public void test010TestEnterpriseId() {
        connector.test();
    }


    @Test
    public void test110UserCreate() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new AttributeBuilder().setName(FIRST_NAME.name()).addValue("Freddy" + randomSuffix).build());
        attributes.add(new AttributeBuilder().setName(LAST_NAME.name()).addValue("Flinstone").build());
        attributes.add(new AttributeBuilder().setName(MIDDLE_NAME.name()).addValue("Francis").build());
        attributes.add(new AttributeBuilder().setName(COMPANY.name()).addValue("Hanna Barbera").build());
        attributes.add(new AttributeBuilder().setName(TITLE.name()).addValue("Construction Worker").build());
        attributes.add(new AttributeBuilder().setName(PASSWORD.name()).addValue("fred1234" +  + randomSuffix).build());

        attributes.add(new AttributeBuilder().setName(USERNAME.name()).addValue("fredflin" +  + randomSuffix).build());
        attributes.add(new AttributeBuilder().setName(EMAIL_ID.name()).addValue("fred" +  + randomSuffix + "@rubble.com").build());

        attributes.add(new AttributeBuilder().setName(MODERATOR_PASSCODE.name()).addValue("4455").build());
        attributes.add(new AttributeBuilder().setName(PARTICIPANT_PASSCODE.name()).addValue("6677").build());
        attributes.add(new AttributeBuilder().setName(WELCOME_MESSAGE.name()).addValue("Welcome!!!").build());

        Uid newId = connector.create(ObjectClass.ACCOUNT, attributes, new OperationOptionsBuilder().build());
        assertNotNull(newId);
        assertNotNull(newId.getUidValue());
        generatedUserId = newId.getUidValue();
    }

    @Test(expected=AlreadyExistsException.class)
    public void test111UserCreateDupe() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new AttributeBuilder().setName(FIRST_NAME.name()).addValue("Freddy" + randomSuffix).build());
        attributes.add(new AttributeBuilder().setName(LAST_NAME.name()).addValue("Flinstone").build());
        attributes.add(new AttributeBuilder().setName(MIDDLE_NAME.name()).addValue("Francis").build());
        attributes.add(new AttributeBuilder().setName(COMPANY.name()).addValue("Hanna Barbera").build());
        attributes.add(new AttributeBuilder().setName(TITLE.name()).addValue("Construction Worker").build());
        attributes.add(new AttributeBuilder().setName(PASSWORD.name()).addValue("fred1234" +  + randomSuffix).build());

        attributes.add(new AttributeBuilder().setName(USERNAME.name()).addValue("fredflin" +  + randomSuffix).build());
        attributes.add(new AttributeBuilder().setName(EMAIL_ID.name()).addValue("fred" +  + randomSuffix + "@rubble.com").build());

        attributes.add(new AttributeBuilder().setName(MODERATOR_PASSCODE.name()).addValue("4455").build());
        attributes.add(new AttributeBuilder().setName(PARTICIPANT_PASSCODE.name()).addValue("6677").build());
        attributes.add(new AttributeBuilder().setName(WELCOME_MESSAGE.name()).addValue("Welcome!!!").build());

        connector.create(ObjectClass.ACCOUNT, attributes, new OperationOptionsBuilder().build());
    }

    @Test
    public void test120UserModify() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new AttributeBuilder().setName(MIDDLE_NAME.name()).addValue("Bedrock").build());
        attributes.add(new AttributeBuilder().setName(PARTICIPANT_PASSCODE.name()).addValue("8899").build());

        Uid newId = connector.update(ObjectClass.ACCOUNT, new Uid(generatedUserId), attributes, new OperationOptionsBuilder().build());
        assertNotNull(newId);
        assertNotNull(newId.getUidValue());
    }

    @Test
    public void test130UsersGet() {
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
        List<String> idValues = new ArrayList<>();
        List<String> nameValues = new ArrayList<>();
        ResultsHandler resultsHandler = ConnectorTestUtils.buildResultsHandler(idValues, nameValues);

        connector.executeQuery(ObjectClass.ACCOUNT, generatedUserId, resultsHandler, new OperationOptionsBuilder().build());
        assertEquals(1, idValues.size());
        assertTrue(StringUtils.isNotBlank(idValues.get(0)));
    }

    @Test(expected=ConnectorException.class)
    public void test210GroupCreate() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new AttributeBuilder().setName(BlueJeansGroupAttribute.GROUP_NAME.name()).addValue("Flinstones").build());
        connector.create(ObjectClass.GROUP, attributes, new OperationOptionsBuilder().build());
    }

    @Test(expected=ConnectorException.class)
    public void test220GroupModify() {
        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new AttributeBuilder().setName(BlueJeansGroupAttribute.GROUP_NAME.name()).addValue("Flinstones2").build());

        connector.update(ObjectClass.GROUP, new Uid("123"), attributes, new OperationOptionsBuilder().build());
    }

    @Test(expected=ConnectorException.class)
    public void test230GroupsGet() {
        List<String> idValues = new ArrayList<>();
        List<String> nameValues = new ArrayList<>();
        ResultsHandler resultsHandler = ConnectorTestUtils.buildResultsHandler(idValues, nameValues);

        connector.executeQuery(ObjectClass.GROUP, "", resultsHandler, new OperationOptionsBuilder().build());
    }

    @Test(expected=ConnectorException.class)
    public void test240GroupGet() {
        List<String> idValues = new ArrayList<>();
        List<String> nameValues = new ArrayList<>();
        ResultsHandler resultsHandler = ConnectorTestUtils.buildResultsHandler(idValues, nameValues);

        connector.executeQuery(ObjectClass.GROUP, "123", resultsHandler, new OperationOptionsBuilder().build());
    }

    @Test(expected=ConnectorException.class)
    public void test290GroupDelete() {
        connector.delete(ObjectClass.GROUP, new Uid("123"), new OperationOptionsBuilder().build());
    }

    @Test
    public void test390UserDelete() {
        connector.delete(ObjectClass.ACCOUNT, new Uid(generatedUserId), new OperationOptionsBuilder().build());
    }

}