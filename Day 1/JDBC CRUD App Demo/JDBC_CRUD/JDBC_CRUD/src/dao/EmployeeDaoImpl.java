package dao;

import static utils.DBUtils.getConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pojos.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	// Data members
	private Connection cn;
	private PreparedStatement pst1, pst2, pst3, pst4, pst5;

	// constr : will be invoked just once @ beginning (init phase) by the Tester
	public EmployeeDaoImpl() throws ClassNotFoundException, SQLException {
		// get cn from DBUtils
		cn = getConnection();
		// create pst1
		pst1 = cn.prepareStatement("select * from my_emp where deptid=? and join_date > ?");
		// pst2
		pst2 = cn.prepareStatement("select * from my_emp where empid=?");
		// pst3 : for insertion of a rec
		pst3 = cn.prepareStatement("insert into my_emp values(default,?,?,?,?,?)");
		// pst4 : for updating emp details
		pst4 = cn.prepareStatement("update my_emp set salary=salary+?,deptid=? where empid=?");
		// pst5 : for updating emp details
		pst5 = cn.prepareStatement("delete from my_emp where empid=?");
		System.out.println("emp dao created....");
	}

	@Override
	public List<Employee> getEmpDetailsByDeptDate(String dept, String joinDate) throws SQLException {
		// create empty AL
		ArrayList<Employee> emps = new ArrayList<>();
		// will be invoked by Tester : once per clnt request.
		// set IN params
		pst1.setString(1, dept);
		pst1.setDate(2, Date.valueOf(joinDate));
		// exec method : execQuery : select => RST : AutoCloseable
		try (ResultSet rst = pst1.executeQuery()) {
			// rst cursor : before the 1st
			while (rst.next())
				emps.add(new Employee(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDouble(4),
						rst.getString(5), rst.getDate(6)));

		}
		return emps;
	}

	@Override
	public Optional<Employee> getEmpDetailsById(int empId) throws SQLException {
		// set In param : emp id
		pst2.setInt(1, empId);
		// select : execQuery : RST
		try (ResultSet rst = pst2.executeQuery()) {
			if (rst.next())
				return Optional.of(new Employee(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDouble(4),
						rst.getString(5), rst.getDate(6)));
		}
		return Optional.empty();
	}

	@Override
	public String addEmpDetails(Employee e) throws SQLException {
		// set IN params : name | addr | salary | deptid | join_date
		pst3.setString(1, e.getName());// name
		pst3.setString(2, e.getAddress());// address
		pst3.setDouble(3, e.getSalary());// salary
		pst3.setString(4, e.getDeptId());// dept id
		pst3.setDate(5, e.getJoinDate());// join date
		// exec method : public int executeUpdate() throws SQLException
		int rowCount = pst3.executeUpdate();
		if (rowCount == 1)
			return "Emp details inserted .....";

		return "Emp details insertion failed....";
	}

	@Override
	public String updateEmpDetails(int empId, double salIncr, String newDept) throws SQLException {
		// set IN params
		pst4.setDouble(1, salIncr);
		pst4.setString(2, newDept);
		pst4.setInt(3, empId);
		// exec method : public int executeUpdate() throws SQLException
		int rowCount = pst4.executeUpdate();
		if (rowCount == 1)
			return "Emp details updated .....";

		return "Emp details updation  failed....";

	}

	@Override
	public String deleteEmpDetails(int empId) throws SQLException {
		// set IN param : emp id
		pst5.setInt(1, empId);
		// exec method : public int executeUpdate() throws SQLException
		int rowCount = pst5.executeUpdate();
		if (rowCount == 1)
			return "Emp details deleted .....";
		return "Emp details deletion  failed....";
	}

	// clean up : closing DB resources : will be invoked by a tester : just once @
	// end of app
	public void cleanUp() throws SQLException {
		if (pst1 != null)
			pst1.close();
		// pst2 close
		if (pst2 != null)
			pst2.close();
		// pst3 close
		if (pst3 != null)
			pst3.close();
		if (pst4 != null)
			pst4.close();
		if (pst5 != null)
			pst5.close();
		System.out.println("emp dao cleaned up....");
	}

}
