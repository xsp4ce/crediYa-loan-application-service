package com.crediya.model.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogMessagesTest {

	@Test
	void shouldHaveCorrectLogMessages() {
		assertEquals("Consuming path: /api/v1/request", LogMessages.CONSUMING_PATH_REQUEST);
		assertEquals("Starting to process the request to save a loan application",
		 LogMessages.STARTING_TO_PROCESS_REQUEST);
		assertEquals("Finding loan type by id: {}", LogMessages.FINDING_LOAN_TYPE);
		assertEquals("Saving loan application in the database", LogMessages.SAVING_APPLICATION_IN_DATABASE);
		assertEquals("Validating status pending exists in the database", LogMessages.VALIDATING_STATUS_IN_DATABASE);
	}

	@Test
	void shouldHaveCorrectErrorMessages() {
		assertEquals("Document required", LogMessages.DOCUMENT_REQUIRED);
		assertEquals("Invalid amount", LogMessages.INVALID_AMOUNT);
		assertEquals("Invalid term", LogMessages.INVALID_TERM);
		assertEquals("Loan type is required", LogMessages.TYPE_REQUIRED);
	}

	@Test
	void shouldNotAllowInstantiation() {
		assertThrows(UnsupportedOperationException.class, LogMessages::new);
	}
}
