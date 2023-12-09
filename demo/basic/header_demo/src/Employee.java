import oracle.j2ee.ws.HeaderCallback;

/**
 *  Employee java class being exposed as Web Services
 *  This service also extends HeaderCallback so as to
 *  access Headers.
 */
public interface Employee
  extends  HeaderCallback
{
  // Get the salary for a given Employee
  int getEmployeeSalary(String  ename);
}
