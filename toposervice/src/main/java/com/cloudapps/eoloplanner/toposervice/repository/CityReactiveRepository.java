package com.cloudapps.eoloplanner.toposervice.repository;

import org.springframework.stereotype.Repository;

import com.cloudapps.eoloplanner.toposervice.model.City;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

@Repository
public interface CityReactiveRepository extends ReactiveMongoRepository<City, String> {

	Mono<City> findById(String city);

}
