This sample demonstrates how Services can access SOAP-headers.

Note : substitute host:port by appropriate values .

Make sure 

  a. You have the database up and running.
  b. your default data-source is properly defined .Specifically you should have a <data-source> for "jdbc/OracleCoreDS" within $J2EE_HOME/config/data-sources.xml, with its 'url' attribute pointing to the running  db .
  a. your classpath contains the following jar files --

$ORACLE_HOME/soap/lib/wsdl.jar:$ORACLE_HOME/lib/xmlparserv2.jar:$ORACLE_HOME/soap/lib/soap.jar:$ORACLE_HOME/j2ee/home/lib/mail.jar:$ORACLE_HOME/j2ee/home/lib/http_client.jar:$ORACLE_HOME/j2ee/home/lib/activation.jar:$JAVA_HOME/lib/tools.jar:$ORACLE_HOME/webservices/lib/wsserver.jar:$ORACLE_HOME/jdbc/lib/classes12dms.jar:$ORACLE_HOME/j2ee/home/lib/jndi.jar

  
Instructions:

0. 
  cd <demo directory>/basic/header_demo

1. Compile the Java classes to be exposed as iAS Web Services.

 % javac -d src/ src/*.java

2. Packaging the iAS Web Services Archive file.

 Note:open config.xml , search for 'http://localhost:8888' and change it to point to your http listener

% java -jar $ORACLE_HOME/webservices/lib/WebServicesAssembler.jar -config ./config.xml

 This generates the header_demo.ear file which contains the following
files when expanded --

 header_demo.ear
 |---META-INF
 |   `---application.xml      
 `---ws_example_web.war
     |---index.html 
     `---WEB-INF
         |------web.xml
         `------classes
                `------ Employee.class
                        EmployeeImpl.class
				

 Employee.java as the Service interface definition and
 EmployeeImpl.java as the corresponding implementation.

 web.xml contains the required configurations to publish 
 Employee and EmployeeImpl as Java web services.

 It also creates the proxy needed to invoke the service under ./proxy

3. Deploy the iAS Web Services Archive file.

 - In an iAS environment ,use the following command to deploy the ear file :

 % dcmctl deployApplication -file  header_demo.ear -a hdemo 

 (it will deploy the application to the 'home' instance ,refer to 'OC4J - Users Guide' for more details on dcmctl)

 - For a standalone OC4J version , do the following:
 
 % java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port username password
 -deploy -file header_demo.ear -deploymentName hdemo_app

 % java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port username password 
 -bindWebApp hdemo_app  header_demo_web default-web-site /hdemo

 If default-web-site doesn't exist, bind it to the appropriate website.
 the ORMI port is not the same as HTTP port 

 Use you browser to open the url - http://<host>:<port>/hdemo , click on the link of the exposed service .You will reach the  'service endpoint page' of that service , which will have  the links to  operations  exposed as well as other webservice 'artifacts'(wsdl,proxy etc).

 The operation 'getEmployeeSalary' can not be invoked directly from here , because 3  crucial pieces of information - datasource name , username and password are provided through the SOAP header only.


4.In the mod_oc4j.conf file, add a mount line --

 Oc4jMount /web_services.
 Note : ignore this step if running standalone oc4j.

5. Build your client to invoke the web service .

 A sample client is provided under  client/EmployeeClient.java

6. Compile/execute your client.
 
 % javac -classpath .:./proxy/Employee_proxy.jar:$CLASSPATH client/EmployeeClient.java
 % java -classpath .:./proxy/Employee_proxy.jar:./client:$CLASSPATH EmployeeClient

 You will see result like "Salary of MILLER is: 1300".

You can run all the above steps by 

 % ant 

 The build script is written for standalone oc4j with 'localhost' being the server address   .

NOTE :
For Win NT/2000 users ,
  use ';' instead of ':' between different classpath entries when setting classpath.
  environment variables are represented as %ORACLE_HOME% instead of $ORACLE_HOME .

