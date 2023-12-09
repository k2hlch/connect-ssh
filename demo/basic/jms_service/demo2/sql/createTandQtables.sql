SET ECHO ON
SET FEEDBACK 1
SET NUMWIDTH 10
SET LINESIZE 80
SET TRIMSPOOL ON
SET TAB OFF
SET PAGESIZE 100


-----------------------------------------------
------ create the Queue and Topic tables ------
-----------------------------------------------
connect JEMUSER/JEMPASSWD;

begin 
 wsdemoAdmin.dropQueTable('QTque');
 wsdemoAdmin.createQueTable('QTque');

 wsdemoAdmin.dropQueTable('QTtpc');
 wsdemoAdmin.createTpcTable('QTtpc');
end;
/
