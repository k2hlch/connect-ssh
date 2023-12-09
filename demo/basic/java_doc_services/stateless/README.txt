Demo for Stateless Java Document Web Services
 
 The client invokes two operations on the service class - displayElement and processElement using an xml element (obtained from input.xml) . 
 displayElement  simply displays the element received on the serverside .
 processElement  converts the received element using the styleshet converter.xsl and sends back the converted element.

Step 1: Compile the service interface and service class 

        Make sure your class path contains the following jar files --

        $ORACLE_HOME/webservices/lib/wsdl.jar:$ORACLE_HOME/lib/xmlparserv2.jar:$ORACLE_HOME/soap/lib/soap.jar:$ORACLE_HOME/j2ee/home/lib/mail.jar:$ORACLE_HOME/j2ee/home/lib/http_client.jar:$ORACLE_HOME/j2ee/home/lib/activation.jar:$JAVA_HOME/lib/tools.jar:$ORACLE_HOME/j2ee/home/lib/jndi.jar
        
        %mkdir classes
        %javac -d classes/ src/*.java
        

Step 2: Create an EAR file using WebServicesAssembler .

        open config.xml , look for 'http://localhost:8888', and change its value to point to your http listener server.

        %java -jar $SRCHOME/webservices/lib/WebServicesAssembler.jar -config config.xml 


Step 3: Deploy the generated ear file (docws.ear)

 - In an iAS environment ,use the following command to deploy the ear file :

% dcmctl deployApplication -file statelessdocws.ear -a StatelessDocService 

(it will deploy the application to the 'home' instance ,refer to 'OC4J - Users Guide' for more det
ails on dcmctl)

 - For a standalone OC4J version , do the following to deploy and bind:

% java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port admin welcome -deploy \
  -file statelessdocws.ear -deploymentName StatelessDocService


% java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port admin welcome -bindWebApp \
  StatelessDocService statelessdocws_web http-web-site /statelessdocws

Step 4:  View the service endpoint page

 Use you browser to open the url - http://<host>:<port>/statelessdocws 
 view the wsdl by clicking on 'service description' (http://localhost:port/statelessdocws/docservice?WSDL) to make sure that the service has been deployed without any errors.



Step 5: make sure that the proxy StatelessDoc_proxy.jar is generated in ./proxy .If not , download the same from "http://localhost:port/statelessdocws/docservice?proxy_jar" and save it in ./proxy
       

Step 7: Compile client
       %javac -classpath .:proxy/StatelessDoc_proxy.jar:$CLASSPATH ./client/StatelessDocClient.java     

Step 8: Run the Client
       %java -classpath .:proxy/StatelessDoc_proxy.jar:./client:$CLASSPATH  StatelessDocClient

Observe the output on the server , on the client side you should see the converted xml :

O/p should be as follows:

<employee>
   <name>Bob</name>
   <phone>827 644 5674</phone>
   <name>Susan</name>
   <phone>827 644 5674</phone>
</employee>
...
DEMO PASSED..!

You can edit input.xml and the converter.xsl and run the demo again to get a different result  
 
Step 8:
You can run through all the above steps by executing 
  %ant 

Make sure that the $CLASSPATH is set as in above
if necessary ,before running ant ,make changes to build.xml with the appropriate property values for  :

        <property name="ormi.url" value="ormi://localhost"/>
        <property name="root.context" value="/statelessdocws"/>
        <property name="user.id" value="admin"/>
        <property name="password" value="welcome"/>
        <property name="web.site" value="http-web-site"/>


