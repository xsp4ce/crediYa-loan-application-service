package com.crediya.model.auth.gateways;

import reactor.core.publisher.Mono;

public interface AuthGateway {
	Mono<String> existsByDocument(String document, Long idUser);
}
