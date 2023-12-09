import demo.proxy.*;

public class HelloClient
{
  public static void main(String[] argv) throws Exception
  {
    HelloServiceProxy proxy = new HelloServiceProxy();
    System.out.println(proxy.hello(argv[0]));
  }
}
