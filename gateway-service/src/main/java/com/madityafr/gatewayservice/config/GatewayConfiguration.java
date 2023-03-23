package com.madityafr.gatewayservice.config;

import com.madityafr.gatewayservice.filter.AdminFilter;
import com.madityafr.gatewayservice.filter.AppFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {
    private final AppFilter filter;
    private final AdminFilter adminFilter;

    private final String endPoint8081 = "http://localhost:8081/";
    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/auth-service/**")
                        .uri("http://localhost:8081/"))
                .route(r -> r.path("/api/room-service/**")
                        .uri("http://localhost:8083/"))
                .route(r -> r.path("/api/reservation-service/**")
                        .uri("http://localhost:8084/"))
                .route(r -> r.path("/api/v1/reservation/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8084/"))
                .route(r -> r.path("/api/v1/rooms/**")
                        .and().method("POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter).filter(adminFilter))
                        .uri("http://localhost:8083/"))
                .route(r -> r.path("/api/v1/rooms/**")
                        .and().method("GET")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8083/"))
                .route(r -> r.path("/api/v1/time/**")
                        .and().method("DELETE")
                        .filters(f -> f.filter(filter).filter(adminFilter))
                        .uri("http://localhost:8083/"))
                .route(r -> r.path("/api/v1/time/**")
                        .and().method("POST", "PUT", "GET")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8083/"))
                .route(r -> r.path("/api/v1/type-room/**")
                        .and().method("POST", "PUT", "DELETE")
                        .filters(f -> f.filter(filter).filter(adminFilter))
                        .uri("http://localhost:8083/"))
                .route(r -> r.path("/api/v1/type-room/**")
                        .and().method("GET")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8083/"))
                .route(r -> r.path("/api/v1/auth/all-users")
                        .filters(f -> f.filter(filter).filter(adminFilter))
                        .uri("http://localhost:8081/"))
                .route(r -> r.path("/api/v1/auth/**")
                        .uri("http://localhost:8081/"))
                .build();
    }
}
