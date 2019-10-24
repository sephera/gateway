package com.example.gateway.gatewayfilter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Post-filter
 */
@Component
public class PostProxyGatewayFilterFactory extends AbstractGatewayFilterFactory<PostProxyGatewayFilterFactory.Config> {
    public PostProxyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(PostProxyGatewayFilterFactory.Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                //Manipulate the response in some way
                response.getHeaders().add("X-Post-Proxy", "Gateway");
            }));
        };
    }

    public static final class Config {

    }
}
