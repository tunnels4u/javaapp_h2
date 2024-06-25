package com.softwaretunnel.javaapp_h2;

public class Properties {
	public static String PROJECT_RESOURCE_PATH = System.getProperty("user.dir").toString() + "/src/main/resources/";
	public static String H2_PATH = PROJECT_RESOURCE_PATH + "h2/";
	public static String H2_SCHEMA = PROJECT_RESOURCE_PATH + "h2/h2init.sql";
	public static String IMAGES_PATH = PROJECT_RESOURCE_PATH + "images/";
	public static String SCHEMA_NAME = "TUNNELDATASCHEMA";
}