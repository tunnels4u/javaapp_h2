package com.softwaretunnel.javaapp_h2.persistance;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;

public class H2Interaction {
	
	
	private static H2Interaction h2Interaction= new H2Interaction();
	private static Connection connection;
	public  static String DB_SOURCE=System.getProperty("user.dir").toString()+"/src/main/resources/";
    
	private H2Interaction() {
		String dir = System.getProperty("user.dir");		
	}
	
	public static H2Interaction getH2Interaction() throws Exception
	{
		try {
		connection = createDatabaseAndConnection();
		}catch (Exception e) {
			throw e;
		}
		return h2Interaction;
	}
	
	
	
	public static Connection createDatabaseAndConnection() throws Exception
	{
		JdbcDataSource ds = new JdbcDataSource();
		Connection newConnection = null;
		ds.setURL("jdbc:h2:"+DB_SOURCE+"h2db");
		ds.setUser("sa");
		ds.setPassword("sa");
		try {
		newConnection = ds.getConnection();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return newConnection;
	}
	
	public void createSchema() {
		try {
		String dir = System.getProperty("user.dir");
		connection.createStatement().execute("RUNSCRIPT FROM '"+ dir.toString()+"/src/main/resources/h2init.sql'");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dropSchema() {
		try {
		
		connection.createStatement().execute("DROP SCHEMA tunneldataschema");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
