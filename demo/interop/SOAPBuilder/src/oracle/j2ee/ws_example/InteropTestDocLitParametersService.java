package oracle.j2ee.ws_example;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import oracle.xml.parser.v2.*;
import org.w3c.dom.*;

public class InteropTestDocLitParametersService 
{
  public InteropTestDocLitParametersService()
  {
  }

  public void echoVoid()
  {
    return;
  }
  
  public Element echoString(Element input)
  {
    return renamedFirstChildClone( input);
  }

  public Element echoStringArray(Element input)
  {
    return renamedFirstChildClone( input);
  }

  public Element echoStruct(Element input)
  {
    return renamedFirstChildClone( input);
  }

  /*
   * need to replace the first child name from param0 to return
   */
  private Element renamedFirstChildClone(Element inputElement) {
    String topElementName;
    Node firstChild = inputElement.getFirstChild();
    topElementName = inputElement.getNodeName() + "Response";

    DOMImplementation i = new XMLDOMImplementation();
    Document d = i.createDocument("http://soapinterop.org/xsd",topElementName,null);
    Element e1 = d.getDocumentElement();

    // iterate on the attribute
    NamedNodeMap attrs = inputElement.getAttributes();
    int alen = attrs.getLength();
    for (int j = 0; j < alen ; j++) {
      String ns = attrs.item(j).getNamespaceURI();
      String ln = attrs.item(j).getLocalName();
      String val = ((Attr) attrs.item(j)).getValue();

      e1.setAttribute( ln,val); 
    }

    // create the return node, insted of the param0 node.
    Element e2 = d.createElementNS("","return");
    e1.appendChild( e2);
    // clone the nested child elements
    NodeList nodes = firstChild.getChildNodes();
    for (int j=0; j<nodes.getLength(); j++) {
      Node child = nodes.item(j);
      e2.appendChild( d.importNode( child, true) );
    }    
    return e1;
  }
}