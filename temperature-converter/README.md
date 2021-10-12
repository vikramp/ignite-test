temperature-converter: Stateless Session EJB
=================================================
Author: Bruce Wolfe
Level: Beginner
Technologies: EJB
Summary: Demonstrates a stateless session bean 
Target Project: WildFly
Source: <https://github.com/wildfly/quickstart/>

What is it?
-----------

This example demonstrates the use of an *EJB 3.1 Stateless Session Bean* and *CDI* to access it via a *JSF*.
Deployment occurs via a war archive for deployment to *JBoss WildFly*.

These are the steps that occur:

1. The user interface is a JSF page that asks for a temperature and a scale (Fahrenheit or Celsius).
2. When you click on `Convert`, the temperature string is passed to the TemperatureConverter controller (managed) bean.
3. The managed bean then invokes the `convert()` method of the injected TemperatureConvertEJB (notice the field annotated with @Inject).
4. The response from TemperatureConvertEJB is stored in the `temperature` field of the managed bean.
5. The managed bean is annotated as @SessionScoped, so the same managed bean instance is used for the entire session.


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

4. This will deploy `target/wildfly-temperature-converter.war` to the running instance of the server.
 

Access the application 
---------------------

The application will be running at the following URL: <http://localhost:8080/wildfly-temperature-converter/>.

You will be presented with a simple form for temperature conversion.

1. Optionally, Select a scale: Celsius or Fahrenheit.
2. Enter a temperature.
3. Press the `Convert` button to see the results.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn wildfly:undeploy


        
Run the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------
You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](../README.md/#useeclipse) 


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

      mvn dependency:sources
      mvn dependency:resolve -Dclassifier=javadoc


