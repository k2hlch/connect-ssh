import java.io.Serializable;
import java.rmi.RemoteException; 
import javax.ejb.*;
import javax.naming.*;
import javax.jms.*;
import org.w3c.dom.*;
import oracle.xml.parser.v2.*;


/**
 * The MessageBean2 class is a message-driven bean.  It implements 
 * the javax.ejb.MessageDrivenBean and javax.jms.MessageListener 
 * interfaces.  
 */
public class MessageBean2 implements MessageDrivenBean, 
  MessageListener
{

  private transient MessageDrivenContext mdc = null;
  private Context context;
    
  /**
   * Constructor, which is public and takes no arguments.
   */
  public MessageBean2()
  {
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
  public void ejbCreate()
  {
  }
  
  /**
   * Casts the incoming Message to a ObjectMessage and processes it and  
   * publishes it over a topic 
   *
   * @param inMessage	the incoming message
   */
  public void onMessage(Message inMessage) {
    // Do some processing
    ObjectMessage msg = null;
    String        factoryName;
    Destination   dest;
    Element       el;
    try {
      // Message should be of type objectMessage
      if (inMessage instanceof ObjectMessage) {
        // retrieve the object
        msg = (ObjectMessage) inMessage;
        el = (Element)msg.getObject();
        System.out.println("MessageBean2::onMessage() => Message received: " );
        ((XMLElement)el).print(System.out);   
        processElement(el);
 
        factoryName = inMessage.getStringProperty("OC4J_REPLY_TO_FACTORY_NAME");
        dest = inMessage.getJMSReplyTo();
        System.out.println("Return Factory Name is: " + factoryName);
        System.out.println("Return Destination is: " + dest);
        this.send2Topic(factoryName,dest,el);
      } else {
        System.out.println("MessageBean2::onMessage() => Message of wrong type: " + inMessage.getClass().getName());
      }      
    } catch (JMSException e) {
      e.printStackTrace();
      mdc.setRollbackOnly();
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
  
  public void 	send2Topic(String factoryName, Destination dest,Element ee)
  throws JMSException{
    Context                 jndiContext = null;
    
    TopicConnectionFactory  topicConnectionFactory = null;
    TopicConnection         topicConnection = null;
    TopicSession            topicSession = null;
    Topic                   topic = null;
    TopicPublisher          topicPublisher = null;    
    ObjectMessage           message = null;
    
    try 
    {
      // Create a JNDI Context
      jndiContext = new InitialContext();
      
      // get the Topic and Topic Connection Factory
      topic = (Topic)dest;
      topicConnectionFactory = (TopicConnectionFactory) 
        jndiContext.lookup("java:comp/env/"+factoryName);
      
      // Create the Topic Connection and Session
      topicConnection =
        topicConnectionFactory.createTopicConnection();
      topicSession =
        topicConnection.createTopicSession(false,
                                           Session.AUTO_ACKNOWLEDGE);

      // Create a Topic Sender
      topicPublisher = topicSession.createPublisher(topic);
      
      // Start the connection
      topicConnection.start();

      // Create an Object Message
      message = topicSession.createObjectMessage();

      // Stuff the  result into the ObjectMessage
      ((ObjectMessage)message).setObject ((java.io.Serializable)ee);                                     
      topicPublisher.publish(message);  
      System.out.println("MessageBean2::send2Topic() => Message Sent");
    } 
    catch (NamingException e) 
    {
      System.out.println("MessageBean2::send2Topic; Exception occurred: " +
                         e.toString());
    } 
    catch (JMSException e) 
    {
      System.out.println("MessageBean2::send2Topic; Exception occurred: " +
                         e.toString());
    } 
    finally 
    {    
      // Close the Sender
      if (topicPublisher != null) 
          topicPublisher.close();
      
      // Close the Session
      if (topicSession != null) 
            topicSession.close();
      
      // Close the Connection
      if (topicConnection != null) 
          topicConnection.close();
    }
    
    System.out.println("MessageBaen2::send2Topic: end");
  }
}
