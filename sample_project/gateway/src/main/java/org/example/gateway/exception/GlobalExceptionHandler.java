package org.example.gateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Order( -1 )
@RequiredArgsConstructor
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
	private final ObjectMapper objectMapper;

	@Override
	public Mono< Void > handle( ServerWebExchange exchange, Throwable ex ) {
		ServerHttpResponse response = exchange.getResponse();

		return response.writeWith( Mono.fromSupplier( () -> {
			DataBufferFactory bufferFactory = response.bufferFactory();
			return bufferFactory.wrap( new byte[0] );
		} ) );
	}
}
