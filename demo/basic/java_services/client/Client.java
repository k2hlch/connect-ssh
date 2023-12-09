import oracle.j2ee.ws_example.proxy.*;

public class Client
{
  public static void main(String[] argv) throws Exception
  {
    StatefulExampleProxy proxy = new StatefulExampleProxy();
    System.out.println(proxy.helloWorld("Scott"));
    System.out.println(proxy.count());
    System.out.println(proxy.count());
    System.out.println(proxy.count());
  }
}
