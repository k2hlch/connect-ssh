import oracle.j2ee.ws_example.proxy.*;


public class BeanClient
{
  public static void main(String[] argv) throws Exception
  {
    BeanExampleProxy proxy = new BeanExampleProxy();

    oracle_j2ee_ws_example_TestBean testBean = new oracle_j2ee_ws_example_TestBean();
    testBean.setIntvalue(new Integer(50));
    testBean.setStringvalue("unctuous");

    proxy.setTestBean(testBean);


    oracle_j2ee_ws_example_TestBean retBean = proxy.getTestBean();


    if(retBean.getIntvalue().intValue() == testBean.getIntvalue().intValue() &&
       retBean.getStringvalue().equals(testBean.getStringvalue())
       )
        System.out.println("DEMO PASSED");
    else
        System.out.println("DEMO FAILED");
    

    
  }
}
