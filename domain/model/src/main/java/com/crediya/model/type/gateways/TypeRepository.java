package com.crediya.model.type.gateways;

import com.crediya.model.type.Type;
import reactor.core.publisher.Mono;

public interface TypeRepository {
	Mono<Type> findById(Long id);
}
