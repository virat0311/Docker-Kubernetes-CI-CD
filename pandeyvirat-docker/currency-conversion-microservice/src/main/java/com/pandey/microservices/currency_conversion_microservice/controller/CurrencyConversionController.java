package com.pandey.microservices.currency_conversion_microservice.controller;

import com.pandey.microservices.currency_conversion_microservice.currencyconversionservice.CurrencyConversion;
import com.pandey.microservices.currency_conversion_microservice.currencyconversionservice.CurrencyExchangeProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {
    @Autowired
    private Environment env;

    @Autowired
    CurrencyExchangeProxy proxy;

    private Logger logger= LoggerFactory.getLogger(CurrencyConversionController.class);

//    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{num}")
//    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
//                                                          @PathVariable String to,
//                                                          @PathVariable BigDecimal num) {
//        HashMap<String,String> uriVariables=new HashMap<>();
//        uriVariables.put("from",from);
//        uriVariables.put("to",to);
//
//        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
//                CurrencyConversion.class, uriVariables);
//        CurrencyConversion currencyConversion=responseEntity.getBody();
//        return new CurrencyConversion(currencyConversion.getId(),from,to, currencyConversion.getConversionMultiple(),num,num.multiply(currencyConversion.getConversionMultiple()),currencyConversion.getEnvironment());
//    }

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{num}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,
                                                          @PathVariable String to,
                                                          @PathVariable BigDecimal num) {
        logger.info("logger of currency-conversion from->{},to->{}, quan->{} ",from,to,num);

        CurrencyConversion currencyConversion=proxy.retrieveExchange(from,to);
        return new CurrencyConversion(currencyConversion.getId(),from,to, currencyConversion.getConversionMultiple(),num,num.multiply(currencyConversion.getConversionMultiple()),currencyConversion.getEnvironment());
    }













    @GetMapping("/hello")
    public String hello(){
        return "this is from hello 8100";
    }
}
