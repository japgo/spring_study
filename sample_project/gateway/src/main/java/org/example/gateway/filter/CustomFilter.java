package org.example.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
	public CustomFilter() {
		super( Config.class );
	}

	@Override
	public GatewayFilter apply( Config config ) {
		return ( (( exchange, chain ) -> {
			ServerHttpRequest request = exchange.getRequest();
			log.info( "custom filter PRE : {}",request.getId() );
			return chain.filter( exchange );
		}));
	}

	public static class Config {

	}
}
