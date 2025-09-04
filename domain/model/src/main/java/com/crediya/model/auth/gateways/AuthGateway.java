package com.crediya.model.auth.gateways;

import reactor.core.publisher.Mono;

public interface AuthGateway {
	Mono<Boolean> existsByDocument(String document, Long idUser);
}
