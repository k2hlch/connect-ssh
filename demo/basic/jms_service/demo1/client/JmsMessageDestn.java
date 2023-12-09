import org.w3c.dom.*;
import oracle.xml.parser.v2.*;



public class JmsMessageDestn  {

  public static void main (String [] args)
  {
    try {
      
      // Create an instance of the JmsTest1Proxy to receive an XML element  
      JmsReceiveProxy sm1 = new JmsReceiveProxy();
      System.out.println("Doing the Receive operation ..");
      Element e = sm1.receive();
      if (e != null) {
         System.out.println("Element received is: " );
         ((XMLElement)e).print(System.out);
      }
    } catch (Exception e) { 
         e.printStackTrace(); 
    }
  }
}
