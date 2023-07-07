package com.snva.employeelist.DBService;

import com.snva.employeelist.bean.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IEmployeeDBServiceImpl implements IEmployeeDBService{
    Connection connection;

    public IEmployeeDBServiceImpl() {
        this.connection = createConnection("root","root");
    }

    @Override
    public Connection createConnection(String uname, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDb", uname, password);
            if (connection == null) {
                System.out.println("Connection Failed");
            } else {
                System.out.println("Connection Success!!!");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addNewEmployeeToDb(Employee employee) {
        String sqlStmt = "Insert into Employee values(?,?,?,?,?,?,?,?)";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT max(EmployeeId)+1 FROM testdb.employee");
            rs.next();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStmt);
            preparedStatement.setInt(1,rs.getInt(1));
            preparedStatement.setString(2,employee.getFirstName());
            preparedStatement.setString(3,employee.getLastName());
            preparedStatement.setString(4,employee.getDesignation());
            preparedStatement.setDouble(5,employee.getContactNumber());
            preparedStatement.setDouble(6,employee.getSalary());
            preparedStatement.setDate(7, new Date(employee.getDateOfBirth().getTime()));
            preparedStatement.setDate(8, new Date (employee.getDateOfJoining().getTime()));
            int success = preparedStatement.executeUpdate();
            if(success == 1){
               return true;

            }
            else {
                return false;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeEmployeeFromDB(String name) {
        String sqlStmt = "Delete from Employee where Fname =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStmt);
            preparedStatement.setString(1,name);
            int result = preparedStatement.executeUpdate();
            if(result ==1){
                System.out.println("Employee " + name + " is deleted!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Employee> showAllEmployeeInDB() {
        try {
            Statement statement = connection.createStatement();
            List<Employee> employeeList = new ArrayList();
            ResultSet rs  = statement.executeQuery("Select * from Employee");
            while (rs.next()){
                Employee employee = new Employee(rs.getInt(1));
                employee.setFirstName(rs.getString(2));
                employee.setLastName(rs.getString(3));
                employee.setDesignation(rs.getString(4));
                employee.setContactNumber(rs.getDouble(5));
                employee.setSalary(rs.getDouble(6));
                employee.setDateOfBirth(rs.getDate(7));
                employee.setDateOfJoining(rs.getDate(8));

                employeeList.add(employee);

            }
            return employeeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Employee> searchEmployeeByNameInDB(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Select * from Employee where Fname Like ?");
            List<Employee> employeeList = new ArrayList();
            preparedStatement.setString(1,'%'+name+'%');
            ResultSet rs  = preparedStatement.executeQuery();
            while (rs.next()){
                Employee employee = new Employee(rs.getInt(1));
                employee.setFirstName(rs.getString(2));
                employee.setLastName(rs.getString(3));
                employee.setDesignation(rs.getString(4));
                employee.setContactNumber(rs.getDouble(5));
                employee.setSalary(rs.getDouble(6));
                employee.setDateOfBirth(rs.getDate(7));
                employee.setDateOfJoining(rs.getDate(8));

                employeeList.add(employee);

            }
            return employeeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Employee> sortEmployeeInDB() {
        try {
            Statement statement = connection.createStatement();
            List<Employee> employeeList = new ArrayList();
            ResultSet rs  = statement.executeQuery("Select * from Employee order by Fname");
            while (rs.next()){
                Employee employee = new Employee(rs.getInt(1));
                employee.setFirstName(rs.getString(2));
                employee.setLastName(rs.getString(3));
                employee.setDesignation(rs.getString(4));
                employee.setContactNumber(rs.getDouble(5));
                employee.setSalary(rs.getDouble(6));
                employee.setDateOfBirth(rs.getDate(7));
                employee.setDateOfJoining(rs.getDate(8));

                employeeList.add(employee);

            }
            return employeeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
