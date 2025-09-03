package com.crediya.model.status;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class StatusTest {

	@Test
	void shouldCreateStatusWithAllFields() {
		Status status = Status.builder().idStatus(1L).name("PENDING").description("Application is pending review").build();

		assertEquals(1L, status.getIdStatus());
		assertEquals("PENDING", status.getName());
		assertEquals("Application is pending review", status.getDescription());
	}

	@Test
	void shouldCreateEmptyStatus() {
		Status status = new Status();
		assertNotNull(status);
		assertNull(status.getIdStatus());
		assertNull(status.getName());
		assertNull(status.getDescription());
	}

	@Test
	void shouldCreateStatusWithConstructor() {
		Status status = new Status(2L, "APPROVED", "Application has been approved");

		assertEquals(2L, status.getIdStatus());
		assertEquals("APPROVED", status.getName());
		assertEquals("Application has been approved", status.getDescription());
	}

	@Test
	void shouldModifyStatusFields() {
		Status status = new Status();

		status.setIdStatus(3L);
		status.setName("REJECTED");
		status.setDescription("Application has been rejected");

		assertEquals(3L, status.getIdStatus());
		assertEquals("REJECTED", status.getName());
		assertEquals("Application has been rejected", status.getDescription());
	}

	@Test
	void shouldCreateCopyWithToBuilder() {
		Status original = Status.builder().idStatus(1L).name("PENDING").description("Original description").build();

		Status copy = original.toBuilder().description("Modified description").build();

		assertEquals(original.getIdStatus(), copy.getIdStatus());
		assertEquals(original.getName(), copy.getName());
		assertEquals("Modified description", copy.getDescription());
	}
}
