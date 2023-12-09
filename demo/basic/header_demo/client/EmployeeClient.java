import org.apache.soap.Header;
import java.util.Vector;
import org.w3c.dom.*;
import oracle.xml.parser.v2.*;

/*
 * Employee client program that creates an instance of
 * proxy and sets the user name, password, & datasource 
 * in the header and sets the actual request payload in 
 * the body.
 *
 * Ps: This demo needs a backend database up & running.
 */

public class EmployeeClient
{
  
  public static void main(String[] argv) throws Exception
  {
    /**
     * Create a XML element with user name, password, &
     * datasource as child elements.
     * Element will look like this:
     *
     * <credetials>
     *   <username>scott</username>
     *   <password>tiger</password>
     *   <datasource>jdbc/OracleCoreDS</datasource>
     * </credentials>
     *
     */
    Document  doc = new XMLDocument();
    Element elAdd = doc.createElement( "credentials");
    Element   elA = doc.createElement( "username");
    Element   elB = doc.createElement("password");
    Element   elC = doc.createElement("datasource");
    elA.appendChild(doc.createTextNode("scott"));
    elB.appendChild(doc.createTextNode("tiger"));
    elC.appendChild(doc.createTextNode("jdbc/OracleCoreDS"));;
    elAdd.appendChild(elA);
    elAdd.appendChild(elB);
    elAdd.appendChild(elC);
    doc.appendChild(elAdd);
    Element e = doc.getDocumentElement();

    // Create an intance of the proxy
    EmployeeProxy proxy = new EmployeeProxy();
    // Create a Header objecy
    Vector v = new Vector();
    v.add (e);
    Header hdr = new Header();
    hdr.setHeaderEntries(v);
    
    // Set the Header
    proxy._setSOAPRequestHeaders(hdr);
    // Invoke the request
    System.out.println("Salary of MILLER is: " + proxy.getEmployeeSalary("MILLER"));
  }
}
