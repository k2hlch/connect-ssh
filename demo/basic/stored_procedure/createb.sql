create or replace package body company as

procedure add_emp(emp IN employee) is
begin
 insert into employees values(emp.eid, emp);
end;
procedure set_emp_biodata (id IN int,biodata IN CLOB) is
begin
     insert into employee_biodata values (id, biodata);
    commit;
end ;

procedure set_emp_image(id IN int,image IN BLOB) is
begin
     insert into employee_image values (id, image);
    commit;
end ;

procedure set_emp_hire_date(id IN int, hiredate IN TIMESTAMP) is
begin
     insert into employee_hiredate values (id, hiredate);
    commit;
end ;

/*Get the espp status of a given employee*/
function get_emp_espp_status(id IN int) return boolean is
espp int;
cursor mycur is select status from employee_espp where id=eid;
begin
  open mycur;
  fetch mycur into espp;
  if(espp = 1) then return true; 
  else return false;
  end if;
end;

/*Set the espp status of a given employee*/
procedure set_emp_espp_status (id IN int , status IN boolean) is
begin
   if (status) then
   insert into employee_espp values (id, 1);
   else
   insert into employee_espp values (id, 0);
   end if;
   commit;
end;

/* get employee */
function get_emp (id IN int) return employee is
out employee;
begin
    select emp into out
    from employees where eid = id;
    return out;
end;

/* get employee address */
function get_emp_address (id IN int) return address is
out address;
e employee;
begin
    select emp into e
    from employees where eid = id;
    out := e.addr;
    return out;
end;

/* get employee name and salary */
function get_emp_info(id IN int, firstname OUT varchar2, lastname OUT varchar2)
    return float is
e employee;
begin
    select emp into e
    from employees where eid = id;
    firstname := e.efirstname;
    lastname := e.elastname;
    return e.salary;
end;

/* change salary; return previous salary */
procedure change_emp_salary(id IN int, newsalary IN OUT float) is
sal float;
e employee;
begin
    select emp into e
    from employees where eid = id;
    sal := e.salary;
    e.salary := newsalary;
    /* update the salary */
    update employees set emp = e where eid = id;
    commit;
    newsalary := sal;
end;

/* remove an employee */
procedure remove_emp (id IN int) is
begin
    delete from employees where eid = id;
    commit;
end;

/*Get employee's biodata*/
function get_emp_biodata(id IN int) return CLOB is
emp_biodata CLOB;
cursor mycur is select biodata from employee_biodata where eid=id;
begin
    open mycur;
    fetch mycur into emp_biodata;
    return emp_biodata;    
end;

/**/
function get_emp_image(id IN int) return BLOB is
emp_image BLOB;
cursor mycur is select image from employee_image where eid=id;
begin
    open mycur;
    fetch mycur into emp_image;
    return emp_image;    
end;

/*get an employee's hiredate*/
function get_emp_hire_date(id IN int) return TIMESTAMP is
emp_hiredate TIMESTAMP;
cursor mycur is select hiredate from employee_hiredate where eid=id;
begin
    open mycur;
    fetch mycur into emp_hiredate;
    return emp_hiredate;
end;

procedure set_emp_accounts(id IN number, accounts sys.xmltype) is
begin
   insert into employee_accounts values(id, accounts);
end;

function get_emp_accounts(id IN number) return sys.xmltype is
   rval sys.xmltype;
   cursor acc_cur is select accounts from employee_accounts where id=eid;
begin
   open acc_cur;
   fetch acc_cur into rval;
   return rval;
end;

function get_manager_rec(mrec manager_rec) return manager_rec is
  rval manager_rec;
begin
  rval.emp_id := mrec.emp_id;
  rval.manager_id := mrec.manager_id;
  return rval;
end;
function get_manager_table(mtbl manager_table) return manager_table is
  rval manager_table;
begin 
  rval := mtbl;
  return rval;
end;
end company;
/
show errors
