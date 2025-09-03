package com.crediya.model.exceptions;

public class ExceptionMessages {
	public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
	public static final String BUSINESS_ERROR = "BUSINESS_ERROR";

	public static final String STATUS_PENDING_NOT_FOUND = "Status 'PENDING' not found";
	public static final String TYPE_NOT_FOUND = "Type not found with id: ";
	public static final String DOCUMENT_NOT_FOUND = "User not found with that document number";
	public static final String FIELD_ALREADY_REGISTERED = "Field is already registered";

	ExceptionMessages() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
}
