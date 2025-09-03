package com.crediya.model.type;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TypeEnumTest {

	@Test
	void shouldHaveCorrectNumberOfTypes() {
		TypeEnum[] types = TypeEnum.values();
		assertEquals(3, types.length);
	}

	@Test
	void shouldHaveCorrectPersonalLoanProperties() {
		TypeEnum personal = TypeEnum.PERSONAL;

		assertEquals("Personal Loan", personal.getName());
		assertEquals(500.00, personal.getMinAmount(), 0.01);
		assertEquals(20000.00, personal.getMaxAmount(), 0.01);
		assertEquals(0.25, personal.getInterestRate(), 0.001);
		assertTrue(personal.isAutomaticValidation());
	}

	@Test
	void shouldHaveCorrectMicroLoanProperties() {
		TypeEnum micro = TypeEnum.MICRO;

		assertEquals("Micro Loan", micro.getName());
		assertEquals(100.00, micro.getMinAmount(), 0.01);
		assertEquals(2000.00, micro.getMaxAmount(), 0.01);
		assertEquals(0.35, micro.getInterestRate(), 0.001);
		assertTrue(micro.isAutomaticValidation());
	}

	@Test
	void shouldHaveCorrectBusinessLoanProperties() {
		TypeEnum business = TypeEnum.BUSINESS;

		assertEquals("Business Loan", business.getName());
		assertEquals(1000.00, business.getMinAmount(), 0.01);
		assertEquals(50000.00, business.getMaxAmount(), 0.01);
		assertEquals(0.1800, business.getInterestRate(), 0.001);
		assertFalse(business.isAutomaticValidation());
	}

	@Test
	void shouldReturnCorrectTypeFromValueOf() {
		assertEquals(TypeEnum.PERSONAL, TypeEnum.valueOf("PERSONAL"));
		assertEquals(TypeEnum.MICRO, TypeEnum.valueOf("MICRO"));
		assertEquals(TypeEnum.BUSINESS, TypeEnum.valueOf("BUSINESS"));
	}

	@Test
	void shouldThrowExceptionForInvalidTypeName() {
		assertThrows(IllegalArgumentException.class, () -> TypeEnum.valueOf("INVALID_TYPE"));
	}

	@Test
	void shouldHaveUniqueNames() {
		TypeEnum[] types = TypeEnum.values();

		for (int i = 0; i < types.length; i++) {
			for (int j = i + 1; j < types.length; j++) {
				assertNotEquals(types[i].getName(), types[j].getName(),
				 "Names should be unique: " + types[i].name() + " and " + types[j].name());
			}
		}
	}

	@Test
	void shouldHaveValidAmountRanges() {
		for (TypeEnum type : TypeEnum.values()) {
			assertTrue(type.getMinAmount() > 0, "Min amount should be positive for " + type.name());
			assertTrue(
			 type.getMaxAmount() > type.getMinAmount(), "Max amount should be greater than min amount for " + type.name());
		}
	}

	@Test
	void shouldHaveValidInterestRates() {
		for (TypeEnum type : TypeEnum.values()) {
			assertTrue(type.getInterestRate() > 0, "Interest rate should be positive for " + type.name());
			assertTrue(type.getInterestRate() <= 1.0, "Interest rate should not exceed 100% (1.0) for " + type.name());
		}
	}

	@Test
	void shouldHaveNonNullAndNonEmptyNames() {
		for (TypeEnum type : TypeEnum.values()) {
			assertNotNull(type.getName(), "Name should not be null for " + type.name());
			assertFalse(type.getName().trim().isEmpty(), "Name should not be empty for " + type.name());
		}
	}

	@Test
	void shouldHaveCorrectOrdinalValues() {
		assertEquals(0, TypeEnum.PERSONAL.ordinal());
		assertEquals(1, TypeEnum.MICRO.ordinal());
		assertEquals(2, TypeEnum.BUSINESS.ordinal());
	}

	@Test
	void shouldImplementToStringCorrectly() {
		assertEquals("PERSONAL", TypeEnum.PERSONAL.toString());
		assertEquals("MICRO", TypeEnum.MICRO.toString());
		assertEquals("BUSINESS", TypeEnum.BUSINESS.toString());
	}

	@Test
	void shouldHaveCorrectAutomaticValidationSettings() {
		assertTrue(TypeEnum.PERSONAL.isAutomaticValidation(), "Personal loans should have automatic validation");
		assertTrue(TypeEnum.MICRO.isAutomaticValidation(), "Micro loans should have automatic validation");
		assertFalse(TypeEnum.BUSINESS.isAutomaticValidation(), "Business loans should not have automatic validation");
	}

	@Test
	void shouldHaveLogicalAmountProgression() {
		assertTrue(TypeEnum.MICRO.getMaxAmount() <
		 TypeEnum.PERSONAL.getMaxAmount(), "Micro loan max should be less than Personal loan max");
		assertTrue(TypeEnum.PERSONAL.getMaxAmount() <
		 TypeEnum.BUSINESS.getMaxAmount(), "Personal loan max should be less than Business loan max");
	}

	@Test
	void shouldHaveLogicalInterestRateProgression() {
		assertTrue(TypeEnum.BUSINESS.getInterestRate() <
		 TypeEnum.PERSONAL.getInterestRate(), "Business loan interest should be less than Personal loan interest");
		assertTrue(TypeEnum.PERSONAL.getInterestRate() <
		 TypeEnum.MICRO.getInterestRate(), "Personal loan interest should be less than Micro loan interest");
	}
}