package com.crediya.model.status;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusEnumTest {

	@Test
	void shouldHaveCorrectNumberOfStatuses() {
		StatusEnum[] statuses = StatusEnum.values();
		assertEquals(5, statuses.length);
	}

	@Test
	void shouldHaveCorrectPendingStatusProperties() {
		StatusEnum pending = StatusEnum.PENDING;

		assertEquals(1L, pending.getId());
		assertEquals("pending", pending.getName());
		assertEquals("Application has been submitted and is awaiting review", pending.getDescription());
	}

	@Test
	void shouldHaveCorrectApprovedStatusProperties() {
		StatusEnum approved = StatusEnum.APPROVED;

		assertEquals(2L, approved.getId());
		assertEquals("approved", approved.getName());
		assertEquals("Application has been approved by the system or an agent", approved.getDescription());
	}

	@Test
	void shouldHaveCorrectRejectedStatusProperties() {
		StatusEnum rejected = StatusEnum.REJECTED;

		assertEquals(3L, rejected.getId());
		assertEquals("rejected", rejected.getName());
		assertEquals("Application has been rejected", rejected.getDescription());
	}

	@Test
	void shouldHaveCorrectCancelledStatusProperties() {
		StatusEnum cancelled = StatusEnum.CANCELLED;

		assertEquals(4L, cancelled.getId());
		assertEquals("cancelled", cancelled.getName());
		assertEquals("Application was cancelled by the applicant", cancelled.getDescription());
	}

	@Test
	void shouldHaveCorrectDisbursedStatusProperties() {
		StatusEnum disbursed = StatusEnum.DISBURSED;

		assertEquals(5L, disbursed.getId());
		assertEquals("disbursed", disbursed.getName());
		assertEquals("Loan has been approved and funds have been disbursed", disbursed.getDescription());
	}

	@Test
	void shouldReturnCorrectStatusFromValueOf() {
		assertEquals(StatusEnum.PENDING, StatusEnum.valueOf("PENDING"));
		assertEquals(StatusEnum.APPROVED, StatusEnum.valueOf("APPROVED"));
		assertEquals(StatusEnum.REJECTED, StatusEnum.valueOf("REJECTED"));
		assertEquals(StatusEnum.CANCELLED, StatusEnum.valueOf("CANCELLED"));
		assertEquals(StatusEnum.DISBURSED, StatusEnum.valueOf("DISBURSED"));
	}

	@Test
	void shouldThrowExceptionForInvalidStatusName() {
		assertThrows(IllegalArgumentException.class, () -> StatusEnum.valueOf("INVALID_STATUS"));
	}

	@Test
	void shouldHaveUniqueIds() {
		StatusEnum[] statuses = StatusEnum.values();

		for (int i = 0; i < statuses.length; i++) {
			for (int j = i + 1; j < statuses.length; j++) {
				assertNotEquals(statuses[i].getId(), statuses[j].getId(),
				 "IDs should be unique: " + statuses[i].name() + " and " + statuses[j].name());
			}
		}
	}

	@Test
	void shouldHaveUniqueNames() {
		StatusEnum[] statuses = StatusEnum.values();

		for (int i = 0; i < statuses.length; i++) {
			for (int j = i + 1; j < statuses.length; j++) {
				assertNotEquals(statuses[i].getName(), statuses[j].getName(),
				 "Names should be unique: " + statuses[i].name() + " and " + statuses[j].name());
			}
		}
	}

	@Test
	void shouldHaveNonNullProperties() {
		for (StatusEnum status : StatusEnum.values()) {
			assertNotNull(status.getId(), "ID should not be null for " + status.name());
			assertNotNull(status.getName(), "Name should not be null for " + status.name());
			assertNotNull(status.getDescription(), "Description should not be null for " + status.name());
			assertFalse(status.getName().trim().isEmpty(), "Name should not be empty for " + status.name());
			assertFalse(status.getDescription().trim().isEmpty(), "Description should not be empty for " + status.name());
		}
	}

	@Test
	void shouldHaveCorrectOrdinalValues() {
		assertEquals(0, StatusEnum.PENDING.ordinal());
		assertEquals(1, StatusEnum.APPROVED.ordinal());
		assertEquals(2, StatusEnum.REJECTED.ordinal());
		assertEquals(3, StatusEnum.CANCELLED.ordinal());
		assertEquals(4, StatusEnum.DISBURSED.ordinal());
	}

	@Test
	void shouldImplementToStringCorrectly() {
		assertEquals("PENDING", StatusEnum.PENDING.toString());
		assertEquals("APPROVED", StatusEnum.APPROVED.toString());
		assertEquals("REJECTED", StatusEnum.REJECTED.toString());
		assertEquals("CANCELLED", StatusEnum.CANCELLED.toString());
		assertEquals("DISBURSED", StatusEnum.DISBURSED.toString());
	}
}
