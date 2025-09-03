package com.crediya.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("loan_types")
public class LoanTypeEntity {

	@Id
	@Column("id_loan_type")
	private Long idLoanType;

	@Column("name")
	private String name;

	@Column("min_amount")
	private BigDecimal minAmount;

	@Column("max_amount")
	private BigDecimal maxAmount;

	@Column("interest_rate")
	private BigDecimal interestRate;

	@Column("automatic_validation")
	private Boolean automaticValidation;
}
