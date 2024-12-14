package com.pandey.microservices.currency_exchange_microservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger log= LoggerFactory.getLogger(CircuitBreakerController.class);


    @GetMapping("/currency-exchange/sample-circ")
    @Retry(name = "sample-reName",fallbackMethod = "hardcodedResponse")
    @CircuitBreaker(name = "sample-reName",fallbackMethod = "hardcodedResponse")
    @RateLimiter(name = "sample-reName")
    public String  sampleApi(){
//        log.info("sample api call receive->{}",CircuitBreakerController.class);
        log.info("sample api call received");
        ResponseEntity<String> forEntity=new RestTemplate().getForEntity("http://localhost:8081/dummy-api",String.class);
        return forEntity.getBody();
    }
    public String hardcodedResponse( Exception exception){
        return "problem in fetching the response from dummy-api";
    }

}
