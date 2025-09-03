package com.crediya.api.config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ConsumerConfig {
	@Bean
	public WebClient authClient(
	 @Value("${auth.base-url}") String baseUrl,
	 @Value("${auth.api-key}") String apiKey
	) {
		var http = HttpClient.create()
		 .doOnConnected(conn -> {
			 conn.addHandlerLast(new ReadTimeoutHandler(10));
		   conn.addHandlerLast(new WriteTimeoutHandler(10));
		 });

		return WebClient.builder()
		 .baseUrl(baseUrl)
		 .defaultHeader("X-Internal-Api-Key", apiKey)
		 .clientConnector(new ReactorClientHttpConnector(http))
		 .build();
	}
}
