package com.snva.employeelist.uiservice;

import java.util.*;

import com.snva.employeelist.DBService.IEmployeeDBService;
import com.snva.employeelist.DBService.IEmployeeDBServiceImpl;
import com.snva.employeelist.bean.Employee;
import com.snva.employeelist.service.EmployeeServiceImpl;
import com.snva.employeelist.service.IEmployeeService;
import com.snva.employeelist.service.exception.EmployeeServiceException;
import com.snva.employeelist.util.ReadUtil;


/**
 *This is an EmployeeUIServiceImpl class which implements the IEmployeeUIService
 *interface and defines all the abstract method of the interface.
 */
public class EmployeeUIServiceImpl implements IEmployeeUIService
{
	private ReadUtil m_readUtil;

	private IEmployeeService m_employeeService;
	IEmployeeDBService dbService;

	/**
	 *This is the default constructor of the class which creates objects of
	 *all the instance variables of the class.
	 */
	public EmployeeUIServiceImpl()
	{
		m_readUtil= new ReadUtil();
		m_employeeService=new EmployeeServiceImpl();
		dbService = new IEmployeeDBServiceImpl();
	}
	/**
	 *This function read an employee detail and add that employee to the list.
	 */
	public void AddNewEmployee()
	{
		Employee employee=new Employee();
		employee.setFirstName(m_readUtil.readString("Input Your First name","String cannot be empty"));
		employee.setLastName(m_readUtil.readString("Input your Last name","Sring cannot be empty"));
		employee.setDesignation(m_readUtil.readString("Input Designation","String cannot be empty"));
		employee.setContactNumber(m_readUtil.readDouble("Input Contact number","Input correct values"));
		employee.setSalary(m_readUtil.readDouble("Input Your Salary","Input Correct Values"));
		employee.setDateOfBirth(m_readUtil.readDate("Input Date of Birth (DD-MM-YYYY)","enter valid date format(DD-MM-YYYY)"));
		employee.setDateOfJoining(m_readUtil.readDate("Input Date Of Joining (DD-MM-YYYY)","enter valid date format(DD-MM-YYYY)"));
		m_employeeService.addNewEmployee(employee);
	}
	/**
	 * This function remove an employee if the list contains that employee.
	 * It reads the employee name from the user and search for it in the list then
	 * make a call for removing that employee.
	 * @exception EmployeeServiceException This exception is thrown when user
	 * want to see the employee detail and the list is empty.
	 */
	public void removeEmployee(){

		try{
			String name = m_readUtil.readString("Enter Employee name(or any part of name) : ","String cannot be empty");
			dbService.removeEmployeeFromDB(name);

		} catch(NullPointerException e){
			System.out.println("Emloyee not found with this name");
		}
	}
	/**
	 * This function shows the information of all the employee containing in the list.
	 * @exception EmployeeServiceException thrown when user want to see the employee
	 * detail and the list is empty.
	 */
	public void showAllEmployee()
	{
		List<Employee> employeelist=dbService.showAllEmployeeInDB();
		System.out.println("All Employees Information : \n ");
		printList(employeelist);

	}
	/**
	 * This function search an employee in the list by its name.
	 * @param Name. It can be first name,last name or full name of the employee
	 * @return List. Returns the list of employee having the name from which search is initiated.
	 * @exception StringIndexOutOfBoundException Thrown by String methods to
	 * indicate that an index is either negative or greater than the size of the
	 * string.
	 * @exception NullPointerException Thrown when an application
	 * attempts to use null in a case where an object is required.
	 */
	 public List<Employee> searchEmployeeByName(String name) throws NullPointerException {

		 name = name.toLowerCase();
		 List<Employee> employeeList = dbService.searchEmployeeByNameInDB(name);
		 printList(employeeList);
		 if((employeeList==null) || (employeeList.size()==0))
			throw new NullPointerException();

		return employeeList;
	}
	 /**
	  * This function sorts the complete list according to first name. It uses the
	  * another class which implements the comparator interface and
	  * its function compareTo().
	  * @exception EmployeeServiceException exception is thrown when user want to
	  * see the employee detail and the list is empty.
	  * @exception ClassCastException Thrown to indicate that the code has attempted
	  * to cast an object to a subclass of which it is not an instance.
	  * @exception UnsupportedOperationException Thrown to indicate that the requested
	  * operation is not supported.
	  */
	public void sortEmployee()
	{
		try
		 {
			List<Employee> employeelist=dbService.sortEmployeeInDB();
			printList(employeelist);

		 } catch(ClassCastException e){
			System.out.println(e.getMessage());
		 }catch(UnsupportedOperationException e){
			System.out.println(e.getMessage());
		 }
	 }

	/**
	 * This function prints the details of an Employee.
	 * @param employee Parameter of the employee bean.
	 */
	private void printInfo(Employee employee)
	{
		System.out.println("---------------------------------------");
		System.out.println("Employee ID  "+employee.getEmployeeId());
		System.out.println("First Name : "+employee.getFirstName());
		System.out.println("Last Name : "+employee.getLastName());
		System.out.println("Designation :"+employee.getDesignation());
		System.out.println("Salary : "+employee.getSalary());
		System.out.println("Date Of Birth : "+employee.getDateOfBirth());
		System.out.println("Date of Joining : "+employee.getDateOfJoining());
		System.out.println("-----------------------------------------");
		System.out.println();
	}
	/**
	 * This function prints the details of all Employee which are in the list.
	 * @param employeelist Parameter of the List(Employee bean).
	 */
	private void printList(List<Employee> employeelist)
	{
		Iterator<Employee> employeelistiterator=employeelist.iterator();
		while(employeelistiterator.hasNext())
		{
			Employee employee=employeelistiterator.next();
			printInfo(employee);
			System.out.println();
		}
	}
}
