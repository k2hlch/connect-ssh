package oracle.j2ee.ws_example;
/**
 * Generated by the Oracle9i JDeveloper Web Services Stub/Skeleton Generator.
 * Date Created: Tue Jun 11 13:57:35 PDT 2002
 * WSDL URL: http://isunrak18.us.oracle.com:8888/soapbuilder/r2/InteropTest?WSDL
 */

public interface IInteropTest 
{
  public Integer[] echoIntegerArray(Integer[] inputIntegerArray) throws Exception;

  //public Byte[] echoHexBinary(Byte[] inputHexBinary) throws Exception;

  public oracle.j2ee.ws_example.SOAPStruct echoStruct(oracle.j2ee.ws_example.SOAPStruct inputStruct) throws Exception;

  public Float echoFloat(Float inputFloat) throws Exception;

  public java.math.BigDecimal echoDecimal(java.math.BigDecimal inputDecimal) throws Exception;

  public java.util.Date echoDate(java.util.Date inputDate) throws Exception;

  public Integer echoInteger(Integer inputInteger) throws Exception;

  public String echoString(String inputString) throws Exception;

  public byte[] echoBase64(byte[] inputBase64) throws Exception;

  public boolean echoBoolean(boolean inputBoolean) throws Exception;

  public void echoVoid() throws Exception;

  public oracle.j2ee.ws_example.SOAPStruct[] echoStructArray(oracle.j2ee.ws_example.SOAPStruct[] inputStructArray) throws Exception;

  public Float[] echoFloatArray(Float[] inputFloatArray) throws Exception;

  public String[] echoStringArray(String[] inputStringArray) throws Exception;
}