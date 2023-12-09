import org.w3c.dom.*;
import oracle.xml.parser.v2.*;



public class JmsMessageOriginator {

  public static void main (String [] args)
  {
    try {
      
      // Create a sample XML Element
      
      Element elAdd = getEmployeeElement("Bob","1234","assistant manager");
      // Create an instance of the JmsTestProxy to send an XML element  
      JmsSendProxy sm = new JmsSendProxy();
      System.out.println("Doing the Send Operation ..on Element");
      ((XMLElement)elAdd).print(System.out);
      sm.send(elAdd);
    } catch (Exception e) { 
       e.printStackTrace(); 
     }
  }

 /**
  * Returns an Element with the customer name  , customer ID and balance amount
  * eg like :
  * 
  * <s:employee>
  *   <s:name>Bob</s:name>
  *   <s:emp_id>ID123</s:emp_id>
  *   <s:position>Assistant Manager</s:position>
  * </s:employee>
  * 
  */
  private  static Element getEmployeeElement(String name,String empId,String position){
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

 
