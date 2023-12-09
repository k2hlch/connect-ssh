package oracle.j2ee.ws_example;

public class StatefulExampleImpl {
  int count = 0;
  public StatefulExampleImpl() {
  }
  public int count() {
    return count++;
  }
  public String helloWorld(String param) {
    return "Hello World, " + param;
  }
}
