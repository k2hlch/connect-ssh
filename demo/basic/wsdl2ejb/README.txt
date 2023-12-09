Running wsdl2ejb Demo Examples

wsdl2ejb demonstrates how to use the wsdl2ejb tool.
All the commands are assumed to be executed from the demo/web_services/wsdl2ejb 
directory. 
The demo will use some sample WSDLs documents as sources and generate 
EJB stubs that can be used to invoke the Web Service operations.

All the demos can be run using ant. Please review the build.xml 
to make sure that its initial properties (RMI_HOST, RMI_PORT,
RMI_ADMIN, RMI_PWD) are set correctly according to your configuration.
The build.xml file will execute the wsdl2ejb tool on the demo WSDLs,
deploy the generated EJB, and execute the EJB clients.
To run both demos, just run ant in the demo/web_services/wsdl2ejb 
directory.

NOTE: if you are executing the demos behind a 
firewall , you will  need to set proxy to access external http sites, such proxy
is  specified in the wsdl2ejb configuration files ( 'rpc_doc/rpc_doc_conf.xml' and 'base/base_conf.xml') .

Note: The demos make use of ejbs , they need the default data-sources entry to be present 
in data-sources.xml for their running .

Note: make sure that your CLASSPATH environment variable has
$ORACLE_HOME/lib/dsv2.jar and $ORACLE_HOME/j2ee/home/lib/ejb.jar

Note: The  demo is  based on WSDL/SOAP interoperability test suites.
They access live SOAP services available on the Internet as SOAP
interoperability test cases. The successful execution of these demos 
depends on the availability of these services.

Make sure that the following wsdl is  accessible from the web and the services are up for the running .

 http://mssoapinterop.org/asmx/DocAndRpc.asmx?wsdl

Directory structure:

demo/web_services/wsdl2ejb:
  - README.txt                : this file
  - build.xml                 : jakarta ant build file to run all the demos 
  - rpc_doc                   : directory for simple Rpc and document style operations
       - rpc_doc_conf.xml          : wsdl2ejb configuration file for the rpc_doc demo
       - TestRpcDocClient.java     : client for the rpc_doc demo
       - DocAndRpc.wsdl            : sample WSDL for the rpc_doc demo
       - (generated)               : directory where the EJB will be generated



 Rpc and Document Style with simple types
 ~~~~~~~~~~~~~~~~~~~~~
 Follow these 4 simple steps to run this demo:


1) To run this demo only, run "ant rpc_doc_cl" in the 
demo/web_services/wsdl2ejb directory.

The first example uses a simple WSDL exposing a couple of operations: 
Add and Multiply. Add is using the document-style operation using literal
parts while Multiply is rpc-style and uses encoded parts.

2) To generate the stub EJB, use the following command:

%cd $ORACLE_HOME/webservices/demo/basic/wsdl2ejb
%java -jar $ORACLE_HOME/webservices/lib/wsdl2ejb.jar -conf rpc_doc/rpc_doc_conf.xml

The utility generates the TestApp.ear file containing the definition
of a stateless EJB, which can be used as a proxy for the Web Service.

3) Deploy the EAR file Oracle 9iAS oc4j as any standard EJB.(with deployment name 'Wsdl2EjbTestApp1')
 - In an iAS environment ,use the following command to deploy the ear file :

% dcmctl deployApplication -file TestApp.ear -a Wsdl2EjbTestApp1

(it will deploy the application to the 'home' instance ,refer to 'OC4J - Users
 Guide' for more det
ails on dcmctl)

 - For a standalone OC4J version , do the following to deploy :

% java -jar $ORACLE_HOME/j2ee/home/admin.jar ormi://host:port admin welcome -d
eploy -file TestApp.ear -deploymentName Wsdl2EjbTestApp


Please refer to the Oracle 9iAS release 2 documentation for examples on how to deploy an EJB.

By looking at the generated EJB remote interface you can see how the WSDL portType 
has been mapped to Java in the InteropTest.wsdl file.

WSDL PortType:

  <types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://soapinterop.org">
      <s:element name="Add">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="a" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="b" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="AddResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="AddResult" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
    </s:schema>
  </types>
  <message name="AddSoapIn">
    <part name="parameters" element="s0:Add" /> 
  </message>
  <message name="AddSoapOut">
    <part name="parameters" element="s0:AddResponse" /> 
  </message>
  <message name="MultiplySoapIn">
    <part name="a" type="xsd:int" /> 
    <part name="b" type="xsd:int" /> 
  </message>
  <message name="MultiplySoapOut">
    <part name="MultiplyResult" type="s:int" /> 
  </message>
  <portType name="TestSoap">
    <operation name="Add">
      <input message="s0:AddSoapIn" /> 
      <output message="s0:AddSoapOut" /> 
    </operation>
    <operation name="Multiply">
      <input message="s0:MultiplySoapIn" /> 
      <output message="s0:MultiplySoapOut" /> 
    </operation>
  </portType>


From the Test.java file, the EJB Remote Interface is:

  public org.w3c.dom.Element add(org.w3c.dom.Element parameters)
    throws RemoteException;
 
  public int multiply(int a, int b)
    throws RemoteException;


When the WSDL operation is using rpc style and its parts are encoded,
the parts XML schema type is mapped to a corresponding Java native 
type. In this example, xsd:int is mapped to Java int.
In a document style using literal parts, each part is simply mapped to
a org.w3c.dom.Element.

4.) The following client code in the TestRpcDocClient.java file  can be used 
to invoke the Add and Multiply Web Service operations. The code has been 
produced by modifying the client code stub generated by the wsdl2ejb tool.

import java.io.*;
import java.util.*;
import javax.naming.*;

import org.w3c.dom.*;
import oracle.xml.parser.v2.*;

import org.mssoapinterop.asmx.Test;
import org.mssoapinterop.asmx.TestHome;


/**
 * This is a simple client template. To compile it, 
 * please include the generated EJB jar file as well as
 * EJB and JNDI libraries in classpath.
 */
public class TestRpcDocClient
{
  // replace the values
  private static String RMI_HOST  = "localhost";
  private static String RMI_PORT  = "23791";
  private static String RMI_ADMIN = "admin";
  private static String RMI_PWD   = "welcome";

  public TestRpcDocClient () {}

  public static void main(String args[]) {

    TestRpcDocClient client = new TestRpcDocClient();

    try {

      RMI_HOST  = args[0];
      RMI_PORT  = args[1];
      RMI_ADMIN = args[2];
      RMI_PWD   = args[3];

      Hashtable env = new Hashtable();
      env.put(Context.INITIAL_CONTEXT_FACTORY, "com.evermind.server.rmi.RMIInitialContextFactory");
      env.put(Context.SECURITY_PRINCIPAL, RMI_ADMIN);
      env.put(Context.SECURITY_CREDENTIALS, RMI_PWD);
      env.put(Context.PROVIDER_URL, "ormi://" + RMI_HOST + ":" + RMI_PORT + "/Wsdl2EjbTestApp1");
      Context ctx = new InitialContext(env);
      TestHome home = (TestHome) ctx.lookup("mssoapinterop.org/asmx/DocAndRpc.asmx");
      
      Test service = home.create();
      
      // call any of the Remote methods below to access the EJB
      
      //
      // Add test
      //
      Document  doc = new XMLDocument();
      Element elAdd = doc.createElementNS("http://soapinterop.org", "s:Add");
      Element   elA = doc.createElementNS("http://soapinterop.org", "s:a");
      Element   elB = doc.createElementNS("http://soapinterop.org", "s:b");
      elA.appendChild(doc.createTextNode("4"));
      elB.appendChild(doc.createTextNode("3"));
      elAdd.appendChild(elA);
      elAdd.appendChild(elB);
      doc.appendChild(elAdd);
      
      Element elAddResponse = service.add(elAdd);
      Node tNode = elAddResponse.getFirstChild().getFirstChild();
      System.out.println("AddResponse: "+tNode.getNodeValue());

      //
      // Multiply Test
      //
      int a = 4;
      int b = 3;
      int iMultiplyResponse = service.multiply(a, b);
      System.out.println("MultiplyResponse: "+iMultiplyResponse);
      
      
    } 
    catch (Throwable ex) {
      ex.printStackTrace();
    }
  }
} 


The result of the execution of the client will be the following:

AddResponse: 7
MultiplyResponse: 12





