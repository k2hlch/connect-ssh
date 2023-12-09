import java.io.Serializable;
import java.rmi.RemoteException; 
import javax.ejb.*;
import javax.naming.*;
import javax.jms.*;
import org.w3c.dom.*;
import oracle.xml.parser.v2.*;



/**
 * The MessageBean class is a message-driven bean.  It implements 
 * the javax.ejb.MessageDrivenBean and javax.jms.MessageListener 
 * interfaces. It is defined as public (but not final or 
 * abstract).  It defines a constructor and the methods
 * setMessageDrivenContext, ejbCreate, onMessage, and 
 * ejbRemove.
 */
public class MessageBean implements MessageDrivenBean, 
  MessageListener
{

  private transient MessageDrivenContext mdc = null;
  private Context context;
    
  /**
   * Constructor, which is public and takes no arguments.
   */
  public MessageBean() {
  }
    
  /**
   * setMessageDrivenContext method, declared as public (but not
   * final or static), with a return type of void, and with one 
   * argument of type javax.ejb.MessageDrivenContext.
   */
  public void setMessageDrivenContext(MessageDrivenContext mdc) 
  {
    this.mdc = mdc;
  }
    
  /**
   * ejbCreate method, declared as public (but not final or 
   * static), with a return type of void, and with no arguments.
   */
  public void ejbCreate() {
  }
  
  /**
   * onMessage method, declared as public (but not final or 
   * static), with a return type of void, and with one argument 
   * of type javax.jms.Message.
   *
   * Casts the incoming Message to a ObjectMessage and displays 
   * the text.
   *
   * @param inMessage	the incoming message
   */
  public void onMessage(Message inMessage) {
    // Do some processing
    ObjectMessage msg = null;
    Element       e;
    try {
      // Message should be of type objectMessage
      if (inMessage instanceof ObjectMessage) {
        // retrieve the object
        msg = (ObjectMessage) inMessage;
        e = (Element)msg.getObject(); 
        System.out.println("MessageBean::onMessage() => Message received: " );
        ((XMLElement)e).print(System.out); 
        processElement(e);
        this.send2Queue(e);
      } else {
        System.out.println("MessageBean::onMessage() => Message of wrong type: " + inMessage.getClass().getName());
      }      
    } catch (JMSException ex) {
      ex.printStackTrace();
  
    } catch (Throwable te) {
      te.printStackTrace();
    }
  }


 /* 
  * Method to process the element , change the value of position
  */

  private void  processElement(Element e) {

      Node          newNode;
      Node          nodeToRemove;
      Node          appendInto =(Node)e;
      Document      doc = e.getOwnerDocument();
      try{

         //create the new node to add
         newNode = (Node)doc.createElement("position");
         newNode.appendChild(doc.createTextNode("senior manager"));

         //the node whose child-node will be replaced
          nodeToRemove = ((XMLNode)appendInto).selectSingleNode("position" );
   
         //remove the old node   and add thhe new
         appendInto.removeChild(nodeToRemove);
         appendInto.appendChild(newNode);
  
       }catch(Exception ex){
          System.out.println(ex);
          throw new RuntimeException("error in processing element"+ex);
  
       }

    }

  
  /**
   * ejbRemove method, declared as public (but not final or 
   * static), with a return type of void, and with no arguments.
   */
  public void ejbRemove() {
  }

  /**
   * Method to send the Element to the queue
   */
  
  public void 	send2Queue(Element ee) throws JMSException
  {
    Context                 jndiContext = null;
    QueueConnectionFactory  queueConnectionFactory = null;
    QueueConnection         queueConnection = null;
    QueueSession            queueSession = null;
    Queue                   queue = null;
    QueueSender             queueSender    = null;
    ObjectMessage           message = null;
    
    
    try 
    {
      // Create a JNDI Context
      jndiContext = new InitialContext();
      
      // Create the Queue and Queue Connection Factory
      queue = (Queue)jndiContext.lookup("java:comp/env/jms/logQueue");
      queueConnectionFactory = (QueueConnectionFactory) 
        jndiContext.lookup("java:comp/env/jms/logQueueConnectionFactory");
      
      // Create the Queue Connection and Session
      queueConnection =
        queueConnectionFactory.createQueueConnection();
      queueSession =
        queueConnection.createQueueSession(false,
                                           Session.AUTO_ACKNOWLEDGE);

      // Create a Queue Sender
      queueSender = queueSession.createSender(queue);
      
      // Start the connection
      queueConnection.start();
      // Create an Object Message
      message = queueSession.createObjectMessage();
      // Stuff the result into the ObjectMessage
      ((ObjectMessage)message).setObject ((java.io.Serializable)ee);                                        // Send the Message
      queueSender.send(message);  
      System.out.println("MessageBean::send2Queue() => Message Sent");
    } 
    catch (NamingException e) 
    {
      System.out.println("MessageBean::send2Queue; Exception occurred: " +
                         e.toString());
    } 
    catch (JMSException e) 
    {
      System.out.println("MessageBean::send2Queue; Exception occurred: " +
                         e.toString());
    } 
    finally 
    {    
      // Close the Sender
      if (queueSender != null) 
          queueSender.close();
      
      // Close the Session
      if (queueSession != null) 
            queueSession.close();
      // Close the Connection
      if (queueConnection != null) 
          queueConnection.close();
    }
    
  }
}
