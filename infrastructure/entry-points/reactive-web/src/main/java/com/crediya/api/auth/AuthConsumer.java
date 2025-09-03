package com.crediya.api.auth;

import com.crediya.model.auth.gateways.AuthGateway;
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
	public Mono<Boolean> existsByDocument(String document) {
		log.info(AuthLogMessage.CONSUMING_PATH_AUTH_LOGIN);
		log.info(AuthLogMessage.VERIFYING_USER_DOCUMENT);

		return authClient.post().uri("/api/v1/document/{documentNumber}", document)
		 .exchangeToMono(resp -> {
			 if (resp.statusCode().equals(HttpStatus.OK)) {
				 log.info(AuthLogMessage.DOCUMENT_NOT_FOUND);
				 return Mono.just(false);
			 }
			 if (resp.statusCode().equals(HttpStatus.CONFLICT)) {
				 log.info(AuthLogMessage.DOCUMENT_ALREADY_EXISTS);
				 return Mono.just(true);
			 }
			 return resp.createException().flatMap(Mono::error);
		 });
	}
}
