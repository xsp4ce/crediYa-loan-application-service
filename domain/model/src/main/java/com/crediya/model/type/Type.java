package com.crediya.model.type;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Type {
	private Long idLoanType;
	private String name;
	private BigDecimal minAmount;
	private BigDecimal maxAmount;
	private BigDecimal interestRate;
	private Boolean automaticValidation;
}
