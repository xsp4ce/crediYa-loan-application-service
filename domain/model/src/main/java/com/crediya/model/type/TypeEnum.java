package com.crediya.model.type;

public enum TypeEnum {
	PERSONAL("Personal Loan", 500.00, 20000.00, 0.25, true),
	MICRO("Micro Loan", 100.00, 2000.00, 0.35, true),
	BUSINESS("Business Loan", 1000.00, 50000.00, 0.1800, false);

	private final String name;
	private final double minAmount;
	private final double maxAmount;
	private final double interestRate;
	private final boolean automaticValidation;

	TypeEnum(String name, double minAmount, double maxAmount, double interestRate, boolean automaticValidation) {
		this.name = name;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.interestRate = interestRate;
		this.automaticValidation = automaticValidation;
	}

	public String getName() {
		return name;
	}

	public double getMinAmount() {
		return minAmount;
	}

	public double getMaxAmount() {
		return maxAmount;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public boolean isAutomaticValidation() {
		return automaticValidation;
	}
}
