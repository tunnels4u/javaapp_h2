package com.softwaretunnel.javaapp_h2.persistance;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.h2.jdbcx.JdbcDataSource;

public class H2Interaction {

	private static H2Interaction h2Interaction = new H2Interaction();
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("system");
	private static Connection connection;

	private H2Interaction() {
	}

	public static boolean doesH2DBExists() {
		String path = resourceBundle.getString("H2_PATH") + "h2db.mv.db";
		System.out.println(path);
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
