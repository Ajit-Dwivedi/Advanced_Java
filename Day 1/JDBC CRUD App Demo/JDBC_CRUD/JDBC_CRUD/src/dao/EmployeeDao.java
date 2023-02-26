package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import pojos.Employee;

public interface EmployeeDao {
//CRUD method declaration
	//R : fetch emp details by dept n join date
	List<Employee> getEmpDetailsByDeptDate(String dept,String joinDate) throws SQLException;
	Optional<Employee> getEmpDetailsById(int empId)throws SQLException;
	//in case of success : populated Optional<Emp> : of()
	// empty() : o.w empty Optional
	//In  tester : orElseThrow
	//add a new method for inserting emp details
	String addEmpDetails(Employee e) throws SQLException;
	//add a method to update emp details
	String updateEmpDetails(int empId,double salIncr,String newDept)throws SQLException;
	//add a method to delete emp details
	String deleteEmpDetails(int empId)throws SQLException;
	
}
