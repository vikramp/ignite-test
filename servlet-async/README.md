servlet-async: How to Write an Asynchronous Servlet
===================================================
Author: Christian Sadilek
Level: Intermediate
Technologies: Servlet, CDI, EJB
Summary: Demonstrates CDI, plus asynchronous Servlets and EJBs
Target Project: WildFly
Source: <https://github.com/wildfly/quickstart/>

What is it?
-----------

This is a sample project showing the use of asynchronous servlets.
 
It shows how to detach the execution of a long-running task from the request processing thread, so the thread is free
to serve other client requests. The long-running tasks are executed using a dedicated thread pool and create the
client response asynchronously.
 
A long-running task in this context does not refer to a computation intensive task executed on the same machine but
could for example be contacting a third-party service that has limited resources or only allows for a limited number
of concurrent connections. Moving the calls to this service into a separate and smaller sized thread pool ensures
that less threads will be busy interacting with the long-running service and that more requests can be served that do
not depend on this service.

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

4. This will deploy `target/wildfly-servlet-async.war` to the running instance of the server.


Access the application 
---------------------

The application will be running at the following URL <http://localhost:8080/wildfly-servlet-async/>.


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

