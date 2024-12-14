package com.pandey.microservices.currency_exchange_microservice.repo;

import com.pandey.microservices.currency_exchange_microservice.currService.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long> {

    CurrencyExchange findByFromAndTo(String from,String to);

}
