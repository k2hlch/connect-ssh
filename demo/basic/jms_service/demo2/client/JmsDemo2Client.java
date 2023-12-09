import org.w3c.dom.*;
import oracle.xml.parser.v2.*;


public class JmsDemo2Client {

  public static void main (String [] args)
  {
    try {
      
      // Create a sample XML Element
      
      Element elAdd = getEmployeeElement("Bob","1234","assistant manager");
      // Create an instance of the JmsDemo2Proxy to send an XML element  
      JmsDemo2Proxy sm2 = new JmsDemo2Proxy();

      System.out.println("Doing the Send Operation ..on Element");
      ((XMLElement)elAdd).print(System.out);
      sm2.send(elAdd);
      
      System.out.println("Doing the Receive operation ..");
      Element e = sm2.receive();
      if (e != null) {
        System.out.println("Element received  is: ");
	((XMLElement)e).print(System.out);
      }
    } catch (Exception e) {
      e.printStackTrace();
      }
  }

 /**
  * Returns an Element with the customer name  , customer ID and balance amount
  * eg like :
  * 
  * <employee>
  *   <name>Bob</s:name>
  *   <emp_id>ID123</s:emp_id>
  *   <position>assistant manager</s:position>
  * </employee>
  * 
  */
 static Element getEmployeeElement(String name,String empId,String position){
      Document  doc = new XMLDocument();
      Element elAdd = doc.createElement( "employee");
      Element   elA = doc.createElement( "name");
      Element   elB = doc.createElement("emp_id");
      Element   elC = doc.createElement( "position");
      elA.appendChild(doc.createTextNode(name));
      elB.appendChild(doc.createTextNode(empId));
      elC.appendChild(doc.createTextNode(position));
      elAdd.appendChild(elA);
      elAdd.appendChild(elB);
      elAdd.appendChild(elC);
      doc.appendChild(elAdd);
      return doc.getDocumentElement();
   }
}
