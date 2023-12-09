
Demo for JMS Document Web Services

This demo uses a Queue for "send" and a Topic for "receive" operation. 
It makes use of only one webservices endpoint to do both using "send" and "receive" operations. "receive" uses "reply-to" destination information.

The client sends an xml element to a JMS service and receives the processed xml.The JMS service
 exposes queue QUEUE1 and topic TOPIC1 as web-services .An MDB processes the message from QUEUE1
and publishes it on to TOPIC1

It makes use of Oracle JMS as the resource provider. An Oracle database is needed for Oracle JMS .


--------------------------------------------------------- 
Step 0: Set up the Oracle database
      Run the SQL scripts under ./sql in following sequence to initialize the databasein following sequence to initialize the database.
      
   1.   admin.sql
   2.   createTandQtables.sql
   3.   createTandQ.sql

      run the utility sql script ./sql/viewTables.sql to make sure that the queue and topic tables have been
 properly created.You should see QUEUE1 and TOPIC1 selected out of user_queues
---------------------------------------------------------
Step 1: configure the Application server.

     a) edit  $ORACLE_HOME/j2ee/config/data-sources.xml
        to  mount the following datasource , make appropriate change to the url(username and password 
correspond to the user created in step 0) :

	<data-source
                class="com.evermind.sql.DriverManagerDataSource"
                name="OracleDS"
                location="jdbc/wsDemoEmulatedOracleCoreDS"
                xa-location="jdbc/xa/wsDemoEmulatedOracleXADS"
                ejb-location="jdbc/wsDemoEmulatedDS"
                connection-driver="oracle.jdbc.driver.OracleDriver"
                username="jemuser"
                password="jempasswd"
                url="jdbc:oracle:thin:@<machine>:<port>:<sid>"
                inactivity-timeout="30"
        />

     b) edit $ORACLE_HOME/j2ee/config/application.xml
       	  to mount the following resource provider :

      	<resource-provider class="oracle.jms.OjmsContext" name="wsdemo">
                <description> OJMS/AQ </description>
                <property name="datasource"
                          value="jdbc/wsDemoEmulatedDS">
                </property>
      	</resource-provider>

--------------------------------------------------------

Step 2: To build the deployable  ear as well as the client side proxies  use "ant"

        Make sure your class path contains the following jar files --

        $ORACLE_HOME/webservices/lib/wsdl.jar:$ORACLE_HOME/lib/xmlparserv2.jar:$ORACLE_HOME/soap/lib/soap.jar:$ORACLE_HOME/j2ee/home/lib/mail.jar:$ORACLE_HOME/j2ee/home/lib/http_client.jar:$ORACLE_HOME/j2ee/home/lib/activation.jar:$ORACLE_HOME/j2ee/home/lib/jndi.jar:$ORACLE_HOME/j2ee/home/lib/jms.jar:$ORACLE_HOME/j2ee/home/lib/ejb.jar


       %ant 

       it makes the mdb jar (./lib/mdb_service2.jar) as well as assembles the deployable ear file "./lib/jmsws2.ear".
       Make sure that the proxies are generated in ./proxy 

Step 3: Deploy the generated ear file (jmsws2.ear)with the context root (/jmsws2)

 - In an iAS environment , use the following command to deploy the ear file :

       %dcmctl deployApplication -file jmsws2.ear -a jmsws2

  (it will deploy the application to the 'home' instance ,refer to 'OC4J - Users
 Guide for more details on dcmctl)

 - For a standalone OC4J version , do the following:

       %java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://localhost admin welcome -deploy -file ./lib/jmsws2.ear -deploymentName jmsws2 

  bind the web application

       %java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://localhost admin welcome  -bindWebApp jmsws2 jmsws2_web http-web-site /jmsws2

Step 5: View the service endpoint pages by going to

        http://localhost:port/jmsws2

     and clicking on the available links .Click on the link for wsdl to make sure that the application 
has been deployed without errors


Step 6: Compile client

     
       %javac   -classpath proxy/JmsDemo2_proxy.jar:$CLASSPATH client/JmsDemo2Client.java


Step 7: Run the Client

       %java -classpath client:proxy/JmsDemo2_proxy.jar:$CLASSPATH JmsDemo2Client 

O/p should be as follows:


Doing the Send Operation ..on Element
  <employee>
    <name>Bob</s:name>
    <emp_id>ID123</s:emp_id>
    <position>assistant manager</s:position>
  </employee>

Doing the Receive operation ..
  <employee>
    <name>Bob</s:name>
    <emp_id>ID123</s:emp_id>
    <position>senior manager</s:position>
  </employee>

---------------------------------------------------

Step 9: drop the Queue and Topic tables by running ./sql/delete.sql
