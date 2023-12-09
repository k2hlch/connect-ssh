package oracle.j2ee.ws_example;

public interface InteropTestRpcEnc 
{
  public String echoString(String inputString) throws Exception;

  public void echoVoid() throws Exception;

  public String[] echoStringArray(String[] inputStringArray) throws Exception;

  public oracle.j2ee.ws_example.SOAPStruct echoStruct(oracle.j2ee.ws_example.SOAPStruct inputStruct) throws Exception;

}