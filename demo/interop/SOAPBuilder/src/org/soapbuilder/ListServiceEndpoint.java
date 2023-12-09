package org.soapbuilder;

import org.soapbuilder.interopInfoSvcProxy;
import org.soapbuilder.EndpointInfo;

public class ListServiceEndpoint {
  public ListServiceEndpoint() {
  }

  public static void main(String[] args) {
    ListServiceEndpoint test = new ListServiceEndpoint();
    test.runIt();
  }

  private void runIt() {
    //InteropInfoSvcStub test = new InteropInfoSvcStub();
    interopInfoSvcProxy test = new interopInfoSvcProxy();
    String endpointGroup[] = {"base", "GroupB", "GroupC" } ;
      
    for (int j = 0 ; j < endpointGroup.length ; j++ ) {
      try {
        EndpointInfo[] result = test.GetEndpointInfo( endpointGroup[j]);

        System.out.println(
          "There are " + result.length + " endpoints registered for '" 
          + endpointGroup[j] + "' test suite");
        for (int i = 0; i < result.length; i++) {
          System.out.println( "  #" + (i+1) + ": " 
            + result[i].getEndpointName() + ", "
            + result[i].getEndpointURL() + ", "
            + result[i].getWsdlURL());
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}