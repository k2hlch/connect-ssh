create type address as object (street varchar2(30), city varchar2(30),
        state varchar2(2), zip varchar2(5));
/
show errors

create type employee as object (eid int, efirstname varchar(30), elastname varchar(30), addr address, salary float);
/
show errors

create table employees (eid int, emp employee);
create table employee_espp (eid int, status int);
create table employee_accounts (eid int, accounts sys.xmltype); 
create table employee_biodata (eid int, biodata CLOB);
create table employee_image (eid int, image BLOB);
create table employee_hiredate(eid int, hiredate TIMESTAMP);

