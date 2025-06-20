package org.bbyoda.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Order(-1)
public class LoggingGlobalFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String requestId = UUID.randomUUID().toString();
        logger.info("Request ID: {}, Method: {}, Path: {}, Remote Address: {}",
                requestId,
                request.getMethod(),
                request.getPath(),
                request.getRemoteAddress());

        ServerHttpRequest modifiedRequest = request.mutate().header("X-Request-ID", requestId).build();
        ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();

        return chain.filter(modifiedExchange)
                .doOnSuccess(aVoid -> logger.info("Request ID: {} completed successfully", requestId))
                .doOnError((throwable -> logger.error("Request ID: {} failed with error: {}",
                        requestId, throwable.getMessage())));
    }
}
