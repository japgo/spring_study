package org.example.gateway.exception;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.core.Ordered;

@Component
public class GlobalErrorFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		return chain.filter(exchange).onErrorResume(ex -> handleError(exchange, ex));
	}

	private Mono<Void> handleError(ServerWebExchange exchange, Throwable ex) {
		// 여기서 오류를 처리하고 응답을 구성할 수 있습니다.
		return Mono.error(ex);
	}

	@Override
	public int getOrder() {
		// Filter 순서 지정
		return -1;
	}
}
