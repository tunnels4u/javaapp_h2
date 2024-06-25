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
			H2Interaction h2Interaction = H2Interaction.getH2Interaction();
			assertTrue(H2Interaction.doesH2DBExists());
			h2Interaction.dropDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testDropSchema() {
		try {
			H2Interaction h2Interaction = H2Interaction.getH2Interaction();
			h2Interaction.createSchema();
			h2Interaction.dropSchema();
			assertFalse(h2Interaction.doesSchemaExists());
			h2Interaction.dropDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testCreateSchema() {
		try {
			H2Interaction h2Interaction = H2Interaction.getH2Interaction();
			h2Interaction.createSchema();
			assertTrue(h2Interaction.doesSchemaExists());
			h2Interaction.dropSchema();
			h2Interaction.dropDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testInsertAndGetEmployeeRecord() {
		try {
			H2Interaction h2Interaction = H2Interaction.getH2Interaction();
			h2Interaction.createSchema();
			String firstName = "Tom";
			String lastName = "Greg";
			ArrayList<Employee> employeeList = new ArrayList<Employee>();
			Employee employee = new Employee();
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employeeList.add(employee);

			h2Interaction.insertEmployeeRecords(employeeList);
			ArrayList<Employee> el = h2Interaction.getEmployeeRecords();
			assertTrue(el.size() == 1);
			assertTrue(el.get(0).getFirstName().equals(firstName));
			assertTrue(el.get(0).getLastName().equals(lastName));

			h2Interaction.dropSchema();
			h2Interaction.dropDatabase();

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testUpdateEmployeeRecord() {
		try {
			H2Interaction h2Interaction = H2Interaction.getH2Interaction();
			h2Interaction.createSchema();
			String firstName = "Tom";
			String lastName = "Greg";
			String updatedLastName = "Gregman";
			ArrayList<Employee> employeeList = new ArrayList<Employee>();
			Employee employee = new Employee();
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employeeList.add(employee);

			h2Interaction.insertEmployeeRecords(employeeList);
			ArrayList<Employee> el = h2Interaction.getEmployeeRecords();
			assertTrue(el.size() == 1);
			Employee updateEmployee = el.get(0);
			updateEmployee.setLastName(updatedLastName);
			employeeList.removeAll(employeeList);
			employeeList.add(updateEmployee);

			h2Interaction.updateEmployeeRecords(employeeList);
			ArrayList<Employee> updatedEl = h2Interaction.getEmployeeRecords();
			assertTrue(updatedEl.size() == 1);
			assertTrue(updatedEl.get(0).getFirstName().equals(firstName));
			assertTrue(updatedEl.get(0).getLastName().equals(updatedLastName));

			h2Interaction.dropSchema();
			h2Interaction.dropDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void testDeleteEmployeeRecord() {
		try {
			H2Interaction h2Interaction = H2Interaction.getH2Interaction();
			h2Interaction.createSchema();
			String firstName = "Tom";
			String lastName = "Greg";
			String updatedLastName = "Gregman";
			ArrayList<Employee> employeeList = new ArrayList<Employee>();
			Employee employee = new Employee();
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employeeList.add(employee);

			h2Interaction.insertEmployeeRecords(employeeList);
			ArrayList<Employee> el = h2Interaction.getEmployeeRecords();
			assertTrue(el.size() == 1);

			h2Interaction.deleteEmployeeRecords(el);
			ArrayList<Employee> records = h2Interaction.getEmployeeRecords();
			assertTrue(records.size() == 0);

			h2Interaction.dropSchema();
			h2Interaction.dropDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
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
