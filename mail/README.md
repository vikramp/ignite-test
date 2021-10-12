mail: E-Mail Example using CDI (Contexts and Dependency Injection) and JSF (JavaServer Faces)
================================================================================================
Author: Joel Tosi
Level: Beginner
Technologies: JavaMail, JSF, CDI
Summary: Demonstrates the use of JavaMail
Target Project: WildFly
Source: <https://github.com/wildfly/quickstart/>

What is it?
-----------

This example demonstrates sending email with the use of *CDI 1.0* and *JSF 2.0* in *JBoss WildFly*.

The example uses the default Mail provider that comes out of the box with *JBoss WildFly*.  It uses your local mail relay and the default SMTP port of 25.

The configuration of the mail provider is found in the `JBOSS_HOME/standalone/configuration/standalone.xml` if you are running a standalone server or in the `JBOSS_HOME/domain/configuration/domain.xml` file if you are running in a managed domain. An example of the mail subsystem XML configuration is provided below:

    <subsystem xmlns="urn:jboss:domain:mail:1.0">
        <mail-session jndi-name="java:jboss/mail/Default" >
            <smtp-server address="localhost" port="25"/>
        </mail-session>
        <mail-session jndi-name="java:/MyOtherMail">
            <smtp-server address="localhost" port="9999">
                <login name="nobody" password="pass"/>
            </smtp-server>
            <pop3-server address="example.com" port="1234"/>
            <imap-server address="example.com" port="432">
                <login name="nobody" password="pass"/>
            </imap-server>
        </mail-session>
    </subsystem>

The example is a web application that takes `To`, `From`, `Subject`, and `Message Body` input and sends mail to that address. The front end is a JSF page with a simple POJO backing, leveraging CDI for resource injection.

System requirements
-------------------

All you need to build this project is Java 7.0 (Java SDK 1.7) or better, Maven 3.1 or better.

The application this project produces is designed to be run on JBoss WildFly.

 
Configure Maven
---------------

If you have not yet done so, you must [Configure Maven](../README.md#mavenconfiguration) before testing the quickstarts.


Start JBoss WildFly with the Web Profile
-------------------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat

 
Build and Deploy the Quickstart
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Build and Deploy the Quickstarts](../README.md#buildanddeploy) for complete instructions and additional options._

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package wildfly:deploy

4. This will deploy `target/wildfly-mail.war` to the running instance of the server.


Access the application 
---------------------

The application will be running at the following URL: <http://localhost:8080/wildfly-mail>.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn wildfly:undeploy


Run the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------
You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md#useeclipse) 


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc
