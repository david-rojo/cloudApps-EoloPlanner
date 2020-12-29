package com.cloudapps.eoloplanner.toposervice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cloudapps.eoloplanner.toposervice.model.City;
import com.cloudapps.eoloplanner.toposervice.repository.CityReactiveRepository;

import reactor.core.publisher.Mono;

@Service
public class CityService {
	
	private CityReactiveRepository cities;

    public CityService(CityReactiveRepository cities) {
        this.cities = cities;
    }

    public Mono<City> save(City city) {
        return this.cities.save(city);
    }
    
    public Mono<City> findById(String city) {
        return this.cities.findById(city)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

}
