package demo;

import java.rmi.RemoteException;
import java.util.Properties;
import javax.ejb.*;

/**
 * This is a Session Bean Class.
 * It can be used with a stateless or stateful SOAP EJB provider.
 */
public class HelloServiceBean implements SessionBean {
    private javax.ejb.SessionContext mySessionCtx = null;

/**
 * ejbActivate method comment
 * @exception java.rmi.RemoteException The exception description.
 */
public void ejbActivate() throws java.rmi.RemoteException {}
/**
 * ejbCreate method comment
 * @exception javax.ejb.CreateException The exception description.
 * @exception java.rmi.RemoteException The exception description.
 */
public void ejbCreate() throws javax.ejb.CreateException, java.rmi.RemoteException {}

/**
 * ejbPassivate method comment
 * @exception java.rmi.RemoteException The exception description.
 */
public void ejbPassivate() throws java.rmi.RemoteException {}
/**
 * ejbRemove method comment
 * @exception java.rmi.RemoteException The exception description.
 */
public void ejbRemove() throws java.rmi.RemoteException {}
/**
 * getSessionContext method comment
 * @return javax.ejb.SessionContext
 */
public javax.ejb.SessionContext getSessionContext() {
        return mySessionCtx;
}
/**
 * Insert the method's description here.
 * Creation date: (10/31/00 1:44:19 PM)
 * @return java.lang.String
 * @param phrase java.lang.String
 */
public String hello(String phrase)
    {
        try
            {
                Thread.sleep(0);
            }
        catch(InterruptedException ex){}
        finally
            {
                return "HELLO!! You just said :" + phrase;
            }
    }
/**
 * setSessionContext method comment
 * @param ctx javax.ejb.SessionContext
 * @exception java.rmi.RemoteException The exception description.
 */
public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException {
        mySessionCtx = ctx;
}
}
