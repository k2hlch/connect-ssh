This sample demonstrates how to publish a stateful Java web service
and a stateless Java web service.

Note : substitute host:port by appropriate values .

Instructions:

0. cd <demo directory>/basic/java_services

1. Compile the Java classes to be exposed as iAS Web Services.

% javac -d src/ src/oracle/j2ee/ws_example/*.java

2. Packaging the iAS Web Services Archive file.

% java -jar $ORACLE_HOME/webservices/lib/WebServicesAssembler.jar -config ./config.xml

This generates the ws_example.ear file which contains the following
files when expanded --

ws_example.ear
|---META-INF
|   `---application.xml      
`---ws_example_web.war
    |---index.html 
    `---WEB-INF
        |------web.xml
        `------classes
               `------oracle
                      `-----j2ee
                            `---ws_example
                                |---StatefulExample.java
                                |---StatefulExample.class
                                |---StatefulExampleImpl.java
                                `---StatefulExampleImpl.class
                                |---StatelessExample.java
                                |---StatelessExample.class
                                |---StatelessExampleImpl.java
                                `---StatelessExampleImpl.class
	                    	|---BeanExample.java
                                |---BeanExample.class
                                |---BeanExampleImpl.java
                                `---BeanExampleImpl.class
				
BeanExample.java is an Java interface class which defines 
the Java methods to be exposed as an Stateful web service 
dealing with JavaBeans. 

BeanExampleImpl.java is an implementation of the BeanExample
interface.

StatefulExample.java is an Java interface class which defines 
the Java methods to be exposed as an Stateful web service. 

StatefulExampleImpl.java is an implementation of the StatefulExample
interface.

StatelessExample.java is an Java interface class which defines 
the Java methods to be exposed as an Stateless web service. 

StatelessExampleImpl.java is an implementation of the StatelessExample
interface.

web.xml contains the required configurations to publish 
StatefulExample and StatelessExample as Java web services.

3. Deploy the iAS Web Services Archive file.


 - In an iAS environment ,use the following command to deploy the ear file :

% dcmctl deployApplication -file  ws_example.ear -a ws_example  

(it will deploy the application to the 'home' instance ,refer to 'OC4J - Users Guide' for more details on dcmctl)

 - For a standalone OC4J version , do the following:

% java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port username password
 -deploy -file ws_example.ear -deploymentName ws_example

% java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port username password 
-bindWebApp ws_example ws_example_web default-web-site /webservices

If default-web-site doesn't exist, bind it to the appropriate website.
the ORMI port is not the same as HTTP port 

Use you browser to open the url - http://<host>:<port>/webservices , click on the links of each exposed service .You will reach the  'service endpoint page' of that service , which will have  the links to  operations  exposed as well as other webservice 'artifacts'(wsdl,proxy etc).

Try the individual operations and view the resulting SOAP messages .


4.In the mod_oc4j.conf file, add the mount line if it doesn't already exist:
  (This line should have been automatically added if dcmctl is used.
   Will only be needed behind Oracle HTTP Server and when admin.jar
   is used to deploy the application)

 Oc4jMount /web_services.

5. Access the WSDL.

For the stateless service, point your browser to 
http://host:port/webservices/statelessTest?wsdl
and save the file under wsdl directory.

For the stateful service, point your browser to
http://host:port/webservices/statefulTest?wsdl
and save the file under wsdl directory.

6. Access the client side stub files.

For the stateless service, point your browser to 
http://host:port/webservices/statelessTest?proxy_jar
and save the file as StatelessExample_proxy.jar under ./client directory.

For the stateful service, point your browser to
http://host:port/webservices/statefulTest?proxy_jar
and save the file as StatefulExample_proxy.jar under ./client directory.


For the stateful bean service, point your browser to 
http://host:port/webservices/beanTest?proxy_jar
and save the file (bean_proxy.jar) under ./client directory.


7. Build your Web Services client.for Stateful Web Service

Create a Client.java file under ./client directory as follows --

import oracle.j2ee.ws_example.proxy.*;

public class Client
{
  public static void main(String[] argv) throws Exception
  {
    StatefulExampleProxy proxy = new StatefulExampleProxy();
    System.out.println(proxy.helloWorld("Scott"));
    System.out.println(proxy.count());
    System.out.println(proxy.count());
    System.out.println(proxy.count());
  }
}

StatefulExampleProxy class is included in the jar file(s) 
downloaded above. It is the Web Services stub file generated by
iAS. 

8. Compile/execute your client.
Make sure your class path contains the following jar files --

$ORACLE_HOME/webservices/lib/wsdl.jar:$ORACLE_HOME/lib/xmlparserv2.jar:$ORACLE_HOME/soap/lib/soap.jar:$ORACLE_HOME/j2ee/home/lib/mail.jar:$ORACLE_HOME/j2ee/home/lib/http_client.jar:$ORACLE_HOME/j2ee/home/lib/activation.jar:$ORACLE_HOME/j2ee/home/lib/ejb.jar:$JAVA_HOME/lib/tools.jar:$ORACLE_HOME/j2ee/home/lib/jndi.jar:$ORACLE_HOME/lib/dsv2.jar
 

For stateful service client --

% cd client
% javac -classpath .:StatefulExample_proxy.jar:$CLASSPATH Client.java
% java -classpath .:StatefulExample_proxy.jar:$CLASSPATH Client


You will see result like "Hello World, Scott 0 1 2".



9) For stateless service client use StatelessClient.java

% cd client
% javac -classpath .:StatelessExample_proxy.jar:$CLASSPATH StatelessClient.java
% java -classpath .:StatelessExample_proxy.jar:$CLASSPATH StatelessClient


You will see result like "Hello World, Scott" and "Hello World, Wendy".


10) For stateful service with bean example

 javac -classpath .:./bean_proxy.jar:${CLASSPATH} BeanClient.java
 java -classpath .:./bean_proxy.jar:${CLASSPATH} BeanClient

You will see result like "DEMO PASSED";

Once you deploy the application and copy the proxy jar files to the client
directory, you can make use of the build.xml file provided by simply running "ant".

This concludes example of client using static-proxy to invoke a webservice .

11)Make sure that the classpath is set as in above and dsv2.jar is in the classpath
If you are behind a firewall , run the dynamic proxy clients using -Dhttp.proxyHost=<your-host> -Dhttp.proxyPort=<your-port> option

For statefull service client using dynamic proxy invocation.


%cd dynamicproxy
%javac StatefulClient.java
%java  -classpath .:$CLASSPATH StatefulClient "http://host:port/webservices/statefulTest?wsdl"


you should see the same result as above : "Hello World, Scott 0 1 2".

12) For stateless service client using dynamic proxy invocation. 


%javac StatelessClient.java
%java  -classpath .:$CLASSPATH StatelessClient "http://host:port/webservices/statelessTest?wsdl"



you should see the same result as above : "Hello World, Scott" and "Hello World, Wendy".

13) For Bean service  client using dynamic proxy invocation .Make sure that the TestBean.class exists at 
../src/oracle/j2ee/ws_example/


%javac -classpath .:../src/:$CLASSPATH BeanClient.java
%java  -classpath .:../src/:$CLASSPATH BeanClient "http://host:port/webservices/beanTest?wsdl"


you should see the same result as above : "DEMO PASSED";


NOTE :
For Win NT/2000 users ,
  use ';' instead of ':' between different classpath entries when setting classpath.
  environment variables are represented as %ORACLE_HOME% instead of $ORACLE_HOME .

