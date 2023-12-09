package org.soapbuilder;

import java.io.*;

import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.Bean;
import org.apache.soap.util.xml.Serializer;
import org.apache.soap.util.xml.Deserializer;
import org.apache.soap.util.xml.*;
import org.apache.soap.encoding.soapenc.SoapEncUtils;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import oracle.xml.parser.v2.XMLText;

import org.soapbuilder.EndpointInfo;

/**
   <return SOAP-ENC:arrayType="nsa:EndpointInfo[35]" SOAP-ENC:offset="[0]"
     xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/"
     xmlns:nsa="http://soapinterop.org/info/xs">
      <item>
         <endpointName>4s4c</endpointName>
         <endpointURL>http://soap.4s4c.com/ilab/soap.asp</endpointURL>
         <wsdlURL>http://www.pocketsoap.com/services/ilab.wsdl</wsdlURL>
      </item>
      ....
    </return>
 */

public class EndpointInfoArrayDeserializer implements Deserializer
{
  public EndpointInfoArrayDeserializer()
  {
  }

  public Bean unmarshall(String inScopeEncStyle, QName elementType,
      Node src, XMLJavaMappingRegistry xjmr, SOAPContext ctx)
        throws IllegalArgumentException
  {
    EndpointInfo[] resArray = null;
    Element        paramEl;
    Element        el;
    String         name;

    try
    {
      paramEl = (Element)src;
      name = paramEl.getTagName();
      if (!name.equals("return"))
      {
        throw new IllegalArgumentException(
          "Top node is '" + name + "', expected value was return");
      }

      // get the number of items to size the array
      NodeList nl = paramEl.getChildNodes();
      resArray = new EndpointInfo[nl.getLength()];

      // iterate on the items in the collection
      for (int i = 0; i < nl.getLength(); i++) {
        EndpointInfo  tz = null;

        el = DOMUtils.getFirstChildElement((Element)nl.item(i));

        if (SoapEncUtils.isNull(el))
          resArray[i] = null;
        else
          resArray[i] = unmarshalItem( el);
      }
    }
    catch(NullPointerException npe)
    {
      resArray = null;
    }
    catch(Exception e)
    {
      throw new IllegalArgumentException(
        "Custom Deserializer failed to unmarshall the EndpointInfo[] from the XML DOM");
    }
    return new Bean(EndpointInfo[].class, resArray);
  }

  private EndpointInfo unmarshalItem( Element el ) {
    String endpointName = ((XMLText)el.getFirstChild()).getData();
    String endpointURL = ((XMLText)el.getNextSibling().getFirstChild()).getData();
    String wsdlURL = ((XMLText)el.getNextSibling().getFirstChild()).getData();
    return new EndpointInfo( endpointName, endpointURL, wsdlURL);
  }
}