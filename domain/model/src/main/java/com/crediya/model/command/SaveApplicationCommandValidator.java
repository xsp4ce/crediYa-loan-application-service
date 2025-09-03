package com.crediya.model.command;

import com.crediya.model.constants.LogMessages;
import com.crediya.model.exceptions.BusinessException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class SaveApplicationCommandValidator {
	public Mono<Void> validate(SaveApplicationCommand c) {
		if (c.documentNumber() == null || c.documentNumber().isBlank())
			return Mono.error(new BusinessException(LogMessages.DOCUMENT_REQUIRED));
		if (c.documentNumber().length() != 8)
			return Mono.error(new BusinessException(LogMessages.DOCUMENT_MIN_LENGTH));
		if (c.amount() == null || c.amount().compareTo(BigDecimal.ZERO) <= 0)
			return Mono.error(new BusinessException(LogMessages.INVALID_AMOUNT));
		if (c.term() == null || c.term() <= 0)
			return Mono.error(new BusinessException(LogMessages.INVALID_TERM));
		if (c.idLoanType() == null || c.idLoanType() <= 0)
			return Mono.error(new BusinessException(LogMessages.TYPE_REQUIRED));
		return Mono.empty();
	}
}
