package com.cloudapps.eoloplanner.toposervice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.cloudapps.eoloplanner.toposervice.model.City;

import reactor.core.publisher.Mono;

public interface CityReactiveRepository extends ReactiveMongoRepository<City, String> {

	Mono<City> findById(String city);

}
