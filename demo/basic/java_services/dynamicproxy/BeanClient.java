import java.util.*;
import java.io.*;
import java.net.*;
import oracle.j2ee.ws.client.*;
import oracle.j2ee.ws.client.wsdl.*;

import oracle.j2ee.ws_example.TestBean;
import org.apache.soap.util.xml.QName;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;

import org.apache.soap.encoding.soapenc.BeanSerializer;


public class BeanClient {

    public static void main(String str[]) throws Exception {
        WebServiceProxyFactory wspf;
        WebServiceMethod wsm;
        WebServiceProxy wsp;

        URL wsdlURL = new URL(str[0]);
        //
        // Set your proxy host and port
        //

        Hashtable ht = new Hashtable();

        try {
            ht.put("http.proxyHost", System.getProperty("http.proxyHost"));
            ht.put("http.proxyPort", System.getProperty("http.proxyPort"));
        } catch (NullPointerException npe) {
            //no  property set ,if you are using a http proxy run the client using 
            //-Dhttp.proxyHost=your host -Dhttp.proxyPort=your port option
        }
        //
        // Create an instance of the dynamic proxy
        //

        wspf = new WebServiceProxyFactory();
        if (ht.size() != 0)
            wspf.setProperties(ht);   
        wsp = wspf.createWebServiceProxy(wsdlURL);
     
        //
        // Register the serializer and  deserializer - BeanSerializer with the proxy
        //NOTE the values of :
        //  encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/"
        //  namespaceURI = "http://oracle.j2ee.ws-example/BeanExample.xsd"
        //  local name = "oracle_j2ee_ws_example_TestBean"
        //..are determined by looking at the wsdl , change them if they are different
        //from the one here .
        //


        XMLJavaMappingRegistry xjmap = wsp.getXMLMappingRegistry();
        BeanSerializer beanSer = new BeanSerializer();

        xjmap.mapTypes("http://schemas.xmlsoap.org/soap/encoding/",
            new QName("http://oracle.j2ee.ws-example/BeanExample.xsd", "oracle_j2ee_ws_example_TestBean"),
            TestBean.class,
            beanSer,
            beanSer);

        //
        // get and Invoke the method setTestBean
        //
        wsm = wsp.getMethod("setTestBean");
        if (wsm == null)
            throw new NullPointerException("helloWorld operation not available in this service");
        //
        // set values to the bean
        //

        TestBean testBean = new TestBean();

        testBean.setIntvalue(50);
        testBean.setStringvalue("unctuous");

        String[] parameterNames = {"inputStruct"};
        Object[] parameterValues = {testBean};

        wsm.invoke(parameterNames, parameterValues);

        //
        // get and Invoke the method getTestBean
        //

        wsm = wsp.getMethod("getTestBean");
        if (wsm == null)
            throw new NullPointerException("getTestBean operation not available in this service");

        TestBean returnBean;   

        returnBean = (TestBean) wsm.invoke(null, null);
        if (testBean.equals(returnBean)) {
            System.out.println("..DEMO PASSED !!");
        }

    }

}

