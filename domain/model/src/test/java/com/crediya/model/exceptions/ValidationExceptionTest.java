package com.crediya.model.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

class ValidationExceptionTest {

	@Test
	void shouldCreateValidationExceptionWithMessage() {
		String errorMessage = "Validation failed";
		ValidationException exception = new ValidationException(errorMessage);

		assertEquals(errorMessage, exception.getMessage());
		assertInstanceOf(RuntimeException.class, exception);
	}

	@Test
	void shouldCreateValidationExceptionWithNullMessage() {
		ValidationException exception = new ValidationException(null);
		assertNull(exception.getMessage());
	}
}
