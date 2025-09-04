package com.crediya.api.auth;

public class AuthLogMessage {
	public static final String DOCUMENT_PATH = "/api/v1/document";
	public static final String CONSUMING_PATH_AUTH_LOGIN = "Consuming path: " + DOCUMENT_PATH + " in auth-ms";
	public static final String VERIFYING_USER_DOCUMENT = "Verifying user document in auth-ms";

	public static final String ID_MATCH = "The ID matches the document";
	public static final String ID_DONT_MATCH = "ID does not match the provided document";

	AuthLogMessage() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
}
