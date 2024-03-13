package org.example.gateway.filter;

import com.fasterxml.jackson.databind.JsonSerializer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
	public GlobalFilter() {
		super( Config.class );
	}

	@Data
	public static class Config {
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}

	@Override
	public GatewayFilter apply( Config config ) {
		log.info( "apply function enter" );

		return new OrderedGatewayFilter( ( exchange, chain ) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode( HttpStatusCode.valueOf( 200 ) );
			log.info("Global Filter Start: request id -> {}", request.getId());

			DataBufferFactory bufferFactory = response.bufferFactory();
			ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator( response ) {
				@Override
				public Mono<Void> writeWith( Publisher<? extends DataBuffer > body ) {
					if( body instanceof Flux< ? extends DataBuffer > fluxBody ) {
						return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
							DefaultDataBuffer joinedBuffers = new DefaultDataBufferFactory().join(dataBuffers);

							byte[] content = new byte[joinedBuffers.readableByteCount()];

							joinedBuffers.read(content);

							//
							String responseBody = new String(content, StandardCharsets.UTF_8);
							try {
                                	/*
                                    ///////////////////////////////////////////////////
                                    // (byte[] type) content 원하는 양식으로 변경 후 재주입
                                    ///////////////////////////////////////////////////
                                    */
								return bufferFactory.wrap(content);
							} catch (Exception e) {
								// data 수정 없이 return
								return joinedBuffers;
							}
						}));
					}

					return super.writeWith( body );
				}
			};

			return chain.filter( exchange.mutate().response( responseDecorator ).build() );

		}, Ordered.HIGHEST_PRECEDENCE);
	}
}