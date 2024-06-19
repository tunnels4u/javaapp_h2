package com.softwaretunnel.javaapp_h2.persistance;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;

import com.softwaretunnel.javaapp_h2.Properties;

public class H2Interaction {

	private static H2Interaction h2Interaction = new H2Interaction();
	private static Connection connection;

	private H2Interaction() {

	}

	public static H2Interaction getH2Interaction() throws Exception {
		try {
			connection = createDatabaseAndConnection();
		} catch (Exception e) {
			throw e;
		}
		return h2Interaction;
	}

	public static Connection createDatabaseAndConnection() throws Exception {
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

			connection.createStatement().execute("DROP SCHEMA " + Properties.SCHEMA_NAME +" CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
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
