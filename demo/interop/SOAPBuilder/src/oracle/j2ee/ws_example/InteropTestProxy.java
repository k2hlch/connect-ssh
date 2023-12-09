package oracle.j2ee.ws_example;

import java.util.Vector;
import java.net.URL;
import java.util.Properties;
import java.util.HashMap;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;
import org.apache.soap.Fault;
import org.apache.soap.SOAPException;
import org.apache.soap.Constants;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.encoding.soapenc.BeanSerializer;
import org.apache.soap.util.xml.QName;
import oracle.soap.transport.http.OracleSOAPHTTPConnection;
import oracle.soap.encoding.soapenc.EncUtils;

/**
 * Web service proxy: InteropTest
 *     generated by Oracle WSDL toolkit (Version: 1.0).
 */
public class InteropTestProxy {

    public InteropTestProxy() {
        m_httpConnection = new OracleSOAPHTTPConnection();
        _setMaintainSession(true);
        m_soapMappingRegistry = new SOAPMappingRegistry();

        BeanSerializer beanSer = new BeanSerializer();

        m_soapMappingRegistry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("http://soapinterop.org/xsd" ,"SOAPStruct"), SOAPStruct.class, beanSer, beanSer);

// work-around: code added by hand to support HP-J2EE unqualified response
m_soapMappingRegistry.getParent().setDefaultEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
// end of code change
        
        Object untypedParams[] = {
            new String("echoIntegerArray"), new String("return"), new QName("http://soapinterop.org/xsd","ArrayOfint"),
            new String("echoStruct"), new String("return"), new QName("http://soapinterop.org/xsd","SOAPStruct"),
            new String("echoStruct"), new String("varString"), new QName("http://www.w3.org/2001/XMLSchema","string"),
            new String("echoStruct"), new String("varInt"), new QName("http://www.w3.org/2001/XMLSchema","int"),
            new String("echoStruct"), new String("varFloat"), new QName("http://www.w3.org/2001/XMLSchema","float"),
            new String("echoFloat"), new String("return"), new QName("http://www.w3.org/2001/XMLSchema","float"),
            new String("echoDecimal"), new String("return"), new QName("http://www.w3.org/2001/XMLSchema","decimal"),
            new String("echoDate"), new String("return"), new QName("http://www.w3.org/2001/XMLSchema","dateTime"),
            new String("echoInteger"), new String("return"), new QName("http://www.w3.org/2001/XMLSchema","int"),
            new String("echoString"), new String("return"), new QName("http://www.w3.org/2001/XMLSchema","string"),
            new String("echoBase64"), new String("return"), new QName("http://www.w3.org/2001/XMLSchema","base64Binary"),
            new String("echoBoolean"), new String("return"), new QName("http://www.w3.org/2001/XMLSchema","boolean"),
            new String("echoStructArray"), new String("return"), new QName("http://soapinterop.org/xsd","ArrayOfSOAPStruct"),
            new String("echoFloatArray"), new String("return"), new QName("http://soapinterop.org/xsd","ArrayOffloat"),
            new String("echoStringArray"), new String("return"), new QName("http://soapinterop.org/xsd","ArrayOfstring")
        };

        String operationName;
        String paramName;
        QName returnType;
        SOAPMappingRegistry registry;
        org.apache.soap.util.xml.Deserializer deserializer;
        int x;
        for (x = 0; x < untypedParams.length; x += 3) {
            operationName = (String) untypedParams[x];
            paramName = (String) untypedParams[x+1];
            returnType = (QName) untypedParams[x+2];

            registry = (SOAPMappingRegistry) m_soapMappingRegistries.get(operationName);
            if (registry == null) {
                if (m_soapMappingRegistry != null) {
                    registry = new SOAPMappingRegistry(m_soapMappingRegistry);
                } else {
                    registry = new SOAPMappingRegistry();
                }
                m_soapMappingRegistries.put(operationName,registry);
            }

            try {
                deserializer = registry.queryDeserializer(returnType,Constants.NS_URI_SOAP_ENC);
                registry.mapTypes(Constants.NS_URI_SOAP_ENC, new QName("",paramName), null, null, deserializer);
            } catch(IllegalArgumentException e) {
            }
        }

    }

    public java.lang.Integer[] echoIntegerArray(java.lang.Integer[] inputIntegerArray) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputIntegerArray", java.lang.Integer[].class, inputIntegerArray, null));
        Response response = makeSOAPCallRPC("echoIntegerArray", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.lang.Integer[])EncUtils.mapArrayInbuiltToWrapper(java.lang.Integer[].class, returnValue.getValue());

    }

    public SOAPStruct echoStruct(SOAPStruct inputStruct) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputStruct", SOAPStruct.class, inputStruct, null));
        Response response = makeSOAPCallRPC("echoStruct", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (SOAPStruct)returnValue.getValue();

    }

    public java.lang.Float echoFloat(java.lang.Float inputFloat) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputFloat", java.lang.Float.class, inputFloat, null));
        Response response = makeSOAPCallRPC("echoFloat", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.lang.Float)returnValue.getValue();

    }

    public java.math.BigDecimal echoDecimal(java.math.BigDecimal inputDecimal) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputDecimal", java.math.BigDecimal.class, inputDecimal, null));
        Response response = makeSOAPCallRPC("echoDecimal", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.math.BigDecimal)returnValue.getValue();

    }

    public java.util.Date echoDate(java.util.Date inputDate) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputDate", java.util.Date.class, inputDate, null));
        Response response = makeSOAPCallRPC("echoDate", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.util.Date)returnValue.getValue();

    }

    public java.lang.Integer echoInteger(java.lang.Integer inputInteger) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputInteger", java.lang.Integer.class, inputInteger, null));
        Response response = makeSOAPCallRPC("echoInteger", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.lang.Integer)returnValue.getValue();

    }

    public java.lang.String echoString(java.lang.String inputString) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputString", java.lang.String.class, inputString, null));
        Response response = makeSOAPCallRPC("echoString", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.lang.String)returnValue.getValue();

    }

    public java.lang.Byte[] echoBase64(java.lang.Byte[] inputBase64) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputBase64", java.lang.Byte[].class, inputBase64, null));
        Response response = makeSOAPCallRPC("echoBase64", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.lang.Byte[])EncUtils.mapArrayInbuiltToWrapper(java.lang.Byte[].class, returnValue.getValue());

    }

    public java.lang.Boolean echoBoolean(java.lang.Boolean inputBoolean) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputBoolean", java.lang.Boolean.class, inputBoolean, null));
        Response response = makeSOAPCallRPC("echoBoolean", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.lang.Boolean)returnValue.getValue();

    }

    public void echoVoid() throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        makeSOAPCallRPC("echoVoid", params, encodingStyleURI, soapActionURI);

    }

    public SOAPStruct[] echoStructArray(SOAPStruct[] inputStructArray) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputStructArray", SOAPStruct[].class, inputStructArray, null));
        Response response = makeSOAPCallRPC("echoStructArray", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (SOAPStruct[])returnValue.getValue();

    }

    public java.lang.Float[] echoFloatArray(java.lang.Float[] inputFloatArray) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputFloatArray", java.lang.Float[].class, inputFloatArray, null));
        Response response = makeSOAPCallRPC("echoFloatArray", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.lang.Float[])EncUtils.mapArrayInbuiltToWrapper(java.lang.Float[].class, returnValue.getValue());

    }

    public java.lang.String[] echoStringArray(java.lang.String[] inputStringArray) throws Exception {
        String soapActionURI = "http://soapinterop.org/";
        String encodingStyleURI = "http://schemas.xmlsoap.org/soap/encoding/";
        Vector params = new Vector();
        params.add(new Parameter("inputStringArray", java.lang.String[].class, inputStringArray, null));
        Response response = makeSOAPCallRPC("echoStringArray", params, encodingStyleURI, soapActionURI);
        Parameter returnValue = response.getReturnValue();
        return (java.lang.String[])returnValue.getValue();

    }

    private Response makeSOAPCallRPC(String methodName, Vector params, String encodingStyleURI, String soapActionURI) throws Exception {
        Call call  = new Call();
        call.setSOAPTransport(m_httpConnection);
        SOAPMappingRegistry registry;
        if ((registry = (SOAPMappingRegistry)m_soapMappingRegistries.get(methodName)) != null)
            call.setSOAPMappingRegistry(registry);
        else if (m_soapMappingRegistry != null)
            call.setSOAPMappingRegistry(m_soapMappingRegistry);
        call.setTargetObjectURI(m_serviceID);
        call.setMethodName(methodName);
        call.setEncodingStyleURI(encodingStyleURI);
        call.setParams(params);

        Response response = call.invoke(new URL(m_soapURL), soapActionURI);
        if (response.generatedFault()) {
            Fault fault = response.getFault();
            throw new SOAPException(fault.getFaultCode(), fault.getFaultString());
        }
        return response;

    }

    public String _getSoapURL() {
        return m_soapURL;

    }

    public void _setSoapURL(String soapURL) {
        m_soapURL = soapURL;

    }

    public String _getServiceID() {
        return m_serviceID;

    }

    public void _setServiceID(String serviceID) {
        m_serviceID = serviceID;

    }

    public SOAPMappingRegistry _getSOAPMappingRegistry() {
        return m_soapMappingRegistry;

    }

    public void _setSOAPMappingRegistry(SOAPMappingRegistry soapMappingRegistry) {
        m_soapMappingRegistry = soapMappingRegistry;

    }

    public boolean _getMaintainSession() {
        return m_httpConnection.getMaintainSession();

    }

    public void _setMaintainSession(boolean maintainSession) {
        m_httpConnection.setMaintainSession(maintainSession);

    }

    public Properties _getTransportProperties() {
        return m_httpConnection.getProperties();

    }

    public void _setTransportProperties(Properties properties) {
        m_httpConnection.setProperties(properties);

    }

    public String _getVersion() {
        return m_version;

    }


    private String m_serviceID = "http://soapinterop.org/";
    private String m_soapURL = "http://localhost:8888/soapbuilder/r2/InteropTest";
    private OracleSOAPHTTPConnection m_httpConnection = null;
    private SOAPMappingRegistry m_soapMappingRegistry = null;
    private String m_version = "1.0";
    private HashMap m_soapMappingRegistries = new HashMap();

}