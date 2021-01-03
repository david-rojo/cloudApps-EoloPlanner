package com.cloudapps.eoloplanner.toposervice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudapps.eoloplanner.toposervice.model.City;
import com.cloudapps.eoloplanner.toposervice.repository.CityReactiveRepository;

import reactor.core.publisher.Flux;

@Configuration
public class ToposerviceDataLoader {

	private Logger log = LoggerFactory.getLogger(ToposerviceDataLoader.class);
	
	@Bean
	ApplicationRunner init(CityReactiveRepository repository) {

		Object[][] cities = {
				{"Madrid", "flat"},
				{"Barcelona", "flat"},
				{"Santander", "mountain"},
				{"Burgos", "mountain"},
				{"Bilbao", "flat"},
				{"Valencia", "flat"},
				{"Granada", "mountain"},
				{"Paris", "mountain"},
				{"London", "flat"},
				{"New York", "flat"},
				{"Bangkok", "flat"},
				{"Munich", "mountain"},				
				{"Tokyo", "flat"},
				{"Jakarta", "flat"},
				{"Mexico City", "mountain"},
				{"Moscow", "mountain"},
				{"Istanbul", "flat"},
				{"Rio de Janeiro", "flat"},
				{"Cairo", "flat"},
				{"Kuala Lumpur", "mountain"},
				{"Toronto", "mountain"},
				{"Kathmandu", "mountain"}
		};

		return args -> {
			repository
			.deleteAll()
			.thenMany(
				Flux.just(cities)
					.map(array -> {return new City((String) array[0], (String) array[1]);})
					.flatMap(repository::save))
			.thenMany(repository.findAll())
			.subscribe(city -> log.info("Saving to db {}", city.toString()));
		};
	}
}
