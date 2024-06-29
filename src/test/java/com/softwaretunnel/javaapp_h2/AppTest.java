package com.softwaretunnel.javaapp_h2;

import java.util.ArrayList;
import java.util.ResourceBundle;

import com.softwaretunnel.javaapp_h2.persistance.H2Interaction;
import com.softwaretunnel.javaapp_h2.persistance.domain.Employee;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */

public class AppTest extends TestCase {

	H2Interaction h2Interaction;

	public AppTest() {

	}

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}

	public void testCreateDatabase() {
		try {
			h2Interaction = H2Interaction.getH2Interaction();
			assertTrue(H2Interaction.doesH2DBExists());
			h2Interaction.dropDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testDropSchema() {
		try {
			h2Interaction = H2Interaction.getH2Interaction();
			h2Interaction.createSchema();
			h2Interaction.dropSchema();
			assertFalse(h2Interaction.doesSchemaExists());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			try {
				h2Interaction.dropDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void testCreateSchema() {
		try {
			h2Interaction = H2Interaction.getH2Interaction();
			h2Interaction.createSchema();
			assertTrue(h2Interaction.doesSchemaExists());
			h2Interaction.dropSchema();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			try {
				h2Interaction.dropDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void testInsertAndGetEmployeeRecord() {
		try {
			h2Interaction = H2Interaction.getH2Interaction();
			h2Interaction.createSchema();
			String firstName = "Tom";
			String lastName = "Greg";
			Employee[] employeeArray = new Employee[1];
			Employee employee = new Employee();
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employeeArray[0] = (employee);

			h2Interaction.insertEmployeeRecords(employeeArray);
			ArrayList<Employee> el = h2Interaction.getEmployeeRecords();
			assertTrue(el.size() == 1);
			assertTrue(el.get(0).getFirstName().equals(firstName));
			assertTrue(el.get(0).getLastName().equals(lastName));

			h2Interaction.dropSchema();

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			try {
				h2Interaction.dropDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void testUpdateEmployeeRecord() {
		try {
			h2Interaction = H2Interaction.getH2Interaction();
			h2Interaction.createSchema();
			String firstName = "Tom";
			String lastName = "Greg";
			String updatedLastName = "Gregman";
			Employee[] employeeArray = new Employee[1];
			Employee employee = new Employee();
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employeeArray[0] = (employee);

			h2Interaction.insertEmployeeRecords(employeeArray);
			ArrayList<Employee> el = h2Interaction.getEmployeeRecords();
			assertTrue(el.size() == 1);
			Employee updateEmployee = el.get(0);
			updateEmployee.setLastName(updatedLastName);

			h2Interaction.updateEmployeeRecords(updateEmployee);
			ArrayList<Employee> updatedEl = h2Interaction.getEmployeeRecords();
			assertTrue(updatedEl.size() == 1);
			assertTrue(updatedEl.get(0).getFirstName().equals(firstName));
			assertTrue(updatedEl.get(0).getLastName().equals(updatedLastName));

			h2Interaction.dropSchema();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			try {
				h2Interaction.dropDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void testDeleteEmployeeRecord() {
		try {
			h2Interaction = H2Interaction.getH2Interaction();
			h2Interaction.createSchema();
			String firstName = "Tom";
			String lastName = "Greg";
			String updatedLastName = "Gregman";
			Employee[] employeeArray = new Employee[1];
			Employee employee = new Employee();
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employeeArray[0] = (employee);

			h2Interaction.insertEmployeeRecords(employeeArray);
			ArrayList<Employee> el = h2Interaction.getEmployeeRecords();
			assertTrue(el.size() == 1);

			h2Interaction.deleteEmployeeRecords((Employee[]) el.toArray());
			ArrayList<Employee> records = h2Interaction.getEmployeeRecords();
			assertTrue(records.size() == 0);

			h2Interaction.dropSchema();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			try {
				h2Interaction.dropDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Keep it at last
	 */
	public void testDropDatabase() {
		try {
			H2Interaction h2Interaction = H2Interaction.getH2Interaction();
			h2Interaction.createSchema();
			h2Interaction.dropDatabase();
			assertFalse(h2Interaction.doesH2DBExists());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
