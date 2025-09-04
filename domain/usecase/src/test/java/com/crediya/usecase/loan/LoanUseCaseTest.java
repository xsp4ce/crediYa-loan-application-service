package com.crediya.usecase.loan;

import com.crediya.model.auth.gateways.AuthGateway;
import com.crediya.model.command.saveapplication.SaveApplicationCommand;
import com.crediya.model.command.saveapplication.SaveApplicationCommandValidator;
import com.crediya.model.exceptions.ExceptionMessages;
import com.crediya.model.exceptions.ValidationException;
import com.crediya.model.loanapplication.LoanApplication;
import com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.model.status.StatusEnum;
import com.crediya.model.type.Type;
import com.crediya.model.type.gateways.TypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanUseCaseTest {

	@Mock
	private LoanApplicationRepository loanApplicationRepository;

	@Mock
	private TypeRepository typeRepository;

	@Mock
	private AuthGateway authGateway;

	@Mock
	private SaveApplicationCommandValidator saveApplicationCommandValidator;

	private LoanUseCase loanUseCase;
	private SaveApplicationCommand validCommand;
	private Type validType;
	private LoanApplication expectedLoanApplication;

	@BeforeEach
	void setUp() {
		loanUseCase =
		 new LoanUseCase(loanApplicationRepository, typeRepository, authGateway, saveApplicationCommandValidator);

		validCommand = new SaveApplicationCommand("12345678", new BigDecimal("10000.00"), 24, 1L, 2L);

		validType = Type.builder().idLoanType(1L).name("Personal Loan").minAmount(new BigDecimal("1000.00"))
		 .maxAmount(new BigDecimal("50000.00")).interestRate(new BigDecimal("0.15")).automaticValidation(true).build();

		expectedLoanApplication =
		 LoanApplication.builder().amount(validCommand.amount()).term(validCommand.term()).email("test@example.com")
			.idStatus(StatusEnum.PENDING.getId()).idLoanType(validCommand.idLoanType()).build();
	}

	@Test
	void shouldSaveLoanApplicationSuccessfully() {
		when(saveApplicationCommandValidator.validate(validCommand)).thenReturn(Mono.empty());
		when(authGateway.existsByDocument("12345678", 2L)).thenReturn(Mono.just("test@example.com"));
		when(typeRepository.findById(1L)).thenReturn(Mono.just(validType));
		when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.just(expectedLoanApplication));

		StepVerifier.create(loanUseCase.save(validCommand)).expectNext(expectedLoanApplication).verifyComplete();

		verify(saveApplicationCommandValidator).validate(validCommand);
		verify(authGateway).existsByDocument("12345678", 2L);
		verify(typeRepository).findById(1L);
		verify(loanApplicationRepository).save(any(LoanApplication.class));
	}

	@Test
	void shouldFailWhenCommandValidationFails() {
		ValidationException validationError = new ValidationException("Invalid command");
		when(saveApplicationCommandValidator.validate(validCommand)).thenReturn(Mono.error(validationError));

		StepVerifier.create(loanUseCase.save(validCommand)).expectError(ValidationException.class).verify();

		verify(saveApplicationCommandValidator).validate(validCommand);
		verifyNoInteractions(authGateway, typeRepository, loanApplicationRepository);
	}

	@Test
	void shouldFailWhenAuthGatewayThrowsValidationException() {
		ValidationException validationError = new ValidationException(ExceptionMessages.LOAN_RESTRICTED);
		when(saveApplicationCommandValidator.validate(validCommand)).thenReturn(Mono.empty());
		when(authGateway.existsByDocument("12345678", 2L)).thenReturn(Mono.error(validationError));

		StepVerifier.create(loanUseCase.save(validCommand))
		 .expectErrorMatches(throwable -> throwable instanceof ValidationException &&
			throwable.getMessage().equals(ExceptionMessages.LOAN_RESTRICTED)).verify();

		verify(saveApplicationCommandValidator).validate(validCommand);
		verify(authGateway).existsByDocument("12345678", 2L);
		verifyNoInteractions(typeRepository, loanApplicationRepository);
	}

	@Test
	void shouldFailWhenLoanTypeDoesNotExist() {
		when(saveApplicationCommandValidator.validate(validCommand)).thenReturn(Mono.empty());
		when(authGateway.existsByDocument("12345678", 2L)).thenReturn(Mono.just("test@example.com"));
		when(typeRepository.findById(1L)).thenReturn(Mono.empty());

		StepVerifier.create(loanUseCase.save(validCommand))
		 .expectErrorMatches(throwable -> throwable instanceof ValidationException &&
			throwable.getMessage().equals(ExceptionMessages.TYPE_NOT_FOUND + "1")).verify();

		verify(saveApplicationCommandValidator).validate(validCommand);
		verify(authGateway).existsByDocument("12345678", 2L);
		verify(typeRepository).findById(1L);
		verifyNoInteractions(loanApplicationRepository);
	}

	@Test
	void shouldFailWhenTypeRepositoryThrowsError() {
		RuntimeException repositoryError = new RuntimeException("Database error");
		when(saveApplicationCommandValidator.validate(validCommand)).thenReturn(Mono.empty());
		when(authGateway.existsByDocument("12345678", 2L)).thenReturn(Mono.just("test@example.com"));
		when(typeRepository.findById(1L)).thenReturn(Mono.error(repositoryError));

		StepVerifier.create(loanUseCase.save(validCommand)).expectError(RuntimeException.class).verify();

		verify(saveApplicationCommandValidator).validate(validCommand);
		verify(authGateway).existsByDocument("12345678", 2L);
		verify(typeRepository).findById(1L);
		verifyNoInteractions(loanApplicationRepository);
	}

	@Test
	void shouldFailWhenAuthGatewayThrowsError() {
		RuntimeException authError = new RuntimeException("Auth service error");
		when(saveApplicationCommandValidator.validate(validCommand)).thenReturn(Mono.empty());
		when(authGateway.existsByDocument("12345678", 2L)).thenReturn(Mono.error(authError));

		StepVerifier.create(loanUseCase.save(validCommand)).expectError(RuntimeException.class).verify();

		verify(saveApplicationCommandValidator).validate(validCommand);
		verify(authGateway).existsByDocument("12345678", 2L);
		verifyNoInteractions(typeRepository, loanApplicationRepository);
	}

	@Test
	void shouldFailWhenLoanApplicationRepositoryThrowsError() {
		RuntimeException saveError = new RuntimeException("Save error");
		when(saveApplicationCommandValidator.validate(validCommand)).thenReturn(Mono.empty());
		when(authGateway.existsByDocument("12345678", 2L)).thenReturn(Mono.just("test@example.com"));
		when(typeRepository.findById(1L)).thenReturn(Mono.just(validType));
		when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.error(saveError));

		StepVerifier.create(loanUseCase.save(validCommand)).expectError(RuntimeException.class).verify();

		verify(saveApplicationCommandValidator).validate(validCommand);
		verify(authGateway).existsByDocument("12345678", 2L);
		verify(typeRepository).findById(1L);
		verify(loanApplicationRepository).save(any(LoanApplication.class));
	}

	@Test
	void shouldCreateLoanApplicationWithCorrectStatus() {
		when(saveApplicationCommandValidator.validate(validCommand)).thenReturn(Mono.empty());
		when(authGateway.existsByDocument("12345678", 2L)).thenReturn(Mono.just("test@example.com"));
		when(typeRepository.findById(1L)).thenReturn(Mono.just(validType));
		when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.just(expectedLoanApplication));

		StepVerifier.create(loanUseCase.save(validCommand)).expectNext(expectedLoanApplication).verifyComplete();

		verify(loanApplicationRepository).save(any(LoanApplication.class));
	}

	@Test
	void shouldHandleDifferentLoanTypes() {
		SaveApplicationCommand businessLoanCommand =
		 new SaveApplicationCommand("87654321", new BigDecimal("50000.00"), 36, 2L, 2L);

		Type businessType = Type.builder().idLoanType(2L).name("Business Loan").minAmount(new BigDecimal("5000.00"))
		 .maxAmount(new BigDecimal("100000.00")).interestRate(new BigDecimal("0.18")).automaticValidation(false).build();

		LoanApplication businessLoanApplication =
		 LoanApplication.builder().amount(businessLoanCommand.amount()).term(businessLoanCommand.term())
			.email("business@example.com").idStatus(StatusEnum.PENDING.getId()).idLoanType(businessLoanCommand.idLoanType())
			.build();

		when(saveApplicationCommandValidator.validate(businessLoanCommand)).thenReturn(Mono.empty());
		when(authGateway.existsByDocument("87654321", 2L)).thenReturn(Mono.just("business@example.com"));
		when(typeRepository.findById(2L)).thenReturn(Mono.just(businessType));
		when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.just(businessLoanApplication));

		StepVerifier.create(loanUseCase.save(businessLoanCommand)).expectNext(businessLoanApplication).verifyComplete();

		verify(saveApplicationCommandValidator).validate(businessLoanCommand);
		verify(authGateway).existsByDocument("87654321", 2L);
		verify(typeRepository).findById(2L);
		verify(loanApplicationRepository).save(any(LoanApplication.class));
	}

	@Test
	void shouldHandleMultipleDocumentNumbers() {
		SaveApplicationCommand secondCommand =
		 new SaveApplicationCommand("99999999", new BigDecimal("5000.00"), 12, 1L, 2L);

		when(saveApplicationCommandValidator.validate(secondCommand)).thenReturn(Mono.empty());
		when(authGateway.existsByDocument("99999999", 2L)).thenReturn(Mono.just("test@example.com"));
		when(typeRepository.findById(1L)).thenReturn(Mono.just(validType));
		when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.just(expectedLoanApplication));

		StepVerifier.create(loanUseCase.save(secondCommand)).expectNext(expectedLoanApplication).verifyComplete();

		verify(authGateway).existsByDocument("99999999", 2L);
	}

	@Test
	void shouldValidateExecutionOrder() {
		when(saveApplicationCommandValidator.validate(validCommand)).thenReturn(Mono.empty());
		when(authGateway.existsByDocument(anyString(), anyLong())).thenReturn(Mono.just("test@example.com"));
		when(typeRepository.findById(anyLong())).thenReturn(Mono.just(validType));
		when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.just(expectedLoanApplication));

		StepVerifier.create(loanUseCase.save(validCommand)).expectNext(expectedLoanApplication).verifyComplete();

		var inOrder =
		 org.mockito.Mockito.inOrder(saveApplicationCommandValidator, authGateway, typeRepository,
			loanApplicationRepository);

		inOrder.verify(saveApplicationCommandValidator).validate(validCommand);
		inOrder.verify(authGateway).existsByDocument("12345678", 2L);
		inOrder.verify(typeRepository).findById(1L);
		inOrder.verify(loanApplicationRepository).save(any(LoanApplication.class));
	}

	@Test
	void shouldSaveLoanApplicationWithCorrectEmail() {
		String expectedEmail = "user@domain.com";
		when(saveApplicationCommandValidator.validate(validCommand)).thenReturn(Mono.empty());
		when(authGateway.existsByDocument("12345678", 2L)).thenReturn(Mono.just(expectedEmail));
		when(typeRepository.findById(1L)).thenReturn(Mono.just(validType));

		LoanApplication expectedWithEmail =
		 LoanApplication.builder().amount(validCommand.amount()).term(validCommand.term()).email(expectedEmail)
			.idStatus(StatusEnum.PENDING.getId()).idLoanType(validCommand.idLoanType()).build();

		when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(Mono.just(expectedWithEmail));

		StepVerifier.create(loanUseCase.save(validCommand)).expectNext(expectedWithEmail).verifyComplete();

		verify(loanApplicationRepository).save(any(LoanApplication.class));
	}
}