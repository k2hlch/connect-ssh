SET ECHO ON
SET FEEDBACK 1
SET NUMWIDTH 10
SET LINESIZE 80
SET TRIMSPOOL ON
SET TAB OFF
SET PAGESIZE 100


connect  sys/change_on_install as sysdba

------------------------------------------------------------
 declare
   sqlstmt1  VARCHAR2(1000);
   sqlstmt2  VARCHAR2(1000);
   sqlstmt3  VARCHAR2(1000);
    err_num  NUMBER;
    err_msg  VARCHAR2(100);
 begin
   begin
     sqlstmt1 :=
       'drop user JEMuser cascade';

     execute immediate sqlstmt1;
   exception
      when others then null;
   end;

   begin
     sqlstmt2 :=
       'drop role JEMrole';

     execute immediate sqlstmt2;
   exception
      when others then null;
   end;

 end;
/

------------------------------------------------------------
 create user JEMUSER identified by JEMPASSWD;

--
 grant create database link, create sequence, create session,
       create synonym, create public synonym, drop public synonym,
       create table, create view, create indextype,
       create procedure, create trigger, create type, 
       unlimited tablespace
    to  JEMUSER;

 grant aq_administrator_role      to  JEMUSER;
 grant execute on sys.dbms_aqadm  to  JEMUSER;
 grant execute on sys.dbms_aq     to  JEMUSER;

 grant execute on sys.dbms_aqin   to  JEMUSER;
 grant execute on sys.dbms_aqjms  to  JEMUSER;

 grant force any transaction      to  JEMUSER;


------------------------------------------------------------
--
connect JEMUSER/JEMPASSWD;

------------------------------------------------------------
--        
create or replace package  wsdemoAdmin
  AUTHID  DEFINER  as

  procedure  createQueTable(quetablename  IN VARCHAR2);
  procedure  createTpcTable(quetablename  IN VARCHAR2);
  procedure  dropQueTable(quetablename  IN VARCHAR2);

  procedure  createQues(
                dbusname                    IN     VARCHAR2);
  procedure  createTpcs(
                dbusname                    IN     VARCHAR2,
                subname                     IN     VARCHAR2);
  procedure  dropQues(dbusname  IN VARCHAR2);
  procedure  dropTpcs(dbusname  IN VARCHAR2,
                      subname   IN     VARCHAR2);
  procedure  addSubscriber(
                dbusname                    IN     VARCHAR2,
                subname                     IN     VARCHAR2);

end  wsdemoAdmin;
/
------------------------------------------------------------
------------------------------------------------------------
create or replace package body wsdemoAdmin  as

---------------------------------
---------------------------------
  procedure  createQues(
                dbusname                    IN     VARCHAR2)  is
    quetablename    VARCHAR2(100);
    quename    VARCHAR2(100);
  begin
    quetablename   := 'QTque';
    quename   := dbusname;
  
    --
    DBMS_AQADM.CREATE_QUEUE( 
      Queue_name          => quename, 
      Queue_table         => quetablename,
      max_retries         => '2');

    --
    DBMS_AQADM.START_QUEUE(
      queue_name         => quename);
 end;
---------------------------------
  procedure  createTpcs(
                dbusname                    IN     VARCHAR2,
                subname                     IN     VARCHAR2) is 
    quetablename    VARCHAR2(100);
    quename    VARCHAR2(100);
    subscriber   sys.aq$_agent; 
  begin
    quetablename   := 'QTtpc';
    quename   := dbusname;
  
    --
    DBMS_AQADM.CREATE_QUEUE( 
      Queue_name          => quename, 
      Queue_table         => quetablename,
      max_retries         => '2');

    --
    subscriber :=  
      sys.aq$_agent(subname, null, null); 

    DBMS_AQADM.ADD_SUBSCRIBER(
      queue_name           => quename, 
      subscriber           => subscriber);

    --
    DBMS_AQADM.START_QUEUE(
      queue_name         => quename);
 end;

---------------------------------
  procedure  addSubscriber(
                dbusname                    IN     VARCHAR2,
                subname                     IN     VARCHAR2) is 
    quename    VARCHAR2(100);
    subscriber   sys.aq$_agent; 
  begin
    quename   := dbusname;

    --
    subscriber :=  
      sys.aq$_agent(subname, null, null); 

    DBMS_AQADM.ADD_SUBSCRIBER(
      queue_name           => quename, 
      subscriber           => subscriber);
 end;

---------------------------------
  procedure  stopQue(dbusname  IN VARCHAR2)  is
    quename    VARCHAR2(100);
  begin
    quename   := dbusname;

    DBMS_AQADM.STOP_QUEUE(
        queue_name         => quename);
  exception
    when others then
       dbms_output.put_line('EXC: stopQue');
    return;
  end;
---------------------------------
  procedure  dropSubQue(dbusname  IN VARCHAR2,
                             subname   IN     VARCHAR2)   is
    quename    VARCHAR2(100);
    subscriber   sys.aq$_agent;
  begin
    quename   := dbusname;

    subscriber :=  
      sys.aq$_agent(subname, null, null); 

    DBMS_AQADM.REMOVE_SUBSCRIBER(
      queue_name           => quename, 
      subscriber           => subscriber);
  exception
    when others then
       dbms_output.put_line('EXC: dropSubQue');
    return;
  end;
---------------------------------
  procedure  dropQue(dbusname  IN VARCHAR2) is
    quename    VARCHAR2(100);
  begin
    quename   := dbusname;

    DBMS_AQADM.DROP_QUEUE( 
      Queue_name          => quename);
  exception
    when others then
       dbms_output.put_line('EXC: dropQue');
    return;
  end;
---------------------------------
  procedure  createQueTable(quetablename  IN VARCHAR2)   is
  begin
      DBMS_AQADM.CREATE_QUEUE_TABLE(
        Queue_table            => quetablename,
        Queue_payload_type     => 'SYS.AQ$_JMS_OBJECT_MESSAGE',
	multiple_consumers  => false,
        compatible             => '8.1.5');
  exception
    when others then
       dbms_output.put_line('EXC: createQueTable');
    return;
  end;
---------------------------------
  procedure  createTpcTable(quetablename  IN VARCHAR2)   is
  begin
      DBMS_AQADM.CREATE_QUEUE_TABLE(
        Queue_table            => quetablename,
        Queue_payload_type     => 'SYS.AQ$_JMS_OBJECT_MESSAGE',
	multiple_consumers  => TRUE,
        compatible             => '8.1.5');
  exception
    when others then
       dbms_output.put_line('EXC: createTpcTable');
    return;
  end;
---------------------------------
  procedure  dropQueTable(quetablename  IN VARCHAR2)   is
  begin
      DBMS_AQADM.DROP_QUEUE_TABLE(
        Queue_table            => quetablename);
  exception
    when others then
       dbms_output.put_line('EXC: dropQueTable');
    return;
  end;
---------------------------------
  procedure  dropQues(dbusname  IN VARCHAR2) is
  begin
    stopQue(dbusname);

    dropQue(dbusname);
  end;
---------------------------------
  procedure  dropTpcs(dbusname  IN VARCHAR2,
                      subname   IN     VARCHAR2) is
  begin
    stopQue(dbusname);

    dropSubQue(dbusname, subname);

    dropQue(dbusname);
  end;
---------------------------------

end  wsdemoAdmin;
/

------------------------------------------------------------

