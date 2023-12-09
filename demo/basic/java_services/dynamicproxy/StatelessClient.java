import java.util.*;
import java.io.*;
import java.net.*;
import oracle.j2ee.ws.client.*;
import oracle.j2ee.ws.client.wsdl.*;


public class StatelessClient {

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
            //no  property set ,if you are using a http proxy , run the client using 
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
        // Get the method and Invoke 
        //    
     
        wsm = wsp.getMethod("helloWorld");
        if (wsm == null)
            throw new NullPointerException("helloWorld operation not available in this service");

        String[] parameterNames = {"param0"};
        String[] parameterValues = {"scott"};
        System.out.println(wsm.invoke(parameterNames, parameterValues)); 

        parameterNames[0] = "param0";
        parameterValues[0] = "wendy";
        System.out.println(wsm.invoke(parameterNames, parameterValues)); 
    }

}
