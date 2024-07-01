package com.softwaretunnel.javaapp_h2;

public enum ErrorMessage {
	DB_CREATION_FAILED("Database could not be created, check logs"),
	SCHEMA_CREATION_FAILED("Schema could not be created, check logs"),
	SCHEMA_DROP_FAILED("Schema could not be dropped, check logs"),
	DATABASE_DROP_FAILED("Schema could not be dropped, check logs"),
	FRAME_RENDERING_FAILED("Frame rendering failed because schema existance could not be determined, check logs"),
	FETCH_FAILED("Record/s could not be fecthed, check logs"),
	INSERT_FAILED("Record could not be inserted, check logs"), 
	UPDATE_FAILED("Record could not be updated, check logs"),
	DELETE_FAILED("Record could not be deleted, check logs"),
	INSERT_SUCCESS("Record inserted"), 
	UPDATE_SUCCESS("Record updated"),
	DELETE_SUCCESS("Record deleted");

	public final String message;

	ErrorMessage(String message) {
		this.message = message;

	}
}
