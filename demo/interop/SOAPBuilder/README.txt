This demo illustrate the basic concept and best practices you should follow to 
build Web Services endpoints that can easily interop with other toolkits.

pre-requisite:
- familiarity with the basic Web Services demo.

For simplicity, all the java code is provided with this demo. Some of the source
file provided could have been generated from the predefined WSDL files.

Detailed specification of the test can be found online.
- Round II  endpoints specification is located at 
  http://www.whitemesa.net/interop/proposal2.html
- Round III endpoints specification is located at 
  http://www.whitemesa.net/r3/plan.html

This sample is composed of three parts, the server endpoints, the client stub and
the client testing framework.

The demo is assuming that the Ant tool is available and that the ORACLE_HOME 
environment variable is properly set (platform specific).

Files and Directories structure :
build.xml   - the antfile, used to compile, package, deploy and run the service as
              well as to run the client test framework.
config.xml  - the configuration file to build the soapbuiler.ear application file.
README.txt  - this file.
src         - the root directory for all the java source files, used in this demo.
build       - the root directory for all the generated files.
public_html - the root directory for the static content we want to append to our 
              final application.

Ant targets :
clean      - Remove any generated file and directory.

setup      - Create build directory structure.

compile    - Compile the Service Interfaces, Classes and utilities.

package    - Produce soapbuilder.ear in the current directory, using the 
             WebServiceAssembler utility. This target also make sure that all the 
             static content and the required customization are done.

deploy     - Deploy the application (soapbuilder.ear) and bind the web application 
             (soapbuilder_web.war). This target assumes that the container is 
             installed in standalone mode and uses the default port setup (8888).
             If you are using another port, you should change the value of the 
             'app.host_port' property in the ant script build.xml.
             If you are using Oracle 9iAS, you should be using the dcmctl utility 
             to deploy the application or Oracle EM user interface. If you need to
             deploy the application with dcmctl, the syntax of the command will be:
               dcmctl deployApplication -f  soapbuilder.ear -a soapbuilder

remote_deploy
           - Deploy the application on a remote target, with a user-defined 
             hostname. This target assumes that the remote container is installed in
             standalone mode. 
             This target is made available to deploy the application with a
             soap:address location attribute not derived from the environment variable
             HOST. This target must be used with 2 explicit properties:
               target.url : the hostname part of the URL, to embed inside WSDL files.
               admin.url  : the hostname part of the URL, to use with admin.jar.
             syntax: 
             ant remote_deploy -Dtarget.url=ws-interop.oracle.com -Dadmin.url=erajkovic-pc

local_run  - Execute the client test driver using the local endpoint URI
             http://localhost:8888/soapbuilder/r2/InteropTest
             If you are not using the default port setup (8888), you will need to eding 
             TestDriver.java before to run this target.

remote_run - Execute the client test driver for each live endpoint registered
             on the public SOAPBuilder Round 2 registry.

notes :

- the dependencies between the targets can be found in build.xml.

- to verify that the application has been deployed successfuly, you can point a 
  browser to the following URI:
    http://localhost:8888/soapbuilder/ - list the endpoints available.
    http://localhost:8888/soapbuilder/r2/InteropTest - InteropTest endpoint home page.

- by default, admin account is disabled with the standalone version. If this is the 
  first application you are trying to deploy, you may want to install it first using:
    java -jar oc4j.jar -install.

- the ant files assumes that you are running using oc4j standalone on the same host.
  If you are running oc4j embeded in Oracle 9iAS, you should use the appropriate
  tool, like Oracle EM (cf. appropriate documentation).
  If you are running oc4j standalone on a remote host, you can overwrite
  the ormi.url property in build.xml.
  If you are not using the default values for the rmi port, the admin account name or
  password, you should also modify the build.xml file accordingly.

local run, verbose mode:
    Running against Local Oracle 9iAS...
    echoVoid                  OK
    echoString                OK
    echoString                OK
    echoString                OK
    echoString                OK
    echoStringArray           OK
    echoInteger               OK
    echoInteger               OK
    echoInteger               OK
    echoIntegerArray          OK
    echoFloat                 OK
    echoFloat                 OK
    echoFloat                 OK
    echoFloat                 OK
    echoFloat                 OK
    echoFloatArray            OK
    echoStruct                OK
    echoStructArray           OK
    echoDate                  OK
    echoDecimal               OK
    echoBoolean               OK
    echoBoolean               OK
    Local Oracle 9iAS - 0 failure for 22 test cases (http://localhost:8888/soapbuild
    er/r2/InteropTest)
    Overall result : 22/22

To generate the proxy used in the demo to access the list of endpoints registered, you can use the 
following command :
java -classpath $J2EE_HOME/oc4j.jar -Dhttp.proxyHost=YourProxyHostNameHere -Dhttp.proxyPort=80 \
  oracle.wsdl.toolkit.ProxyGenerator \
  http://www.whitemesa.net/wsdl/interopInfo.wsdl ./src/ -k org.soapbuilder

