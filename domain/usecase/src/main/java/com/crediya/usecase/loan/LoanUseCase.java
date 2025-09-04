package com.crediya.usecase.loan;

import com.crediya.model.auth.gateways.AuthGateway;
import com.crediya.model.command.saveapplication.SaveApplicationCommand;
import com.crediya.model.command.saveapplication.SaveApplicationCommandValidator;
import com.crediya.model.exceptions.ValidationException;
import com.crediya.model.exceptions.ExceptionMessages;
import com.crediya.model.loanapplication.LoanApplication;
import com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.model.status.StatusEnum;
import com.crediya.model.type.gateways.TypeRepository;
import reactor.core.publisher.Mono;

public record LoanUseCase(LoanApplicationRepository loanApplicationRepository, TypeRepository typeRepository,
													AuthGateway authGateway, SaveApplicationCommandValidator saveApplicationCommandValidator) {

	public Mono<LoanApplication> save(SaveApplicationCommand cmd) {
		return saveApplicationCommandValidator.validate(cmd)
		 .then(Mono.defer(() -> validateDocumentNumberExists(cmd.documentNumber(), cmd.idUser())))
		 .flatMap(email -> validateLoanTypeExists(cmd.idLoanType())
		 .then(Mono.defer(() -> saveLoanApplication(cmd, email))));
	}

	private Mono<Long> validateLoanTypeExists(Long typeId) {
		return typeRepository.findById(typeId)
		 .switchIfEmpty(Mono.error(new ValidationException(ExceptionMessages.TYPE_NOT_FOUND + typeId)))
		 .thenReturn(typeId);
	}

	private Mono<String> validateDocumentNumberExists(String documentNumber, Long idUser) {
		return authGateway.existsByDocument(documentNumber, idUser);
	}

	private Mono<LoanApplication> saveLoanApplication(SaveApplicationCommand cmd, String email) {
		var entity = LoanApplication.builder()
		 .amount(cmd.amount())
		 .term(cmd.term())
		 .email(email)
		 .idStatus(StatusEnum.PENDING.getId())
		 .idLoanType(cmd.idLoanType())
		 .build();
		return loanApplicationRepository.save(entity);
	}
}
