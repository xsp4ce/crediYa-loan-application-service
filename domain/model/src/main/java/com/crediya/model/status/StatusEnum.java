package com.crediya.model.status;

public enum StatusEnum {
	PENDING(1L, "pending", "Application has been submitted and is awaiting review"),
	APPROVED(2L, "approved", "Application has been approved by the system or an agent"),
	REJECTED(3L, "rejected", "Application has been rejected"),
	CANCELLED(4L, "cancelled", "Application was cancelled by the applicant"),
	DISBURSED(5L, "disbursed", "Loan has been approved and funds have been disbursed");

	private final Long id;
	private final String name;
	private final String description;

	StatusEnum(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
