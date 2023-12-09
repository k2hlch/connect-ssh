package oracle.j2ee.ws_example;

import org.w3c.dom.Element;
import oracle.xml.parser.v2.*;
import org.w3c.dom.*;

/**
 * 
 * 
 */
public class Compound2Service 
{
  public Compound2Service()
  {
  }

  /**
   * 
   * @see #http://www.whitemesa.com/r3/plan.html
   * @param employee the XML element containing the top node from the request.
   * <x_Employee xmlns="http://soapinterop.org/employee">
   *   <person>
   *     <Name xmlns="http://soapinterop.org/person">string</Name>
   *     <Male xmlns="http://soapinterop.org/person">boolean</Male>
   *   </person>
   *   <salary>double</salary>
   *   <ID>int</ID>
   * </x_Employee>
   * @exception XxxxxxException if ...
   * @return returns the same element, in the same format. 
   */
  public Element echoEmployee( Element employee)
  {
    return renamedClone( employee, "result_Employee", "http://soapinterop.org/employee");
  }

  private Element renamedClone(Element inputElement, String topElementName, String tns) {
    DOMImplementation i = new XMLDOMImplementation();
    Document d = i.createDocument( tns,topElementName,null);
    Element e1 = d.getDocumentElement();
/* no need to preserve any of the incoming attributes
    // iterate on the attribute
    NamedNodeMap attrs = inputElement.getAttributes();
    int alen = attrs.getLength();
    for (int j = 0; j < alen ; j++) {
      String ns = attrs.item(j).getNamespaceURI();
      String ln = attrs.item(j).getLocalName();
      String val = ((Attr) attrs.item(j)).getValue();

      e1.setAttribute( ln,val);
    }
*/
    e1.setAttribute( "xmlns",tns);

    // clone the nested child elements
    NodeList nodes = inputElement.getChildNodes();
    for (int j=0; j<nodes.getLength(); j++) {
      Node child = nodes.item(j);
      e1.appendChild( d.importNode( child, true) );
    }    
    return e1;
  }
}