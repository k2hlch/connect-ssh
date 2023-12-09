import oracle.j2ee.ws_example.proxy.*;

public class StatelessClient
{
  public static void main(String[] argv) throws Exception
  {
    StatelessExampleProxy proxy = new StatelessExampleProxy();
    System.out.println(proxy.helloWorld("Scott"));
    System.out.println(proxy.helloWorld("Wendy"));
  }
}
