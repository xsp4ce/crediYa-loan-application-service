package com.crediya.model.loanapplication;

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
public class LoanApplication {
	private Long idLoanApplication;
	private BigDecimal amount;
	private Integer term;
	private String email;
	private Long idStatus;
	private Long idLoanType;
}
