This demo shows database as webservies client. This demo depends on the SQL statement demo, located at ../sqlstatement.

Deploy the Web Service
======================
Assemble and deploy the SQL statement demo located at ../sqlstatement. 

Load Client-Supporting Libraries 
================================
(1) Load client supporting libraries for 9.2 databases. 
(1.1) Make sure the shared_pool_size and java_pool_size in pfile (.ora) are equal to or greater than 96M and 80M, i.e.,

shared_pool_size=96M
java_pool_size=80M

Restart the database, if pfile is modified. 

(1.2) Load the client jar file. Ignore class resolution errors for oracle.dms.* classes.

% loadjava -u sys/change_on_install -r -v -s -f -grant public \
	${ORACLE_HOME}/soap/lib/soap.jar \
	${ORACLE_HOME}/dms/lib/dms.jar \
	${J2EE_HOME}/lib/servlet.jar \
	${J2EE_HOME}/lib/ejb.jar \
	${J2EE_HOME}/lib/mail.jar 

(2) Load client supporting libraries for 10.1 databases. 
(2.1) Make sure the shared_pool_size and java_pool_size in pfile (.ora) are equal to or greater than 96M and 80M, i.e.,

shared_pool_size=96M
java_pool_size=80M

Restart the database, if pfile is modified. 

(2.2) Load the client support jar file. Ignore class resolution errors with oracle.dms.* classes and possibly oracle/security/ssl/OracleSSLCredential.

% loadjava -u sys/change_on_install -r -v -s -f -grant public \
	${J2EE_HOME}/lib/jssl-1_2.jar \
	${ORACLE_HOME}/soap/lib/soap.jar \
	${ORACLE_HOME}/dms/lib/dms.jar \
	${J2EE_HOME}/lib/servlet.jar \
	${J2EE_HOME}/lib/ejb.jar \
	${J2EE_HOME}/lib/mail.jar 

Load the Client Proxy
=====================
% loadjava -u scott/tiger -r -v ../sqlstatement/proxy/oracle/demo/db/sqlstatement/proxy/SqlStmts_proxy.jar

Grant Client Necessary Privileges
=========================

Run the following script. Do not forget to change from localhost to the actual host of the OC4J instance. 

SQL> conn / as sysdba
SQL>
BEGIN
  dbms_java.grant_permission( 'SCOTT', 'SYS:java.util.PropertyPermission', '*', 'read,write' );
  dbms_java.grant_permission( 'SCOTT', 'SYS:java.net.SocketPermission', 'localhost', 'resolve' );
  dbms_java.grant_permission( 'SCOTT', 'SYS:java.net.SocketPermission', 'localhost:8888', 'connect,resolve' );
  dbms_java.grant_permission( 'SCOTT', 'SYS:java.lang.RuntimePermission', 'getClassLoader', '' );
END;
/

Create and Run the Client
=========================
SQL> conn scott/tiger
SQL>
create or replace and resolve java source named test as 
    public class Test 
    {
      public static String mainx() throws Exception
      {
        oracle.demo.db.sqlstatement.proxy.SqlStmtsProxy s = new oracle.demo.db.sqlstatement.proxy.SqlStmtsProxy();
        return "" + s.updateEmp("list");
      }
    }
/
 
SQL> create or replace function myfx return varchar2 is language java name 'Test.mainx() return String';
/

SQL>  select myfx  from dual;

MYFX
--------------------------------------------------------------------------------
0
