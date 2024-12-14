package com.pandey.microservices.currency_exchange_microservice.controller;

import com.pandey.microservices.currency_exchange_microservice.currService.CurrencyExchange;
import com.pandey.microservices.currency_exchange_microservice.repo.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ControllerExchange {

    @Autowired
    Environment env;
    @Autowired
    CurrencyExchangeRepository repository;
    //int a=0;

    private Logger logger= LoggerFactory.getLogger(ControllerExchange.class);


    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to){
//       CurrencyExchange currencyExchange=new CurrencyExchange(1000L,from,to, BigDecimal.valueOf(65));
//       String  port= env.getProperty("local.server.port");
//       currencyExchange.setEnvironment(port);
//       repository.save(currencyExchange);
        logger.info("logging from retrive exchange value from->{}, to->{}",from,to);
        CurrencyExchange currencyExchange=repository.findByFromAndTo(from,to);
        if(currencyExchange==null){
            throw new RuntimeException("unable to find value from :"+ from+" to:"+to);
        }
        String port= env.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        //System.out.println("this is the currenct numb: "+a++);

       return repository.findByFromAndTo(from,to);
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello form 8000";
    }
}
