package com.softwaretunnel.javaapp_h2;

import java.util.ResourceBundle;

import com.softwaretunnel.javaapp_h2.persistance.H2Interaction;

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
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	


	public void dropSchema() {

	}
}
