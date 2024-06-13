package com.softwaretunnel.javaapp_h2.persistance;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;

import com.softwaretunnel.javaapp_h2.Properties;

public class H2Interaction {
	
	
	private static H2Interaction h2Interaction= new H2Interaction();
	private static Connection connection;
    
	private H2Interaction() {
				
	}
	
	public static H2Interaction getH2Interaction() throws Exception
	{
		try {
		connection = createDatabaseAndConnection();
		}catch (Exception e) {
			throw e;
		}finally{
		    if(connection != null){
		    	connection.close();
		    }
		}
		return h2Interaction;
	}
	
	
	
	public static Connection createDatabaseAndConnection() throws Exception
	{
		JdbcDataSource ds = new JdbcDataSource();
		Connection newConnection = null;
		ds.setURL("jdbc:h2:"+Properties.H2_PATH+"h2db");
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
	
	public void createSchema() throws Exception {
		try {
		connection.createStatement().execute("RUNSCRIPT FROM '"+ Properties.H2_PATH+"h2init.sql'");
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
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
