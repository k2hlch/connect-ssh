SET ECHO ON
SET FEEDBACK 1
SET NUMWIDTH 10
SET LINESIZE 80
SET TRIMSPOOL ON
SET TAB OFF
SET PAGESIZE 100


--
connect JEMUSER/JEMPASSWD;

------------------------------------------------------------
 variable  TableName  CHAR(50);
 --
 execute  :TableName := '**** user_queues ; available Qs and Topics';
 print  TableName;


 select  name, queue_table, queue_type, enqueue_enabled,
         dequeue_enabled, user_comment, max_retries, retry_delay,
         retention                                               
   from user_queues order by name;

------------------------------------------------------------
--you should see '4 rows selected' above                   ---
--you should see QUEUE1 and TOPIC1 of type NORMAL_QUEUE  --- 
--in addition to two  EXCEPTION_QUEUE                      ---      
------------------------------------------------------------ 

variable  TableName  CHAR(50);

 --
 execute  :TableName := '**** Topic-Messages; AQ$QTtpc';
 print  TableName;

 select queue, msg_id, msg_state,consumer_name,sender_name  from 
   JEMUSER.AQ$QTtpc;

------------------------------------------------------------
 variable  TableName  CHAR(50);

 --
 execute  :TableName := '**** Queue-Messages; AQ$QTque';
 print  TableName;

 select queue, msg_id, msg_state,sender_name  from 
   JEMUSER.AQ$QTque;

/
