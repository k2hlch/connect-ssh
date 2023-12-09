/* create the package header */
create or replace package company as

procedure add_emp (emp IN employee);
function get_emp (id IN int) return employee;
function get_emp_address (id IN int) return address;
function get_emp_info(id IN int, firstname OUT varchar2, lastname OUT varchar2)
    return float;

procedure set_emp_espp_status (id IN int , status IN boolean);
function get_emp_espp_status(id IN int) return boolean;

/* change salary; return previous salary */
procedure change_emp_salary(id IN int, newsalary IN OUT float);

/* remove an employee */
procedure remove_emp (id IN int);

procedure set_emp_biodata (id int, biodata IN CLOB);
function get_emp_biodata(id IN int) return CLOB;

procedure set_emp_image (id int, image IN BLOB);
function get_emp_image(id IN int) return BLOB;

procedure set_emp_accounts(id IN number, accounts sys.xmltype);
function get_emp_accounts(id IN number) return sys.xmltype;

procedure set_emp_hire_date (id IN int, hiredate IN TIMESTAMP);
function get_emp_hire_date(id IN int) return TIMESTAMP;

TYPE manager_rec is RECORD (emp_id int, manager_id int); 
TYPE manager_table is TABLE OF manager_rec INDEX BY BINARY_INTEGER;
function get_manager_rec(mrec manager_rec) return manager_rec;
function get_manager_table(mtbl manager_table) return manager_table;

end company;
/
show errors
