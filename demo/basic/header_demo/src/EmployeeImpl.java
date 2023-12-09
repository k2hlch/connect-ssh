import org.apache.soap.Header;
import org.w3c.dom.*;
import oracle.xml.parser.v2.*;
import java.util.Vector;
import java.sql.*;
import javax.sql.DataSource;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *  Java class implementation for Employee service interface
 */
public class EmployeeImpl implements Employee{
 
  private String userName;
  private String password;
  private String datasourceName;

  // default constructor
  public EmployeeImpl() {
  }

  // A method that retrieves the Employee salary
  // for the given Employee name
  public int getEmployeeSalary (String eName) 
  {
    int sal = 0;
    Hashtable env = new Hashtable ();
    try {
      InitialContext ic = new InitialContext(env);
      
      // Get the datasource
      DataSource ods = (DataSource)
        ic.lookup(datasourceName);
                            
      // Get the connection
      Connection conn = ods.getConnection(userName, password);
      // Create a prepared statement
      PreparedStatement pstmt = 
        conn.prepareStatement ("select SAL from EMP where ENAME = ?");
      
      // Set the Employee name
      pstmt.setString (1, eName);
      //Execute the statement          
      ResultSet rs = pstmt.executeQuery();
      rs.next();
      // Get the result
      sal = rs.getInt(1);
      // Close the resources
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception sqe) {
      sqe.printStackTrace();
    }

    // return the salary
    System.out.println("Salary is: " + sal);
    return sal;
  }

  /**
   * Method to process the Headers first.
   * The Header as a XML Element with user name,
   * password, & datasource information to
   * be used to get the Salary from the
   * backend database schema.
   */ 
  public void processHeaders(Header header) 
     throws java.io.IOException,
            oracle.xml.parser.v2.XSLException
  { 
    // Get all the Elements
    Vector entries = header.getHeaderEntries();
    Element e = (Element) entries.firstElement();
    System.out.println("Element received from SOAP header is: " );
    ((XMLElement)e).print(System.out);
   
    // Get independent nodes and retrieve node values.
    Node userNode;  
    userNode = ((XMLNode)e).selectSingleNode("username");
    userName = ((XMLElement)userNode).getText();

    Node passwordNode;
    passwordNode = ((XMLNode)e).selectSingleNode("password");
    password = ((XMLElement)passwordNode).getText();

    Node dsNode;  
    dsNode = ((XMLNode)e).selectSingleNode("datasource");
    datasourceName = ((XMLElement)dsNode).getText();

    System.out.println("User name is: " + userName);
    System.out.println("Password is: " + password);
    System.out.println("Datasource is: " + datasourceName);
  }
}

