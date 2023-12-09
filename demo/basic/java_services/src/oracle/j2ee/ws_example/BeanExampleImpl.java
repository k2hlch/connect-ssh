package oracle.j2ee.ws_example;

public class BeanExampleImpl {
private TestBean m_testBean;
    
  public BeanExampleImpl() {
  }
  public  TestBean getTestBean() {
      return m_testBean;
      
  }
  public void setTestBean(TestBean b) {
      m_testBean = b;
  }
}
