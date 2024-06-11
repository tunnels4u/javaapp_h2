package com.softwaretunnel.javaapp_h2.persistance;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;

public class H2Interaction {
	
	
	private static H2Interaction h2Interaction= new H2Interaction();
	private Connection connection;
    
	private H2Interaction() {
		
	}
	
	public static H2Interaction getH2Interaction() {
		return h2Interaction;
	}
	
	
	
	public void createDatabase()
	{
		JdbcDataSource ds = new JdbcDataSource();
		String dir = System.getProperty("user.dir");
		ds.setURL("jdbc:h2:"+dir.toString()+"/src/main/resources/h2db");
		ds.setUser("sa");
		ds.setPassword("sa");
		try {
		connection = ds.getConnection();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createSchema() {
		try {
		String dir = System.getProperty("user.dir");
		connection.createStatement().execute("RUNSCRIPT FROM '"+ dir.toString()+"/src/main/resources/h2init.sql'");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
