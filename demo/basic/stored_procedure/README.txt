INSTRUCTIONS TO INSTALL AND RUN STORED PROCEDURE WEB SERVICE

Explanation:
------------

This service represents a simple company that manages employees.
Clients are provided to add, get, and remove an employee, as well as to
get an employee's address, get some of the information on an employee,
and to change an employee's salary. 

The demo also demonstrated support for additional BOOLEAN, TIMESTAMP, 
PL/SQL RECORD, and PL/SQL TABLE types. For PL/SQL RECORD 
and TABLE types, a PL/SQL wrapper package is generated, which provides 
mapping between PL/SQL to SQL types. The mapping is necessary because
the underlying JDBC connection does not support PL/SQL types.

The demo shows how SYS.XMLTYPE is supported in webservices. SYS.XMLTYPE is mapped into org.w3c.dom.DocumentFragment. The demo exposes two methods related to SYS.XMLTYPE, i.e.,

  procedure set_emp_accounts(id number, accounts sys.xmltype);
  function get_emp_accounts(id number) returns sys.xmltype;

The two methods above are exposed as Java methods:

  void setEmpAccounts(java.math.BigDecimal, org.w3c.dom.DocumentFragment);
  org.w3c.dom.DocumentFragment getEmpAccounts(java.math.BigDecimal);

The XMLTYPE part of the demo excercise the two methods above, using DocumentFragment to access SYS.XMLTYPE instance. 

IMPORTANT:
Make sure that 'mail.jar' and 'activation.jar' are present in your <JDK_HOME>/jre/lib/ext/

Database Setup
--------------
If you are using a DB release earlier than 9.2.0.1 or your DB is not 
Java-enabled then you need to install the SYS.SQLJUTIL package in order 
to get support for PL/SQL BOOLEAN arguments. The PLSQL script that defines 
this package is located at ${ORACLE_HOME}/sqlj/lib/sqljutl.sql

(We shall use "scott/tiger" as the standard database user/password in
these examples; change them as appropriate for your system).

Create the package, types, and procedures on the database.
 
% sqlplus scott/tiger@<DB_CONNECT_STRING>  @create.sql

Server Setup
------------

1) SP Web service packaging
a) update the following parameters: 
   - In the jar-generation section of the provided config.xml file:
	 <db_url> --Database connection URL
         <schema> --Database schema
   - In the wsdl-gen section
         <httpServerUrl> --the url of the web service endpoint

b) Set the classpath up as follows: $ORACLE_HOME/webservices/lib/wsdl.jar:$ORACLE_HOME/lib/xmlparserv2.jar:$ORACLE_HOME/soap/lib/soap.jar
 
c)Run WebServicesAssembler.jar on supplied config.xml. The output is a webservices EAR file (spexample.ear) :

% java -jar $ORACLE_HOME/webservices/lib/WebServicesAssembler.jar -config ./config.xml
  Please wait ...
  SCOTT.COMPANY
  SCOTT.EMPLOYEE
  SCOTT.ADDRESS
  SCOTT."COMPANY.MANAGER_TABLE"
  sp.company.CompanyUser__getEmpInfo_Out
  sp.company.CompanyUser__changeEmpSalary_Out
  SCOTT."COMPANY.MANAGER_REC"
  J2T-138, NOTE: Wrote PL/SQL package JPUB_PLSQL_WRAPPER to file webservices9_wrapper.sql. Wrote the dropping script to file webservices9_dropper.sql

This step also generates WSDL and proxy code in the wsdl and proxy directories respectively.

d) Run the generated PL/SQL wrapper

% sqlplus scott/tiger @webservices9_wrapper.sql

2) Set up data sources in OC4J by configuring data-sources.xml. This step points OC4J to a database that has your SP package loaded.  An example data-source element (Please replace the hostname, port number and oracle SID in database connection url with appropriate values of your backend database). 

!!! NOTE !!! 
The default emulated data-source with "ejb-location" attribute will not work for the LOB cases. Rather the "location" attribute must be used instead, as shown:
!!! NOTE !!! 

<data-source
     class="com.evermind.sql.DriverManagerDataSource"
     name="OracleDS"
     location="jdbc/OracleDS"
     connection-driver="oracle.jdbc.driver.OracleDriver"
     username="scott"
     password="tiger"
     url="jdbc:oracle:thin:@localhost:1521:oracle"
     inactivity-timeout="30"
/>

3) Startup OC4J and deploy spexample.ear

In an iAS environment (it will deploy the application to the 'home' instance , refer to 'OC4J - Users Guide' for more details on dcmctl)

% cd $ORACLE_HOME/dcm/bin
% dcmctl deployApplication -file $ORACLE_HOME/webservices/demo/basic/stored_procedure/spexample.ear -a SPDEMO

In a standalone OC4J environment

% cd $ORACLE_HOME/j2ee/home
% java -jar admin.jar ormi://<host>:<port> <username> <password> -deploy -file $ORACLE_HOME/webservices/demo/basic/stored_procedure/spexample.ear -deploymentName SPDEMO
% java -jar admin.jar ormi://<host>:<port> <username> <password> -bindWebApp SPDEMO spexample_web http-web-site /webservices1

Compile and Run the Client:
--------------------------

This step compiles the client TestSP.java and then runs client (uses 
the proxy code generated at service generation time):

% ant client

Expected Result
---------------
     [echo] Executing Client...
     [java] Successfully added an employee
     [java] TIMESTAMP test before: Tue May 06 01:54:37 PDT 2003, after: Tue May 06 01:42:51 PDT 2003
     [java] BOOLEAN Test returned true
     [java] SW 5Th Ave. Portland OR 97204
     [java] Tony XYZ 1181000.0
     [java] SW 5Th Ave. Portland OR 97204
     [java] Successfully removed an employee
     [java] *** Start PL/SQL RECORD and TABLE test *** 
     [java] ---- PL/SQL test returns 10,11 
     [java] *** End PL/SQL RECORD and TABLE test ***    
     [java] *** Start XMLTYPE test ***
     [java] ---- set accounts as DocumentFragment 
     [java] ---- get accounts as DocumentFragment
     [java] ---- returns <?xml version = '1.0'?>
     [java] <#document-fragment><account> <name> email </name> <id> john.deer </id> <password> reed.nhoj </password></account></#document-fragment>
     [java] *** End XMLTYPE test ***
     [echo] Executing Client... Done.


Cleanup after running the test:
-------------------------------
1) Drop the types and stored procedures:

% sqlplus scott/tiger@<DB_CONNECT_STRING> @drop.sql
% sqlplus scott/tiger @webservices9_dropper.sql

