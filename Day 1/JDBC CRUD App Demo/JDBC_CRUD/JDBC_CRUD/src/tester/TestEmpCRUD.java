package tester;

import java.sql.Date;
import java.util.Optional;
import java.util.Scanner;

import custom_exc.EmpRelatedException;
import dao.EmployeeDaoImpl;
import pojos.Employee;
import static utils.DBUtils.*;

public class TestEmpCRUD {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			// open db cn n then create DAO instance : @ init phase of app
			openConnection();
			EmployeeDaoImpl dao = new EmployeeDaoImpl();
			boolean exit = false;
			while (!exit) {
				try {
					System.out.println("Menu : 1 : Fetch Emp Details 2. Fetch Emp details by id \n"
							+ "3 : Hire Employee 4. Update Emp  5.Delete Emp Details 10:Exit");
					switch (sc.nextInt()) {
					case 1:
						System.out.println("Enter dept id & join date(yyyy-[M]M-[d]d)");
						String dept = sc.next();
						String date = sc.next();
						System.out.println("Emp Details");
						dao.getEmpDetailsByDeptDate(dept, date).forEach(System.out::println);
						break;
					case 2: // display specific emp's details : PK empid
						// i/p : empid
						// o/p emp details or err mesg
						System.out.println("Enter emp id");
						Optional<Employee> optionalEmp = dao.getEmpDetailsById(sc.nextInt());
						System.out.println(
								optionalEmp.orElseThrow(() -> new EmpRelatedException("Invalid Emp ID!!!!!!")));
						break;
					case 3:
						System.out.println(
								"Enter emp details for insertion :  name    | addr    | salary | deptid | join_date");
						String status = dao.addEmpDetails(new Employee(sc.next(), sc.next(), sc.nextDouble(), sc.next(),
								Date.valueOf(sc.next())));
						System.out.println("Emp Hiring status :  " + status);
						break;
					case 4: // incr specific emp's salary n change dept id
						// i/p : empId sal incr new dept id
						System.out.println("Enter empid , salary increment & new dept id for updation");
						System.out.println(
								"Updation status " + dao.updateEmpDetails(sc.nextInt(), sc.nextDouble(), sc.next()));
						break;
					case 5: // delete emp details
						System.out.println("Enter emp id to remove the details");
						System.out.println("Deletion status "+dao.deleteEmpDetails(sc.nextInt()));
						break;

					case 10:
						exit = true;
						// before terminating app : clean up db resources
						dao.cleanUp();
						// close db cn
						closeConnection();
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// to read off pending i/ps from scanner
				sc.nextLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
