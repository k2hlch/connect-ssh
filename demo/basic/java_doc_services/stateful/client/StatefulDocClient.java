import org.w3c.dom.*;
import oracle.xml.parser.v2.*;

/* Client program that uses proxy generated for stateful 
 *  java doc web services
 */

public class StatefulDocClient{

  public static void main (String [] args)
  {
    Element e ;
    Element customer;
    try {
      // Create a  XML element customer (String name,String empId,String creditBalance)
      customer = getCustomerElement("Bob","ID123","1000");
      
      // Create an instance of proxy : StatefulDocProxy
      StatefulDocProxy dp = new StatefulDocProxy();
     
      System.out.println("Initially the element is: ");
      ((XMLElement)customer).print(System.out);
      
      System.out.println("Calling startShopping ...");
      dp.startShopping(customer);
      
      System.out.println("Calling makePurchase  3 times...");      
      for(int x=0;x<3;x++){
        e = dp.makePurchase();
        if (e != null) {
          //print out the element's contents
          System.out.println("element is: ");
          ((XMLElement) e).print(System.out);
          }
        }
        
      System.out.println("DEMO PASSED..!");
      
    } catch (Exception ex) {
    
       ex.printStackTrace();
       
    }
  }
  
 /**
  * Returns an Element with the customer name  , customer ID and balance amount
  * eg like :
  * 
  * <s:customer>
  *   <s:name>Bob</s:name>
  *   <s:cust_id>ID123</s:cust_id>
  *   <s:balance_amount>1000</s:balance_amount>
  * </s:customer>
  * 
  */
 static Element getCustomerElement(String name,String custId,String creditBalance){
      Document  doc = new XMLDocument();
      Element elAdd = doc.createElementNS("http://customer.org", "s:customer");
      Element   elA = doc.createElementNS("http://customer.org", "s:name");
      Element   elB = doc.createElementNS("http://customer.org", "s:cust_id");
      Element   elC = doc.createElementNS("http://customer.org", "s:balance_amount");
      elA.appendChild(doc.createTextNode(name));
      elB.appendChild(doc.createTextNode(custId));
      elC.appendChild(doc.createTextNode(creditBalance));
      elAdd.appendChild(elA);
      elAdd.appendChild(elB);
      elAdd.appendChild(elC);
      doc.appendChild(elAdd);
      return doc.getDocumentElement();
   }

   
}
