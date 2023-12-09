ant clean
ant compile

#ant create_package
java -jar $ORACLE_HOME/webservices/lib/WebServicesAssembler.jar -config config.xml

#ant update_package
#unzip soapbuilder.ear soapbuilder_web.war
jar xf soapbuilder.ear soapbuilder_web.war

jar uvf soapbuilder_web.war index.html

jar uvf soapbuilder_web.war WEB-INF/web.xml
jar uvf soapbuilder_web.war WEB-INF/classes/oracle/j2ee/ws_example/IInteropTest.wsdl

jar uvf soapbuilder_web.war WEB-INF/classes/oracle/j2ee/ws_example/Import1Service.wsdl
jar uvf soapbuilder_web.war WEB-INF/classes/oracle/j2ee/ws_example/Import2Service.wsdl
jar uvf soapbuilder_web.war WEB-INF/classes/oracle/j2ee/ws_example/Import3Service.wsdl
jar uvf soapbuilder_web.war WEB-INF/classes/oracle/j2ee/ws_example/Compound1Service.wsdl
jar uvf soapbuilder_web.war WEB-INF/classes/oracle/j2ee/ws_example/Compound2Service.wsdl
jar uvf soapbuilder_web.war WEB-INF/classes/oracle/j2ee/ws_example/InteropTestDocLitService.wsdl
jar uvf soapbuilder_web.war WEB-INF/classes/oracle/j2ee/ws_example/InteropTestDocLitParametersService.wsdl

jar uvf soapbuilder.ear soapbuilder_web.war

ant deploy

#ant local_run
java -classpath $ORACLE_HOME/j2ee/home/oc4j.jar:./build/classes -Dhttp.proxyHost=www-proxy.us.oracle.com -Dhttp.proxyPort=80 oracle.j2ee.ws_example.TestDriver -v > ./build/local_run.log

#ant remote_run
java -classpath $ORACLE_HOME/j2ee/home/oc4j.jar:./build/classes -Dhttp.proxyHost=www-proxy.us.oracle.com -Dhttp.proxyPort=80 oracle.j2ee.ws_example.TestDriver -rv > ./build/remote_run.log