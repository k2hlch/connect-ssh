

/* Copyright (c) 2001, 2002, Oracle Corporation.  All rights reserved.  */

/*
   DESCRIPTION
    This file has been generated from the Oracle 9i Web Services.

   NOTES
    other useful comments, qualifications, etc.
*/
import java.io.*;
import java.util.*;
import javax.naming.*;

import org.w3c.dom.*;
import oracle.xml.parser.v2.*;

import org.mssoapinterop.asmx.Test;
import org.mssoapinterop.asmx.TestHome;




/**
 * This is a simple client template. To compile it, 
 * please include the generated EJB jar file as well as
 * EJB and JNDI libraries in classpath.
 */
public class TestRpcDocClient
{
  // replace the values
  private static String RMI_HOST  = "localhost";
  private static String RMI_PORT  = "23791";
  private static String RMI_ADMIN = "admin";
  private static String RMI_PWD   = "welcome";

  public TestRpcDocClient () {}

  public static void main(String args[]) {

    TestRpcDocClient client = new TestRpcDocClient();

    try {

      RMI_HOST  = args[0];
      RMI_PORT  = args[1];
      RMI_ADMIN = args[2];
      RMI_PWD   = args[3];

      Hashtable env = new Hashtable();
      env.put(Context.INITIAL_CONTEXT_FACTORY, "com.evermind.server.rmi.RMIInitialContextFactory");
      env.put(Context.SECURITY_PRINCIPAL, RMI_ADMIN);
      env.put(Context.SECURITY_CREDENTIALS, RMI_PWD);
      env.put(Context.PROVIDER_URL, "ormi://" + RMI_HOST + ":" + RMI_PORT + "/Wsdl2EjbTestApp1");
      Context ctx = new InitialContext(env);
      TestHome home = (TestHome) ctx.lookup("mssoapinterop.org/asmx/DocAndRpc.asmx");
      
      Test service = home.create();
      
      // call any of the Remote methods below to access the EJB
      
      //
      // Add test
      //
      Document  doc = new XMLDocument();
      Element elAdd = doc.createElementNS("http://soapinterop.org", "s:Add");
      Element   elA = doc.createElementNS("http://soapinterop.org", "s:a");
      Element   elB = doc.createElementNS("http://soapinterop.org", "s:b");
      elA.appendChild(doc.createTextNode("4"));
      elB.appendChild(doc.createTextNode("3"));
      elAdd.appendChild(elA);
      elAdd.appendChild(elB);
      doc.appendChild(elAdd);
      
      Element elAddResponse = service.add(elAdd);
      Node tNode = elAddResponse.getFirstChild().getFirstChild();
      System.out.println("AddResponse: "+tNode.getNodeValue());

      //
      // Multiply Test
      //
      int a = 4;
      int b = 3;
      int iMultiplyResponse = service.multiply(a, b);
      System.out.println("MultiplyResponse: "+iMultiplyResponse);
      
      
    } 
    catch (Throwable ex) {
      ex.printStackTrace();
    }
  }
} 
