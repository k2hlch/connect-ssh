This sample demonstrates a Hotel reservation service

 scenario :

   A service provider has developed a simple hotel reservation system .It allows
for any number of users to reserve a room in a hotel for a number of days , with
logic to verify if the rooms are indeed available .To keep matters simple , each
hotel is assumed to have only one room  .

The main classes to look at :

  -com.oracle.demo.Hotel.java  (contains operations to do the reservations). 
  -com.oracle.demo.HotelAdmin.java  (contains operations to administer the database ).

  The service provider decides to expose some operations/methods from the above 
two classes as webservices .He packages the application in an ear as well as 
generates wsdl's and proxies .Steps 0 to 7 walks you through that .
 Steps 8 - 9 walks you through the process of writing a simple consumer with a web
 front-end for that service. 





Instructions:

0. cd $ORACLE_HOME/webservices/demo/advanced/hotel_reservation
Make sure your class path contains the following jar files --

$ORACLE_HOME/webservices/lib/wsdl.jar:$ORACLE_HOME/lib/xmlparserv2.jar:$ORACLE_HOME/soap/lib/soap.jar:$ORACLE_HOME/j2ee/home/lib/http_client.jar:$ORACLE_HOME/jdbc/lib/classes12.jar:$ORACLE_HOME/j2ee/home/lib/jndi.jar


1. Compile the Java classes to be exposed as iAS Web Services.

%mkdir classes
%javac -d classes/ src/*.java


2. Package the Web Services Archive file and create proxies and wsdls.

  Open the  config.xml file ,search for 'http://localhost:8888' and change it  
to the URL of the http listener of 9iAS .
  Make sure that 'mail.jar' and 'activation.jar' are present in your <JDK_HOME>/jre/lib/ext/


  % java -jar $ORACLE_HOME/webservices/lib/WebServicesAssembler.jar -config ./config.xml

 This generates the 

 a. ./lib/Hotel-ws.ear file .

 b. The wsdl and keeps it in ./wsdl as well as packaging  the same in the ear file . 

 c. Creates the proxies for client side invocation and keeps them in ./proxy

It exposes two classes Hotel.java and HotelAdmin.java as web service.The methods
exposed are determined by the interfaces IHotel and IHotelAdmin respectively .



3. Set up the Database Tables .
   Create two tables : 	hotel_main and hotel_room with the following schema :

   hotel_main;
	 Name                                             Type
	 ----------------------               -----------------
	 HOTELNAME                                 VARCHAR2(20)
	 CITY                                      VARCHAR2(25)
	 STATE                                     CHAR(2)
	 TYPE                                      VARCHAR2(10)
	 RATE                                      NUMBER(5,2)
	 AMENETIES                                 VARCHAR2(50)
	 ADDRESS                                   VARCHAR2(30)

   hotel_room
	 Name                                      Type
	 ----------------------               -----------------
	 HOTELNAME                                 VARCHAR2(20)
	 CITY                                      VARCHAR2(25)
	 STATE                                     CHAR(2)
	 ROOMTYPE                                  CHAR(5)
	 CHECKIN                                   DATE
	 CHECKOUT                                  DATE
	 BOOKEDBY                                  VARCHAR2(30)


The above tables can be created by the following SQL commands
through SQLPLUS or any other database client 
   
	
SQLPLUS%> create table HOTEL_MAIN ( HOTELNAME   VARCHAR2(20), CITY VARCHAR2(25),STATE CHAR(2),
TYPE VARCHAR2(10),RATE    NUMBER(5,2), AMENETIES  VARCHAR2(50), ADDRESS VARCHAR2(30));

SQLPLUS%> create table hotel_room (HOTELNAME VARCHAR2(20), CITY VARCHAR2(25),STATE CHAR(2),
ROOMTYPE CHAR(5),CHECKIN DATE, CHECKOUT DATE ,BOOKEDBY  VARCHAR2(30));

4.Mount  a data-source entry in $ORACLE_HOME/j2ee/home/config/data-sources.xml to point 
to your database :
    
    <data-source
                class="oracle.jdbc.pool.OracleDataSource"
                name="OracleHotelDS"
                location="jdbc/OracleHotelDS"
                pooled-location="jdbc/OracleHotelDS"
                url="jdbc:oracle:thin:@<machine name>:<port>:<sid>"
                username="SCOTT"
                password="TIGER"
                min-connections="3"
                max-connections="10"
                wait-timeout="10"
                inactivity-timeout="30"
        />

 note : change the 'url' according to the db you are connecting to
        change the 'username' and 'password' if different from default .

5. Start up OC4J/9iAS and Deploy the iAS Web Services Archive file.



 - In an iAS environment ,use the following command to deploy the ear file :

% dcmctl deployApplication -file $ORACLE_HOME/webservices/demo/hotel_reservation/lib/Hotel-ws.ear -a HotelService

(it will deploy the application to the 'home' instance ,refer to 'OC4J - Users Guide' for more 
details on dcmctl)

 - For a standalone OC4J version , do the following:

% java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port admin welcome -deploy \
  -file ./lib/Hotel-ws.ear -deploymentName HotelService
% java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port admin welcome -bindWebApp \
  HotelService Hotel-ws_web http-web-site /HotelService

If default-web-site doesn't exist, bind it to the appropriate website(say ..http-web-site)


6.In the mod_oc4j.conf file, add a mount line --

 Oc4jMount /HotelService .
	
	
	
7. View the service endpoint page

 Use you browser to open the url - http://<host>:<port>/HotelService , click on the links of
 each exposed service .You will reach the  'service endpoint page' of that service , which will
 have the links to  operations  exposed as well as other webservice 'artifacts'(wsdl,proxy etc).

 view the wsdl by clicking on 'service description'  to make sure that the service has been 
deployed without any errors.
	
	
 
8. Build the  consumer for your Web Service
by simply :

% ant
    
        
   A consumer 'Consumer.ear' is created in './lib/' which is nothing but a package of the Proxies
 created in step two and Jsps as frontend.

  
9 . Deploy the consumer in the same 9iAS  instance or another instance .

 - For a standalone OC4J version , do the following:

% java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port admin welcome -deploy \
 -file  ./lib/Consumer.ear -deploymentName ConsumerDeploy

% java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port admin welcome -bindWebApp \
  ConsumerDeploy Consumer http-web-site /Consumer

Bring up the Administration pages from your browser by

  http://host:port/Consumer/HotelAdmin.jsp

use it to update the database with some hotels - click on 'add a new hotel',add the hotels 
(Make sure not to add any special characters as "'" in any text fields)
 Then click on 'look at hotel_main table' to verify that the hotels have been added .

Bring up  the reservation pages by :

  http://host:port/Consumer

And try to reserve the hotel.






