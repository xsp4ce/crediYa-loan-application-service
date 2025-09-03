package com.crediya.model.loanapplication;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class LoanApplicationTest {

	@Test
	void shouldCreateLoanApplicationWithAllFields() {
		BigDecimal amount = new BigDecimal("10000.00");
		LoanApplication loanApplication =
		 LoanApplication.builder().idLoanApplication(1L).amount(amount).term(12).email("test@example.com").idStatus(1L)
			.idLoanType(1L).build();

		assertEquals(1L, loanApplication.getIdLoanApplication());
		assertEquals(amount, loanApplication.getAmount());
		assertEquals(12, loanApplication.getTerm());
		assertEquals("test@example.com", loanApplication.getEmail());
		assertEquals(1L, loanApplication.getIdStatus());
		assertEquals(1L, loanApplication.getIdLoanType());
	}

	@Test
	void shouldCreateEmptyLoanApplication() {
		LoanApplication loanApplication = new LoanApplication();
		assertNotNull(loanApplication);
		assertNull(loanApplication.getIdLoanApplication());
		assertNull(loanApplication.getAmount());
		assertNull(loanApplication.getTerm());
		assertNull(loanApplication.getEmail());
		assertNull(loanApplication.getIdStatus());
		assertNull(loanApplication.getIdLoanType());
	}

	@Test
	void shouldCreateLoanApplicationWithConstructor() {
		BigDecimal amount = new BigDecimal("5000.00");
		LoanApplication loanApplication = new LoanApplication(1L, amount, 24, "user@test.com", 2L, 3L);

		assertEquals(1L, loanApplication.getIdLoanApplication());
		assertEquals(amount, loanApplication.getAmount());
		assertEquals(24, loanApplication.getTerm());
		assertEquals("user@test.com", loanApplication.getEmail());
		assertEquals(2L, loanApplication.getIdStatus());
		assertEquals(3L, loanApplication.getIdLoanType());
	}

	@Test
	void shouldModifyLoanApplicationFields() {
		LoanApplication loanApplication = new LoanApplication();
		BigDecimal newAmount = new BigDecimal("15000.00");

		loanApplication.setIdLoanApplication(5L);
		loanApplication.setAmount(newAmount);
		loanApplication.setTerm(36);
		loanApplication.setEmail("modified@test.com");
		loanApplication.setIdStatus(3L);
		loanApplication.setIdLoanType(2L);

		assertEquals(5L, loanApplication.getIdLoanApplication());
		assertEquals(newAmount, loanApplication.getAmount());
		assertEquals(36, loanApplication.getTerm());
		assertEquals("modified@test.com", loanApplication.getEmail());
		assertEquals(3L, loanApplication.getIdStatus());
		assertEquals(2L, loanApplication.getIdLoanType());
	}

	@Test
	void shouldCreateCopyWithToBuilder() {
		BigDecimal amount = new BigDecimal("7500.00");
		LoanApplication original =
		 LoanApplication.builder().idLoanApplication(1L).amount(amount).term(18).email("original@test.com").idStatus(1L)
			.idLoanType(1L).build();

		LoanApplication copy = original.toBuilder().amount(new BigDecimal("8000.00")).email("copy@test.com").build();

		assertEquals(original.getIdLoanApplication(), copy.getIdLoanApplication());
		assertEquals(new BigDecimal("8000.00"), copy.getAmount());
		assertEquals(original.getTerm(), copy.getTerm());
		assertEquals("copy@test.com", copy.getEmail());
		assertEquals(original.getIdStatus(), copy.getIdStatus());
		assertEquals(original.getIdLoanType(), copy.getIdLoanType());
	}
}
