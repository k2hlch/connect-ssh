import org.w3c.dom.*;
import oracle.xml.parser.v2.*;


// Class Implementing the interface StatefulDoc
public class StatefulDocImpl implements StatefulDoc {

  private Element e ;

  public  StatefulDocImpl()
  {

  }
 
  /**
   * Stores the customer element state
   */

  public void startShopping(Element e) 
  {
    this.e = e;
   
    System.out.println("startShopping called");
  }

  /**
   * return the element after customer makes a purchase
   */

  public Element makePurchase()
  {
    System.out.println("makePurchase called");
   
    processElement(e);
    return this.e;
  }
  
  /**
   * Method where the xml element is processed .The customer's balance amount is 
   * decremented by 100  
   */
 
   private void  processElement(Element e) {

      String        balanceAmount;
      Node          newNode;
      Node          nodeToRemove;
      Node          appendInto =(Node)e;
      TheNSResolver nsr = new TheNSResolver(e.getNamespaceURI(),"s");
      Document      doc = e.getOwnerDocument();
      try{

         //decrement balance amount by 100
         balanceAmount =((XMLElement)e).valueOf("s:balance_amount",nsr );
         balanceAmount = String.valueOf(Integer.parseInt(balanceAmount)-100);

         //create the new node to add
         newNode = (Node)doc.createElementNS(e.getNamespaceURI(), "s:balance_amount");
         newNode.appendChild(doc.createTextNode(balanceAmount));

         //the node to be replaced
          nodeToRemove = ((XMLNode)appendInto).selectSingleNode("s:balance_amount",nsr );
   
         //remove the old node   and add thhe new
         appendInto.removeChild(nodeToRemove);
         appendInto.appendChild(newNode);
  
       }catch(Exception ex){
          System.out.println(ex);
          throw new RuntimeException("error in processing element"+ex);
  
       }

    }


  /**
   * The name space resolver
   */
      
    private class TheNSResolver implements NSResolver{
       String inputNS;
       String iprefix;
       TheNSResolver(String ns,String prefix){
         this.inputNS=ns;
         this.iprefix =prefix;
       }
        public   String  resolveNamespacePrefix(String prefix) {
             if(prefix.equals(this.iprefix))
                 return inputNS;
             else  
                 return "";
         }
      }


}
