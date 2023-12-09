#!/bin/sh
echo "This script for running the demo in Unix shell environment"
echo "Steps for running the test:"
echo "(1) Update config.xml with the correct database connection"
echo "(2) Add OracleDS entry in j2ee/home/data-sources.xml" 
echo "(3) Run this script "

CLASSPATH=".:${ORACLE_HOME}/j2ee/lib/mail.zip:${ORACLE_HOME}/j2ee/activation.jar:${ORACLE_HOME}/webservices/lib/wsdl.jar:${ORACLE_HOME}/lib/xmlparserv2.jar:${ORACLE_HOME}/soap/lib/soap.jar:${CLASSPATH}"
export CLASSPATH
echo ${CLASSPATH}

echo "Create SQL type and PL/SQL packages in the database "
sqlplus scott/tiger @drop.sql
sqlplus scott/tiger @create.sql

echo "Generate the server code. "
${ORACLE_HOME}/jdk/bin/java -jar ${ORACLE_HOME}/webservices/lib/WebServicesAssembler.jar -config config.xml

echo "Re-start OC4J ... You may need to adjust the sleeping time if the server does not go up soon enought for the client to call"
ps -ef | grep oc4j.jar | awk -e '{print $2}' |xargs kill -9
sleep 10 
DIR=`pwd`; cd ${ORACLE_HOME}/j2ee/home; 
${ORACLE_HOME}/jdk/bin/java -jar oc4j.jar &  
cd ${DIR}
sleep 50 

echo "Undeploy previously installed webservice... "
${ORACLE_HOME}/jdk/bin/java -jar ${ORACLE_HOME}/j2ee/home/admin.jar  ormi://localhost admin welcome -undeploy SPDEMO

echo "Deploy... http://<localhost>:8888/webservices1/SPDEMO"
${ORACLE_HOME}/jdk/bin/java -jar ${ORACLE_HOME}/j2ee/home/admin.jar ormi://localhost admin welcome -deploy -file ./spexample.ear  -deploymentName SPDEMO 
${ORACLE_HOME}/jdk/bin/java -jar ${ORACLE_HOME}/j2ee/home/admin.jar ormi://localhost admin welcome -bindWebApp SPDEMO spexample_web http-web-site /webservices1

echo "Run the client ..."
ant client 
