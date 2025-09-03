package com.crediya.api.dto;

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
public class SaveLoanRequestDTO {
	private String documentNumber;
	private BigDecimal amount;
	private Integer term;
	private Long idLoanType;
	private Long idUser;
}
