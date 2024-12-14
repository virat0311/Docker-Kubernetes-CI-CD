package com.pandey.microservices.api_gateway.configuration;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class config {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        //you can change the route and decide what apis should go and how
        return builder.routes()
                .route(p->p.path("/get")
                   .filters(f->f.addRequestHeader("MyHeader","MyURI")
                        .addRequestParameter("MyParam","MyValue")
                   )
                .uri("http://httpbin.org:80"))
                .route(p->p.path("/currency-exchange/**") // here now we have given the name of service
                        // connected to eureka server so if any new req comes through api gateway
                        // for this service it will be rerouted to that servie name
                        // using eireka server and it will automatic load balance
                        .uri("lb://currency-exchange")
                )
                .route(p->p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion")
                )
                .build();
    }

}
