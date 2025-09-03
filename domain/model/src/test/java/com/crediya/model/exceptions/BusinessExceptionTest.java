package com.crediya.model.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

class BusinessExceptionTest {

	@Test
	void shouldCreateBusinessExceptionWithMessage() {
		String errorMessage = "Business rule violation";
		BusinessException exception = new BusinessException(errorMessage);

		assertEquals(errorMessage, exception.getMessage());
		assertInstanceOf(RuntimeException.class, exception);
	}

	@Test
	void shouldCreateBusinessExceptionWithNullMessage() {
		BusinessException exception = new BusinessException(null);
		assertNull(exception.getMessage());
	}
}
