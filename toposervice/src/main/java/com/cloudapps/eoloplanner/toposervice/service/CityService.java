package com.cloudapps.eoloplanner.toposervice.service;

import java.time.Duration;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cloudapps.eoloplanner.toposervice.model.City;
import com.cloudapps.eoloplanner.toposervice.repository.CityReactiveRepository;

import reactor.core.publisher.Mono;

@Service
public class CityService {
	
	private final static int MIN_WAIT = 1;
	private final static int MAX_WAIT = 3;
	
	private Logger log = LoggerFactory.getLogger(CityService.class);
	
	private CityReactiveRepository cities;

    public CityService(CityReactiveRepository cities) {
        this.cities = cities;
    }
    
    public Mono<City> findById(String city) {
    		
    	int randomProcessTime = new Random().ints(MIN_WAIT, MAX_WAIT+1).findFirst().getAsInt();
    	log.info("Random process time for the query for city {} is {} second(s)", city, randomProcessTime);
        
        return this.cities.findById(city)
        	.delayElement(Duration.ofSeconds(randomProcessTime))
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

}
