import org.w3c.dom.Element;

// Interface that implements getElement and setElement
public interface StatefulDoc {

  // Set the Element
  public void startShopping(Element e);

  // Retrieve the element that was set
  public Element makePurchase();
}
