package com.softwaretunnel.javaapp_h2.persistance;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;

import com.softwaretunnel.javaapp_h2.Properties;

public class H2Interaction {

	private static H2Interaction h2Interaction = new H2Interaction();
	private static Connection connection;

	private H2Interaction() {

	}

	public static boolean doesH2DBExists() {
		String path = Properties.H2_PATH + "h2db.mv.db";
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
		ds.setURL("jdbc:h2:" + Properties.H2_PATH + "h2db");
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
			connection.createStatement().execute("RUNSCRIPT FROM '" + Properties.H2_SCHEMA + "'");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void dropSchema() throws Exception {
		try {

			connection.createStatement().execute("DROP SCHEMA " + Properties.SCHEMA_NAME + " CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public boolean doesSchemaExists() throws Exception {
		try {

			ResultSet rs = connection.createStatement().executeQuery(
					"SELECT * FROM INFORMATION_SCHEMA.SCHEMATA  WHERE SCHEMA_NAME = '" + Properties.SCHEMA_NAME + "'");
			if (rs.next() && rs.getString("SCHEMA_NAME").equals(Properties.SCHEMA_NAME)) {
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
