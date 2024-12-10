package ru.otus.hw.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@EnableConfigurationProperties(PropertiesConfiguration.class)
public class RedirectConfiguration {

    @Bean
    RouteLocator gateway(RouteLocatorBuilder builder, PropertiesConfiguration properties) {
        var routesBuilder = builder.routes();
        for (var route : properties.getApiRoutes()) {
            routesBuilder.route(route.getId(), routeSpec ->
                    routeSpec
                            .path(String.format("/%s/**", route.getFrom()))
                            .uri(String.format("%s/@", route.getTo()))
            );
        }
        return routesBuilder.build();
    }
}
