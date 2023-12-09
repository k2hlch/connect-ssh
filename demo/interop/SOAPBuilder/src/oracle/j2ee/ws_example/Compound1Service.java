package oracle.j2ee.ws_example;

import org.w3c.dom.Element;

import oracle.xml.parser.v2.*;
import org.w3c.dom.*;

/**
 * 
 * 
 */
public class Compound1Service 
{
  static final String[] PERSON_ATTRIBUTES = {"Name","Male"};
  static final String[] DOCUMENT_ATTRIBUTES = {"ID"};

  public Compound1Service()
  {
  }

  /**
   * 
   * @see #http://www.whitemesa.com/r3/plan.html
   * @param person the XML element containing the top node from the request.
   *  <x_Person Name="string" Male="boolean" xmlns="http://soapinterop.org/xsd">
   *   <Age>double</Age>
   *   <ID>float</ID>
   *  </x_Person>
   * @exception XxxxxxException if ...
   * @return returns the same element, in the same format. 
   */
  public Element echoPerson( Element person)
  {
    return renamedClone(person, "result_Person", "http://soapinterop.org/xsd", PERSON_ATTRIBUTES);
  }

  /**
   * 
   * @see #aaa
   * @param document the XML element containing the top node from the request.
   *  <x_Document ID="650-506-1622" Male="boolean" xmlns="http://soapinterop.org/xsd">
   *    Here is some example content.
   *  </x_Document>
   * @exception XxxxxxException if ...
   * @return returns the same element, in the same format. 
   */
  public Element echoDocument( Element document)
  {
    return renamedClone(document, "result_Document", "http://soapinterop.org/xsd", DOCUMENT_ATTRIBUTES);
  }

  private Element renamedClone(Element inputElement, String topElementName, String tns, String[] attributes) {
    DOMImplementation i = new XMLDOMImplementation();
    Document d = i.createDocument("http://soapinterop.org/xsd",topElementName,null);
    Element e1 = d.getDocumentElement();

    // iterate on the attribute
    NamedNodeMap attrs = inputElement.getAttributes();

    for (int j = 0; j < attributes.length; j++) 
    {
      Attr att = (Attr)attrs.getNamedItem( attributes[j]);
      if (att != null)
      {
        // String ns = att.getNamespaceURI();
        String val = att.getValue();
        e1.setAttribute( attributes[j],val);
      }
    }
    e1.setAttribute( "xmlns", tns);
/*
    int alen = attrs.getLength();
    for (int j = 0; j < alen ; j++) {
      //String ns = attrs.item(j).getNamespaceURI();
      String ln = attrs.item(j).getLocalName();
      String val = ((Attr) attrs.item(j)).getValue();

      e1.setAttribute( ln,val); 
    }
*/
    // clone the nested child elements
    NodeList nodes = inputElement.getChildNodes();
    for (int j=0; j<nodes.getLength(); j++) {
      Node child = nodes.item(j);
      e1.appendChild( d.importNode( child, true) );
    }    
    return e1;
  }
}