SET ECHO ON
SET FEEDBACK 1
SET NUMWIDTH 10
SET LINESIZE 80
SET TRIMSPOOL ON
SET TAB OFF
SET PAGESIZE 100

------------------------------------------
------create the Queue and the Topic ------
-------------------------------------------
connect JEMUSER/JEMPASSWD;

begin 
 wsdemoAdmin.dropTpcs('topic1', 'WS_SUBSCRIBER');

 wsdemoAdmin.createTpcs('topic1', 'WS_SUBSCRIBER'); 

 wsdemoAdmin.dropQues('queue1');

 wsdemoAdmin.createQues('queue1');


end;
/
