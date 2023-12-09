
Demo for Stateful Java Document Web Services

Step 1: Compile the service interface and service class 

        Make sure your class path contains the following jar files --

        $ORACLE_HOME/webservices/lib/wsdl.jar:$ORACLE_HOME/lib/xmlparserv2.jar:$ORACLE_HOME/soap/lib/soap.jar:$ORACLE_HOME/j2ee/home/lib/mail.jar:$ORACLE_HOME/j2ee/home/lib/http_client.jar:$ORACLE_HOME/j2ee/home/lib/activation.jar:$JAVA_HOME/lib/tools.jar:$ORACLE_HOME/j2ee/home/lib/jndi.jar
        
        %mkdir classes
        %javac -d classes/ src/*.java
        

Step 2:  Create an EAR file using WebServiceAssembler .

        open config.xml , look for 'http://localhost:8888', and change its value to point to your http listener server.

        %java -jar $SRCHOME/webservices/lib/WebServicesAssembler.jar -config config.xml 


Step 3: Deploy the generated ear file (docws.ear)

 - In an iAS environment ,use the following command to deploy the ear file :

% dcmctl deployApplication -file docws.ear -a StatefulDocService

(it will deploy the application to the 'home' instance ,refer to 'OC4J - Users Guide' for more det
ails on dcmctl)

 - For a standalone OC4J version , do the following to deploy and bind:

% java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port admin welcome -deploy \
  -file docws.ear -deploymentName StatefulDocService


% java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port admin welcome -bindWebApp \
  StatefulDocService docws_web http-web-site /statefuldocws

Step 4:  View the service endpoint page

 Use you browser to open the url - http://<host>:<port>/statefuldocws 
 view the wsdl by clicking on 'service description' (http://localhost:port/statefuldocws/docservice?WSDL) to make sure that the service has been deployed without any errors.



Step 5: make sure that the proxy StatefulDoc_proxy.jar is generated in ./proxy .If not , download the same from "http://localhost:port/statefuldocws/docservice?proxy_jar" and save it in ./proxy
       

Step 7: Compile client
       %javac -classpath .:proxy/StatefulDoc_proxy.jar:$CLASSPATH ./client/StatefulDocClient.java     

Step 8: Run the Client
       %java -classpath .:proxy/StatefulDoc_proxy.jar:./client:$CLASSPATH  StatefulDocClient

O/p should be as follows:

Initially the element is:
 <s:customer>
   <s:name>Bob</s:name>
   <s:cust_id>CS120</s:cust_id>
   <s:balance_amount>1000</s:balance_amount>
 </s:customer>
Calling startShopping ...
...

...
DEMO PASSED..!

Observe the decrease in  <s:balance_amount> by 100 in each subsequent calls to makePurchase operation

Step 8:
You can run through all the above steps by simply  executing 
        %ant 

Make sure that the $CLASSPATH is set as above
if necessary ,before running ant ,make changes to build.xml with the appropriate property values for  :

        <property name="ormi.url" value="ormi://localhost"/>
        <property name="root.context" value="/statefuldocws"/>
        <property name="user.id" value="admin"/>
        <property name="password" value="welcome"/>
        <property name="web.site" value="http-web-site"/>


