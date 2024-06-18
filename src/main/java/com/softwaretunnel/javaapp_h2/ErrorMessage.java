package com.softwaretunnel.javaapp_h2;

public enum ErrorMessage {
	DB_CREATION_FAILED("Database could not be created, check logs"),
	SCHEMA_CREATION_FAILED("Schema could not be created, check logs"),
	SCHEMA_DROP_FAILED("Schema could not be dropped, check logs");

	public final String message;
	
	ErrorMessage(String message) {
        this.message = message;

	}
}
