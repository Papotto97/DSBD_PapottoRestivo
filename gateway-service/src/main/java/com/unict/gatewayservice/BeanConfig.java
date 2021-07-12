package com.unict.gatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/auction/**")
                        .filters(f -> f.rewritePath("/api/v1/auction/(?<remains>.*)", "/${remains}")
                                .addRequestHeader("X-first-Header", "auction-manager-header")
                                .hystrix(c -> c.setName("hystrix")
                                        .setFallbackUri("forward:/fallback/auction-manager")))
                        .uri("lb://AUCTION-MANAGER/")
                        .id("auction-manager"))
                
                .route(r -> r.path("/api/v1/saga/**")
                        .filters(f -> f.rewritePath("/api/v1/saga/(?<remains>.*)", "/${remains}")
                                .addRequestHeader("X-first-Header", "sagaorchestration-header")
                                .hystrix(c -> c.setName("hystrix")
                                        .setFallbackUri("forward:/fallback/sagaorchestration")))
                        .uri("lb://SAGAORCHESTRATION/")
                        .id("sagaorchestration"))
                
                .route(r -> r.path("/api/v1/wallet/**")
                        .filters(f -> f.rewritePath("/api/v1/wallet/(?<remains>.*)", "/${remains}")
                                .addRequestHeader("X-first-Header", "wallet-manager-header")
                                .hystrix(c -> c.setName("hystrix")
                                        .setFallbackUri("forward:/fallback/wallet-manager")))
                        .uri("lb://WALLET-MANAGER/")
                        .id("wallet-manager"))

                .build();
    }

}
