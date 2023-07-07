package com.snva.employeelist.DBService;

import com.snva.employeelist.bean.Employee;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public interface IEmployeeDBService {
    Connection createConnection (String uname,String Password);
    public boolean addNewEmployeeToDb( Employee employee);
    public void removeEmployeeFromDB( String name);
    public List showAllEmployeeInDB();
    public List<Employee> searchEmployeeByNameInDB(String name);
    public List<Employee> sortEmployeeInDB();
}
