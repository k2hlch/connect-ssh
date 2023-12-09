package oracle.j2ee.ws_example;

import org.w3c.dom.Element;
import oracle.xml.parser.v2.*;
import org.w3c.dom.*;

public class InteropTestDocLitService 
{
  static final String TNS = "http://soapinterop.org/xsd";

  public InteropTestDocLitService()
  {
  }

  public void echoVoid()
  {
    return;
  }
  
  public Element echoString(Element input)
  {
    return renamedClone( input, "echoStringReturn", TNS);
  }

  public Element echoStringArray(Element input)
  {
    return renamedClone( input, "echoStringArrayReturn", TNS);
  }

  public Element echoStruct(Element input)
  {
    return renamedClone( input, "echoStructReturn", TNS);
  }

  private Element renamedClone(Element inputElement, String topElementName, String tns) {
    DOMImplementation i = new XMLDOMImplementation();
    Document d = i.createDocument( tns,topElementName,null);
    Element e1 = d.getDocumentElement();
/*
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
    e1.setAttribute( "xmlns", TNS);

    // clone the nested child elements
    NodeList nodes = inputElement.getChildNodes();
    for (int j=0; j<nodes.getLength(); j++) {
      Node child = nodes.item(j);
      e1.appendChild( d.importNode( child, true) );
    }    
    return e1;
  }
}