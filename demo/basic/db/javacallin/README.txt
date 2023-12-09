This directory demonstrates publishing Java class in the server into webservices.

1. Prepare the Database
=======================

If the database used is earlier than 10i production release, you need to load ${ORACLE_HOME}/sqlj/lib/sqljutl.jar into the database. E.g., on Solaris, 

% loadjava -u sys/change_on_install -r -v -s -f -grant public  ${ORACLE_HOME}/sqlj/lib/sqljutl.jar 

In addition, if the database is 9i or earlier, then you need to run ${ORACLE_HOME}/sqlj/lib/sqljutl.sql against the SYS schema. 

2. Download utl_dbws_jserver.jar 
================================
Download utl_dbws_jserver.jar from Oracle OTN site

http://otn.oracle.com/sample_code/tech/java/jsp/dbwebservices.html

and copy that jar file to the ORACLE_HOME/sqlj/lib directory.

You may also download Oracle SQLJ Translator and Oracle JPublisher 10g 
from the site

http://otn.oracle.com/software/tech/java/sqlj_jdbc/index.html

to get the sqlj/lib/utl_dbws_jserver.jar file.

3. Prepare the OC4J Instance
============================

Modify the J2EE_HOME/data-sources.xml file so that the data-source 
"jdbc/OracleDS" points to the running database, for instance,

        <data-source
                class="com.evermind.sql.DriverManagerDataSource"
                name="OracleDS"
                location="jdbc/OracleCoreDS"
                xa-location="jdbc/xa/OracleXADS"
                ejb-location="jdbc/OracleDS"
                connection-driver="oracle.jdbc.driver.OracleDriver"
                username="scott"
                password="tiger"
                url="jdbc:oracle:thin:@localhost:1521:sqlj"
                inactivity-timeout="30"
        />

4. Start OC4J
=============

% java -jar oc4j.jar

5. Prepare config.xml 
=============================
Modify config.xml, so that the <db-conn/> element points 
to the running database, for instance,
      <db-conn>jdbc:oracle:thin:@localhost:1521:sqlj</db-conn>

6. Run the demo
===============
% java -jar $SRCHOME/webservices/lib/WebServicesAssembler.jar -config config.xml

% java -jar $J2EE_HOME/admin.jar ormi://localhost admin welcome -deploy -file javacallin.ear -deploymentName javacallin

% java -jar $J2EE_HOME/admin.jar ormi://localhost admin welcome -bindWebApp javacallin javacallin_web http-web-site /webservices1

Access the URL
http://locahost:8888/webservices1/javacallin?invoke=to_string

which returns

 Oracle JDBC driver 10.0, classes12

