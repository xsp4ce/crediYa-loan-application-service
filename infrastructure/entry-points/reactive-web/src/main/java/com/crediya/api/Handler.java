package com.crediya.api;

import com.crediya.api.dto.SaveLoanRequestDTO;
import com.crediya.api.mapper.ILoanMapper;
import com.crediya.api.security.RequireRole;
import com.crediya.api.security.RoleConstants;
import com.crediya.model.constants.LogMessages;
import com.crediya.usecase.loan.LoanUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class Handler {
	private final LoanUseCase loanUseCase;
	private final ILoanMapper loanMapper;

	@RequireRole({RoleConstants.ADMINISTRATOR, RoleConstants.ADVISOR, RoleConstants.CUSTOMER})
	public Mono<ServerResponse> listenSaveLoanRequest(ServerRequest serverRequest) {
		String userId = serverRequest.exchange().getAttribute("userId");
		String userRole = serverRequest.exchange().getAttribute("userRole");
		log.info(LogMessages.AUTHENTICATED_USER, userId, userRole);
		log.info(LogMessages.STARTING_TO_PROCESS_REQUEST);
		return serverRequest
		 .bodyToMono(SaveLoanRequestDTO.class)
		 .map(dto -> dto.toBuilder().idUser(Long.parseLong(userId)).build())
		 .map(loanMapper::toCommand)
		 .flatMap(loanUseCase::save)
		 .flatMap(saved -> ServerResponse.status(HttpStatus.CREATED).build());
	}
}
