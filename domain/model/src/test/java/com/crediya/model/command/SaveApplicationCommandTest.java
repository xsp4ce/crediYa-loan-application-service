package com.crediya.model.command;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SaveApplicationCommandTest {

	@Test
	void shouldCreateSaveApplicationCommand() {
		BigDecimal amount = new BigDecimal("15000.00");
		SaveApplicationCommand command = new SaveApplicationCommand("12345678", amount, 24, 1L, 2L);

		assertEquals("12345678", command.documentNumber());
		assertEquals(amount, command.amount());
		assertEquals(24, command.term());
		assertEquals(1L, command.idLoanType());
	}

	@Test
	void shouldHandleNullValues() {
		SaveApplicationCommand command = new SaveApplicationCommand(null, null, null, null, null);

		assertNull(command.documentNumber());
		assertNull(command.amount());
		assertNull(command.term());
		assertNull(command.idLoanType());
	}

	@Test
	void shouldBeImmutable() {
		BigDecimal amount = new BigDecimal("20000.00");
		SaveApplicationCommand command = new SaveApplicationCommand("87654321", amount, 36, 2L, 2L);

		assertEquals("87654321", command.documentNumber());
		assertEquals(amount, command.amount());
		assertEquals(36, command.term());
		assertEquals(2L, command.idLoanType());
	}

	@Test
	void shouldImplementEqualsAndHashCode() {
		BigDecimal amount1 = new BigDecimal("10000.00");
		BigDecimal amount2 = new BigDecimal("10000.00");

		SaveApplicationCommand command1 = new SaveApplicationCommand("123", amount1, 12, 1L, 2L);
		SaveApplicationCommand command2 = new SaveApplicationCommand("123", amount2, 12, 1L, 2L);
		SaveApplicationCommand command3 = new SaveApplicationCommand("456", amount1, 12, 1L, 2L);

		assertEquals(command1, command2);
		assertNotEquals(command1, command3);
		assertEquals(command1.hashCode(), command2.hashCode());
	}
}
