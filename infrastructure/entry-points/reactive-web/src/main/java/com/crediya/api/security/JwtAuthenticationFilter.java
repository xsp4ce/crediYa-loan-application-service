package com.crediya.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter implements WebFilter {

	private final Algorithm algorithm;
	private final String issuer;

	public JwtAuthenticationFilter(
	 @Value("${security.jwt.secret}") String secret,
	 @Value("${security.jwt.issuer}") String issuer
	) {
		this.algorithm = Algorithm.HMAC256(secret);
		this.issuer = issuer;
	}

	@Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
      String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
          return unauthorized(exchange);
      }

      String token = authHeader.substring(7);

      return validateToken(token).flatMap(decodedJWT -> {
          exchange.getAttributes().put("userId", decodedJWT.getSubject());
          exchange.getAttributes().put("userRole", decodedJWT.getClaim("role").asString());
          return chain.filter(exchange);
      }).onErrorResume(JWTVerificationException.class, e -> unauthorized(exchange));
  }

	private Mono<DecodedJWT> validateToken(String token) {
		return Mono.fromCallable(() -> {
			var verifier = JWT.require(algorithm).withIssuer(issuer).build();
			return verifier.verify(token);
		});
	}

	private Mono<Void> unauthorized(ServerWebExchange exchange) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		String body = "{\"code\":\"UNAUTHORIZED\",\"message\":\"Invalid or missing token\"}";
		DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));

		return response.writeWith(Mono.just(buffer));
	}
}
