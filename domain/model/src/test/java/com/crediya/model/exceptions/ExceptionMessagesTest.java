package com.crediya.model.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionMessagesTest {

	@Test
	void shouldHaveCorrectLogMessages() {
		assertEquals("VALIDATION_ERROR", ExceptionMessages.VALIDATION_ERROR);
		assertEquals("BUSINESS_ERROR", ExceptionMessages.BUSINESS_ERROR);
		assertEquals("Status 'PENDING' not found", ExceptionMessages.STATUS_PENDING_NOT_FOUND);
		assertEquals("Type not found with id: ", ExceptionMessages.TYPE_NOT_FOUND);
		assertEquals("User not found with document number: ", ExceptionMessages.DOCUMENT_NOT_FOUND);
		assertEquals("Field is already registered", ExceptionMessages.FIELD_ALREADY_REGISTERED);
	}

	@Test
	void shouldNotAllowInstantiation() {
		assertThrows(UnsupportedOperationException.class, ExceptionMessages::new);
	}
}
