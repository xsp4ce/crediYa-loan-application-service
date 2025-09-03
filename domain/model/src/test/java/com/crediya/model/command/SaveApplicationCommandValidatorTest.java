package com.crediya.model.command;


import com.crediya.model.constants.LogMessages;
import com.crediya.model.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

class SaveApplicationCommandValidatorTest {

	private SaveApplicationCommandValidator validator;

	@BeforeEach
	void setUp() {
		validator = new SaveApplicationCommandValidator();
	}

	@Test
	void shouldValidateSuccessfullyWithValidCommand() {
		SaveApplicationCommand validCommand = new SaveApplicationCommand("12345678", new BigDecimal("10000.00"), 24, 1L,
		 2L);

		Mono<Void> result = validator.validate(validCommand);

		StepVerifier.create(result).verifyComplete();
	}

	@Test
	void shouldFailWhenDocumentNumberIsNull() {
		SaveApplicationCommand command = new SaveApplicationCommand(null, new BigDecimal("10000.00"), 24, 1L, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.DOCUMENT_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenDocumentNumberIsBlank() {
		SaveApplicationCommand command = new SaveApplicationCommand("   ", new BigDecimal("10000.00"), 24, 1L, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.DOCUMENT_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenDocumentNumberIsInvalid() {
		SaveApplicationCommand command = new SaveApplicationCommand("123", new BigDecimal("10000.00"), 24, 1L, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.DOCUMENT_MIN_LENGTH)).verify();
	}

	@Test
	void shouldFailWhenAmountIsNull() {
		SaveApplicationCommand command = new SaveApplicationCommand("12345678", null, 24, 1L, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.INVALID_AMOUNT)).verify();
	}

	@Test
	void shouldFailWhenAmountIsZero() {
		SaveApplicationCommand command = new SaveApplicationCommand("12345678", BigDecimal.ZERO, 24, 1L, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.INVALID_AMOUNT)).verify();
	}

	@Test
	void shouldFailWhenAmountIsNegative() {
		SaveApplicationCommand command = new SaveApplicationCommand("12345678", new BigDecimal("-1000.00"), 24, 1L, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.INVALID_AMOUNT)).verify();
	}

	@Test
	void shouldFailWhenTermIsNull() {
		SaveApplicationCommand command = new SaveApplicationCommand("12345678", new BigDecimal("10000.00"), null, 1L, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.INVALID_TERM)).verify();
	}

	@Test
	void shouldFailWhenTermIsZero() {
		SaveApplicationCommand command = new SaveApplicationCommand("12345678", new BigDecimal("10000.00"), 0, 1L, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.INVALID_TERM)).verify();
	}

	@Test
	void shouldFailWhenTermIsNegative() {
		SaveApplicationCommand command = new SaveApplicationCommand("12345678", new BigDecimal("10000.00"), -12, 1L, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.INVALID_TERM)).verify();
	}

	@Test
	void shouldFailWhenIdLoanTypeIsNull() {
		SaveApplicationCommand command = new SaveApplicationCommand("12345678", new BigDecimal("10000.00"), 24, null, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.TYPE_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenIdLoanTypeIsZero() {
		SaveApplicationCommand command = new SaveApplicationCommand("12345678", new BigDecimal("10000.00"), 24, 0L, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.TYPE_REQUIRED)).verify();
	}

	@Test
	void shouldFailWhenIdLoanTypeIsNegative() {
		SaveApplicationCommand command = new SaveApplicationCommand("12345678", new BigDecimal("10000.00"), 24, -1L, 2L);

		Mono<Void> result = validator.validate(command);

		StepVerifier.create(result).expectErrorMatches(throwable -> throwable instanceof BusinessException &&
		 throwable.getMessage().equals(LogMessages.TYPE_REQUIRED)).verify();
	}
}
