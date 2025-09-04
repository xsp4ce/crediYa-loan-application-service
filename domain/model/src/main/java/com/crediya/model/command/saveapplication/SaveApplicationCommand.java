package com.crediya.model.command.saveapplication;

import java.math.BigDecimal;

public record SaveApplicationCommand(String documentNumber, BigDecimal amount, Integer term, Long idLoanType,
																		 Long idUser) {
}
