package com.crediya.api.auth;

import com.crediya.model.auth.gateways.AuthGateway;
import com.crediya.model.exceptions.ExceptionMessages;
import com.crediya.model.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class AuthConsumer implements AuthGateway {
	private final WebClient authClient;

	@Override
	public Mono<String> existsByDocument(String document, Long idUser) {
		log.info(AuthLogMessage.CONSUMING_PATH_AUTH_LOGIN);
		log.info(AuthLogMessage.VERIFYING_USER_DOCUMENT);

		var request = new ValidateDocumentRequest(document, idUser);

		return authClient.post().uri("/api/v1/document").bodyValue(request).exchangeToMono(resp -> {
			if (resp.statusCode().equals(HttpStatus.OK)) {
				log.info(AuthLogMessage.ID_MATCH);
				return resp.bodyToMono(ValidateDocumentResponseDTO.class).map(ValidateDocumentResponseDTO::getEmail);
			}
			if (resp.statusCode().equals(HttpStatus.CONFLICT)) {
				log.info(AuthLogMessage.ID_DONT_MATCH);
				return Mono.error(new ValidationException(ExceptionMessages.LOAN_RESTRICTED));
			}
			return resp.createException().flatMap(Mono::error);
		});
	}
}
