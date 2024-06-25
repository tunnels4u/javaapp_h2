package com.softwaretunnel.javaapp_h2.persistance;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.h2.jdbcx.JdbcDataSource;

import com.softwaretunnel.javaapp_h2.persistance.domain.Employee;

public class H2Interaction {

	private static H2Interaction h2Interaction = new H2Interaction();
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("system");
	private static Connection connection;

	private H2Interaction() {
	}

	public static boolean doesH2DBExists() {
		String path = resourceBundle.getString("H2_PATH") + "h2db.mv.db";
		File dbFile = new File(path);
		if (dbFile.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public static H2Interaction getH2Interaction() throws Exception {
		try {
			if (connection == null) {
				connection = h2Interaction.createDatabaseAndConnection();
			}
		} catch (Exception e) {
			throw e;
		}
		return h2Interaction;
	}

	public Connection createDatabaseAndConnection() throws Exception {
		JdbcDataSource ds = new JdbcDataSource();
		Connection newConnection = null;
		ds.setURL("jdbc:h2:" + resourceBundle.getString("H2_PATH") + "h2db");
		ds.setUser("sa");
		ds.setPassword("sa");
		try {
			newConnection = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return newConnection;
	}

	public void createSchema() throws Exception {
		try {
			connection.createStatement().execute("RUNSCRIPT FROM '" + resourceBundle.getString("H2_SCHEMA") + "'");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void dropSchema() throws Exception {
		try {

			connection.createStatement()
					.execute("DROP SCHEMA IF EXISTS " + resourceBundle.getString("SCHEMA_NAME") + " CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void dropDatabase() throws Exception {
		try {
			connection.createStatement().execute("DROP ALL OBJECTS DELETE FILES");
			connection.createStatement().execute("SHUTDOWN");
			connection = null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public boolean doesSchemaExists() throws Exception {
		try {

			ResultSet rs = connection.createStatement()
					.executeQuery("SELECT * FROM INFORMATION_SCHEMA.SCHEMATA  WHERE SCHEMA_NAME = '"
							+ resourceBundle.getString("SCHEMA_NAME") + "'");
			if (rs.next() && rs.getString("SCHEMA_NAME").equals(resourceBundle.getString("SCHEMA_NAME"))) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}

	public void insertEmployeeRecords(List<Employee> employeeList) throws Exception {

		try {
			Statement statement = connection.createStatement();
			for (Employee employee : employeeList) {

				statement.addBatch("INSERT INTO TUNNELDATASCHEMA.EMPLOYEE (First_Name,Last_Name) VALUES ( " + "'"
						+ employee.getFirstName() + "'," + "'" + employee.getLastName() + "')");
			}
			statement.executeBatch();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public void updateEmployeeRecords(List<Employee> employeeList) throws Exception {

		try {
			Statement statement = connection.createStatement();
			for (Employee employee : employeeList) {

				statement.addBatch("UPDATE TUNNELDATASCHEMA.EMPLOYEE SET " + "First_Name = '" + employee.getFirstName()
						+ "'," + "Last_Name = '" + employee.getLastName() + "'" + "WHERE ID = " + employee.getID());

				System.out.println(statement);
			}
			statement.executeBatch();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public ArrayList<Employee> getEmployeeRecords() throws Exception {
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		try {
			ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM TUNNELDATASCHEMA.EMPLOYEE");

			while (rs.next()) {
				Employee employee = new Employee();
				employee.setID(rs.getInt("ID"));
				employee.setFirstName(rs.getString("First_Name"));
				employee.setLastName(rs.getString("Last_Name"));
				employeeList.add(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return employeeList;
	}

	public void releaseResources() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
