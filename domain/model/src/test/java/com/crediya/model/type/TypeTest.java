package com.crediya.model.type;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TypeTest {

	@Test
	void shouldCreateTypeWithAllFields() {
		BigDecimal minAmount = new BigDecimal("1000.00");
		BigDecimal maxAmount = new BigDecimal("50000.00");
		BigDecimal interestRate = new BigDecimal("5.5");

		Type type = Type.builder().idLoanType(1L).name("Personal Loan").minAmount(minAmount).maxAmount(maxAmount)
		 .interestRate(interestRate).automaticValidation(true).build();

		assertEquals(1L, type.getIdLoanType());
		assertEquals("Personal Loan", type.getName());
		assertEquals(minAmount, type.getMinAmount());
		assertEquals(maxAmount, type.getMaxAmount());
		assertEquals(interestRate, type.getInterestRate());
		assertTrue(type.getAutomaticValidation());
	}

	@Test
	void shouldCreateEmptyType() {
		Type type = new Type();
		assertNotNull(type);
		assertNull(type.getIdLoanType());
		assertNull(type.getName());
		assertNull(type.getMinAmount());
		assertNull(type.getMaxAmount());
		assertNull(type.getInterestRate());
		assertNull(type.getAutomaticValidation());
	}

	@Test
	void shouldCreateTypeWithConstructor() {
		BigDecimal minAmount = new BigDecimal("5000.00");
		BigDecimal maxAmount = new BigDecimal("100000.00");
		BigDecimal interestRate = new BigDecimal("3.2");

		Type type = new Type(2L, "Mortgage", minAmount, maxAmount, interestRate, false);

		assertEquals(2L, type.getIdLoanType());
		assertEquals("Mortgage", type.getName());
		assertEquals(minAmount, type.getMinAmount());
		assertEquals(maxAmount, type.getMaxAmount());
		assertEquals(interestRate, type.getInterestRate());
		assertFalse(type.getAutomaticValidation());
	}

	@Test
	void shouldModifyTypeFields() {
		Type type = new Type();
		BigDecimal newMinAmount = new BigDecimal("2000.00");
		BigDecimal newMaxAmount = new BigDecimal("75000.00");
		BigDecimal newInterestRate = new BigDecimal("4.8");

		type.setIdLoanType(3L);
		type.setName("Business Loan");
		type.setMinAmount(newMinAmount);
		type.setMaxAmount(newMaxAmount);
		type.setInterestRate(newInterestRate);
		type.setAutomaticValidation(true);

		assertEquals(3L, type.getIdLoanType());
		assertEquals("Business Loan", type.getName());
		assertEquals(newMinAmount, type.getMinAmount());
		assertEquals(newMaxAmount, type.getMaxAmount());
		assertEquals(newInterestRate, type.getInterestRate());
		assertTrue(type.getAutomaticValidation());
	}

	@Test
	void shouldCreateCopyWithToBuilder() {
		BigDecimal originalRate = new BigDecimal("6.0");
		Type original = Type.builder().idLoanType(1L).name("Auto Loan").minAmount(new BigDecimal("10000.00"))
		 .maxAmount(new BigDecimal("80000.00")).interestRate(originalRate).automaticValidation(false).build();

		Type copy = original.toBuilder().interestRate(new BigDecimal("5.5")).automaticValidation(true).build();

		assertEquals(original.getIdLoanType(), copy.getIdLoanType());
		assertEquals(original.getName(), copy.getName());
		assertEquals(original.getMinAmount(), copy.getMinAmount());
		assertEquals(original.getMaxAmount(), copy.getMaxAmount());
		assertEquals(new BigDecimal("5.5"), copy.getInterestRate());
		assertTrue(copy.getAutomaticValidation());
	}
}
