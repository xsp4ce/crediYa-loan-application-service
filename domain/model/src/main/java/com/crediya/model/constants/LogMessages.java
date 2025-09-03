package com.crediya.model.constants;

public class LogMessages {
	public static final String CONSUMING_PATH_REQUEST = "Consuming path: /api/v1/request";
	public static final String STARTING_TO_PROCESS_REQUEST = "Starting to process the request to save a loan application";
	public static final String FINDING_LOAN_TYPE = "Finding loan type by id: {}";
	public static final String SAVING_APPLICATION_IN_DATABASE = "Saving loan application in the database";
	public static final String VALIDATING_STATUS_IN_DATABASE = "Validating status pending exists in the database";
	public static final String AUTHENTICATED_USER = "Authenticated user ID: {}, Role: {}";

	public static final String DOCUMENT_REQUIRED = "Document required";
	public static final String DOCUMENT_MIN_LENGTH = "Document must be 8 characters";
	public static final String INVALID_AMOUNT = "Invalid amount";
	public static final String INVALID_TERM = "Invalid term";
	public static final String TYPE_REQUIRED = "Loan type is required";

	LogMessages() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
}
