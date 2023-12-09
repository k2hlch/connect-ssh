Demo for JMS Document Web Services

This demo uses OC4J/JMS Queues for both "send" and "receive" operations. 

The demo mimics a simple  workflow where one application (JmsMessageOriginator)sends an xml
 element to a JMS service and another application (JmsMessageDestn) receives the processed xml. 
Queues 'The Queue' and 'Log Queue' are exposed as web services .
The xml processing is done using an MDB .All the applications work in an asynchronous fashion . 

This demo needs 2 different endpoints, one to do "send" operation, and other to do "receive"
operation.

 
Step 0: Add the following definitions for the Queues and QueueConnectionFactories in
        $ORACLE_HOME/j2ee/home/config/jms.xml

        <queue name="The Queue" location="jms/theQueue">
		      <description>The queue</description>
	</queue>

        <queue name="Log Queue" location="jms/logQueue">
		      <description>Log queue</description>
	</queue>
        <!-- change 'user' and 'password' attribute values  as appropriate -->
        <queue-connection-factory location="jms/theQueueConnectionFactory" user="admin" password="welcome">
  	      <description>The Queue Connection factory</description>
	</queue-connection-factory>

        <!-- change 'user' and 'password' attribute  values  as appropriate -->
        <queue-connection-factory location="jms/logQueueConnectionFactory" user="admin" password="welcome">
  	      <description>Log Queue Connection factory</description>
	</queue-connection-factory>

Step 1: Compile the MDB classes
        %javac -d MDB/src MDB/src/*.java

        Make sure your class path contains the following jar files --

        $ORACLE_HOME/webservices/lib/wsdl.jar:$ORACLE_HOME/lib/xmlparserv2.jar:$ORACLE_HOME/soap/lib/soap.jar:$ORACLE_HOME/j2ee/home/lib/mail.jar:$ORACLE_HOME/j2ee/home/lib/http_client.jar:$ORACLE_HOME/j2ee/home/lib/activation.jar:$ORACLE_HOME/j2ee/home/lib/jndi.jar:$ORACLE_HOME/j2ee/home/lib/jms.jar:$ORACLE_HOME/j2ee/home/lib/ejb.jar


Step 2: Create an EJB jar file out of it. All necessary deployment information is in MDB/src/META-INF
        %jar -cvf MDB/mdb_service1.jar -C MDB/src .

Step 3: Create an EAR file using WebServiceAssembler
Note: open config.xml , look for 'http://localhost:8888', and change its value
 to point to your http listener server.


        %java -jar $ORACLE_HOME/webservices/lib/WebServicesAssembler.jar -config config.xml 
 
        make sure that the JmsSend_proxy.jar & JmsReceive_proxy.jar are generated in ./proxy . 


Step 4: Deploy (and bind if using admin.jar)the generated ear file (jmsws1.ear)with the context root (/jmsws1)

 - In an iAS environment , use the following command to deploy the ear file :

  %dcmctl deployApplication -file jmsws1.ear -a jmsws1

  (it will deploy the application to the 'home' instance ,refer to 'OC4J - Users
 Guide for more details on dcmctl)

 - For a standalone OC4J version , do the following:

  %java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://localhost admin welcome \
    -deploy -file jmsws1.ear -deploymentName jmsws1

 bind the web application

  %java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://localhost admin welcome \
   -bindWebApp jmsws1 jmsws1_web http-web-site /jmsws1


Step 5: View the service endpoint pages by going to 
    http://localhost:port/jmsws1 
     clicking on the available links .Click on the link for wsdl to make sure that the application has been deployed without errors 

Step 6: Compile clients
       
	%javac   -classpath proxy/JmsSend_proxy.jar:$CLASSPATH client/JmsMessageOriginator.java
	%javac   -classpath proxy/JmsReceive_proxy.jar:$CLASSPATH client/JmsMessageDestn.java


Step 7: Running the Client
	from the existing console 
        %java -classpath client:proxy/JmsSend_proxy.jar:$CLASSPATH JmsMessageOriginator 

        open another console and run  
	%java -classpath client:proxy/JmsReceive_proxy.jar:$CLASSPATH JmsMessageDestn

O/p should be as follows:

 in the console running JmsMessageOriginator  
Doing the Send Operation ..on Element
<employee>
   <name>Bob</name>
   <emp_id>1234</emp_id>
   <position>assistant manager</position>
</employee>

 in the console running JmsMessageDestn  
Element received is: 
<employee>
   <name>Bob</name>
   <emp_id>1234</emp_id>
   <position>senior manager</position>
</employee>

Observe that the  message is received asynchronously at the JmsMessageDestn
and the value of <position> is changed .

