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
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("loan_applications")
public class LoanApplicationEntity {

	@Id
	@Column("id_loan_application")
	private Long idLoanApplication;

	@Column("amount")
	private BigDecimal amount;

	@Column("term")
	private Integer term;

	@Column("email")
	private String email;

	@Column("id_status")
	private Long idStatus;

	@Column("id_loan_type")
	private Long idLoanType;

	@Column("created_at")
	private LocalDateTime createdAt;

	@Column("updated_at")
	private LocalDateTime updatedAt;
}
