package com.example.gateway.gatewayfilter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * Pre-filter
 */
@Component
public class PreProxyGatewayFilterFactory extends AbstractGatewayFilterFactory<PreProxyGatewayFilterFactory.Config> {
    public PreProxyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            //If you want to build a "pre" filter you need to manipulate the
            //request before calling chain.filter
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .headers(httpHeaders -> httpHeaders.set("X-Pre-Proxy", "CloudGateway"))
                    .build();

            //use builder to manipulate the request
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static final class Config {

    }
}
