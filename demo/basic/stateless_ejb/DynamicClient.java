import java.util.*;
import java.io.*;
import java.net.*;
import oracle.j2ee.ws.client.*;
import oracle.j2ee.ws.client.wsdl.*;


public class DynamicClient {
    public DynamicClient() {}

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
        wspf.setProperties(ht);
       
        wsp = wspf.createWebServiceProxy(wsdlURL);
        //
        // Invoke the method
        //         
        wsm = wsp.getMethod("hello");
        if (wsm == null)
            throw new NullPointerException("hello operation not available in this service");

        String[] parameterNames = {"param0"};
        String[] parameterValues = {str[1]};

        System.out.println(wsm.invoke(parameterNames, parameterValues)); 

    }

   
}
