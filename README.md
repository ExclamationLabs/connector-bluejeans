# BlueJeans Connector

Open source Identity Management connector for [BlueJeans](https://www.bluejeans.com/)

Now leverages the [Connector Base Framework](https://github.com/ExclamationLabs/connector-base)

Developed and tested in [Midpoint](https://evolveum.com/midpoint/), but also could be utilized in any [ConnId](https://connid.tirasa.net/) framework. 

## Introductory Notes/Software Limitations

- This software is Copyright 2020 Exclamation Labs.  Licensed under the Apache License, Version 2.0.

- Groups do not exist and are not manageable for the BlueJeans connector.  This connector manages users only. 

- For OAuth2, I discovered I had to use password authentication in order for the
authentication process to return the user id (needed to obtain the enterprise Id and call the API).  Other
authentication methods could be used, but you probably would have to add the administrator user id
as a configuration property in order to get it to work.


## Getting started

- For a developer account, [Fill out a contact form](https://support.bluejeans.com/contact) and request access. You will receive an email
 back with your username and temporary password (which you will need to change)
 
- Once you have the password changed and are logged in, your can use your [home page](https://bluejeans.com/scheduling/) 
for managing users and further setup.

- Go to Admin -> OAuth Access -> Add New App.  Add Name, Description and App Key

- You can user the web UI for managing users - Admin -> Manage Users
 
- [REST API dpcumentation](https://bluejeans.github.io/api-rest-meetings/site/index.html)  
        
## Midpoint configuration

See XML files in src/test/resources folder for Midpoint examples.  resourceOverlay.xml is an example
resource configuration setup for Midpoint.

## Configuration properties
 
- CONNECTOR_BASE_CONFIGURATION_ACTIVE - Set this to Y to activate the configuration 
 
- CONNECTOR_BASE_AUTH_OAUTH2_TOKEN_URL - Currently is https://api.bluejeans.com/oauth2/token\#User

- CONNECTOR_BASE_AUTH_OAUTH2_ENCODED_SECRET - Not used for this connector, this can be NA

- CONNECTOR_BASE_AUTH_OAUTH2_USERNAME - This is the username given to you after
you receive a developer account with BlueJeans.

- CONNECTOR_BASE_AUTH_OAUTH2_PASSWORD - The password for your developer account.


