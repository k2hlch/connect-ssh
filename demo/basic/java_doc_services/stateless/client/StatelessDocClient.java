import org.w3c.dom.*;
import oracle.xml.parser.v2.*;
import java.io.*;


// Client program that uses proxy generated for stateless java document
// web service
public class StatelessDocClient {

  public static void main (String [] args)
  {
    try {      
     
      // Create an instance of the proxy
      StatelessDocProxy sp = new StatelessDocProxy();  
      Element el = null;
      
      el=getDocument();
      // Call the displayElement method on the proxy
      System.out.println("Calling displayElement ...the element is displayed on server");
      sp.displayElement(el);

      // Call the processElement method on the proxy
      System.out.println("Calling processElement ...");
      el = sp.processElement(el);

      if (el != null) {
        System.out.println("The result of processElement is:" );
        ((XMLElement)el).print(System.out);
      System.out.println("DEMO PASSED");
      }
    } catch (Exception e) { 
           e.printStackTrace();
   }
  }

  static Element getDocument() throws Exception{
      // Create a sample XML element    
      DOMParser dp = new DOMParser();
      dp.parse(new FileInputStream("input.xml"));
      XMLDocument dom = dp.getDocument();     
      return dom.getDocumentElement();
  }





}


