This directory demonstrates publishing a query into a webservice.

1. Prepare the Database 
=======================
The demo requires the present of the EMP table in the database. The table is created during installtion for the SCOTT schema in Oracle databases. To create the EMP table in a database, use the script ${ORACLE_HOME}/bin/demobld.

2. Prepare the OC4J Instance
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
                url="jdbc:oracle:oci8"
                inactivity-timeout="30"
        />

4. Start OC4J
=============

% java -jar oc4j.jar

5. Prepare config.xml 
=============================

5.1. JDBC Connection String
Modify config.xml, so that the <db-conn/> element points 
to the running database, for instance,
      <db-conn>jdbc:oracle:thin:@localhost:1521:sqlj</db-conn>

NOTE: Do forget to change from localhost to the actual host of the database.

5.2. WSDL Endpoint
Modify config.xml, so the wsdl-gen httpServerURL points to 
the URL where the webservice will be deployed, for instance,

<option name="httpServerURL">http://dsunrde22.us.oracle.com:8888</option>

NOTE: Do forget to change from localhost to the actual host of the OC4J instance.


6. Run the demo
===============

Assemble and deploy the application
% setenv CLASSPATH ${ORACLE_HOME}/soap/lib/soap.jar:${CLASSPATH}

% java -jar ${ORACLE_HOME}/webservices/lib/WebServicesAssembler.jar -config config.xml

% java -jar ${J2EE_HOME}/admin.jar ormi://localhost admin welcome -deploy -file sqlstatement.ear -deploymentName sqlstatement

% java -jar ${J2EE_HOME}/admin.jar ormi://localhost admin welcome -bindWebApp sqlstatement sqlstatement_web http-web-site /webservices1

Access the URL
http://locahost:8888/webservices1/sqlstatement?getEmpBySalBeans&param0=500

which returns such information as below.

<?xml version='1.0' encoding='UTF-8'?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
<SOAP-ENV:Body>
<ns1:getEmpBySalBeansResponse xmlns:ns1="http://oracle.demo.db.sqlstatement/SqlStmts.wsdl" SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
<return xmlns:ns2="http://schemas.xmlsoap.org/soap/encoding/" xsi:type="ns2:Array" xmlns:ns3="http://oracle.demo.db.sqlstatement/SqlStmts.xsd" ns2:arrayType="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser[14]">
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">SMITH</ename>
<sal xsi:type="xsd:decimal">800</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">ALLEN</ename>
<sal xsi:type="xsd:decimal">1600</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">WARD</ename>
<sal xsi:type="xsd:decimal">1250</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">JONES</ename>
<sal xsi:type="xsd:decimal">2975</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">MARTIN</ename>
<sal xsi:type="xsd:decimal">1250</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">BLAKE</ename>
<sal xsi:type="xsd:decimal">2850</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">CLARK</ename>
<sal xsi:type="xsd:decimal">2450</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">SCOTT</ename>
<sal xsi:type="xsd:decimal">3000</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">KING</ename>
<sal xsi:type="xsd:decimal">5000</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">TURNER</ename>
<sal xsi:type="xsd:decimal">1500</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">ADAMS</ename>
<sal xsi:type="xsd:decimal">1100</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">JAMES</ename>
<sal xsi:type="xsd:decimal">950</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">FORD</ename>
<sal xsi:type="xsd:decimal">3000</sal>
</item>
<item xsi:type="ns3:oracle_demo_db_sqlstatement_SqlStmtsUser_getEmpBySalRowUser">
<ename xsi:type="xsd:string">MILLER</ename>
<sal xsi:type="xsd:decimal">1300</sal>
</item>
</return>
</ns1:getEmpBySalBeansResponse>

</SOAP-ENV:Body>
</SOAP-ENV:Envelope>


