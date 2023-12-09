import java.io.*;
import sp.company.proxy.*;
import org.w3c.dom.*;

/*
  A client for stored procedure web service, which uses the 
  downloaded proxy.
 */

public class TestSP
{
  public static void main(String args[])
  {
    try
      {
        CompanyProxy ci = new CompanyProxy();
        sp_company_AddressUser addr = new sp_company_AddressUser();
	addr.setState("OR");
	addr.setCity("Portland");
	addr.setStreet("SW 5Th Ave.");
	addr.setZip("97204");
        Integer id = new Integer(118);
            
        java.util.Date hireDate = new java.util.Date();
            
        sp_company_EmployeeUser emp = new sp_company_EmployeeUser();
	emp.setAddr(addr);
	emp.setEfirstname("Tony");
	emp.setEid(id);
	emp.setElastname("XYZ");
	emp.setSalary(new Double(1000.00));

        ci.addEmp(emp);
        System.out.println("Successfully added an employee");
           
        //NOTE: Due to current JDBC limitaton 
        //fractional seconds are lost. Thus this is not tested here!
        ci.setEmpHireDate(id, hireDate);
        java.util.Date retDate = ci.getEmpHireDate(id);
        System.out.println("TIMESTAMP test before: " + hireDate.toString() + ", after: " + retDate.toString());

        ci.setEmpEsppStatus(id, Boolean.TRUE);
        System.out.println("BOOLEAN Test returned " + ci.getEmpEsppStatus(id));
        sp_company_AddressUser return_addr = ci.getEmpAddress(id);
        System.out.println(toString(return_addr));

        sp_company_EmployeeUser return_emp = ci.getEmp(id);
        System.out.println(toString(return_emp));

        ci.removeEmp(id);
        System.out.println("Successfully removed an employee");

	System.out.println("*** Start PL/SQL RECORD and TABLE test ***");
	//sp_company_CompanybaseManagerRecUser rec = new sp_company_CompanybaseManagerRecUser(new java.math.BigDecimal(10), new java.math.BigDecimal(11));

      sp_company_CompanybaseManagerRecUser rec = new sp_company_CompanybaseManagerRecUser();
        rec.setManagerId(new java.math.BigDecimal(11));
        rec.setEmpId(new java.math.BigDecimal(10));
	sp_company_CompanybaseManagerRecUser[] recs = new sp_company_CompanybaseManagerRecUser[]{rec};
	sp_company_CompanybaseManagerTable tbl = new sp_company_CompanybaseManagerTable(recs);
	tbl = ci.getManagerTable(tbl);
	System.out.println("---- PL/SQL test returns " + tbl.getArray()[0].getEmpId() + "," +  tbl.getArray()[0].getManagerId());
    
	System.out.println("*** End PL/SQL RECORD and TABLE test ***");

        // XMLTYPE test
	System.out.println("*** Start XMLTYPE test ***");
        javax.xml.parsers.DocumentBuilder db =
        javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder();
        org.w3c.dom.Document doc =db.parse(new java.io.ByteArrayInputStream("<account> <name> email </name> <id> john.deer </id> <password> reed.nhoj </password></account>".getBytes()));

        Element ele = doc.getDocumentElement();
        org.w3c.dom.DocumentFragment x2 = doc.createDocumentFragment();
        x2.appendChild(ele);
        System.out.println("---- set accounts as DocumentFragment ");
        ci.setEmpAccounts(new java.math.BigDecimal(1001), x2);

        System.out.println("---- get accounts as DocumentFragment");
        x2 = ci.getEmpAccounts(new java.math.BigDecimal(1001));

        javax.xml.transform.Transformer trans = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
        javax.xml.transform.dom.DOMSource doms = new javax.xml.transform.dom.DOMSource(x2);
        java.io.ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
        javax.xml.transform.stream.StreamResult streamr = new javax.xml.transform.stream.StreamResult(buf);
        trans.transform(doms, streamr);
        System.out.println("---- returns " + buf.toString());
	System.out.println("*** End XMLTYPE test ***");
    }
    catch(Throwable t)
    {
        t.printStackTrace();
        System.out.println("Exception caught!!");
    }
  }
    
  private static String toString(sp_company_AddressUser addr) {
    return addr.getStreet() + " " + addr.getCity() + " " + addr.getState() + " " + addr.getZip();
  }

  private static String toString(sp_company_EmployeeUser emp) {
    return emp.getEfirstname() + " " + emp.getElastname() + " " + emp.getEid() + emp.getSalary() + "\n" +
    toString(emp.getAddr()); 
  }
}

