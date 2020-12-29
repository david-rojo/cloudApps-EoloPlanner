package com.cloudapps.eoloplanner.toposervice.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudapps.eoloplanner.toposervice.model.City;

@Service
public class SampleDataService {

	private Logger log = LoggerFactory.getLogger(SampleDataService.class);
	
	@Autowired
	private CityService cities; 
	
	@PostConstruct
	public void init() {

		log.info("Starting cities loading in database");
		cities.save(new City("Madrid", "flat"));
		cities.save(new City("Paris", "mountain"));
		cities.save(new City("London", "flat"));
		cities.save(new City("New York", "flat"));
		cities.save(new City("Bangkok", "flat"));
		cities.save(new City("Munich", "mountain"));
		cities.save(new City("Barcelona", "flat"));
		cities.save(new City("Tokyo", "flat"));
		cities.save(new City("Jakarta", "flat"));
		cities.save(new City("Mexico City", "mountain"));
		cities.save(new City("Moscow", "mountain"));
		cities.save(new City("Istanbul", "flat"));
		cities.save(new City("Rio de Janeiro", "flat"));
		cities.save(new City("Cairo", "flat"));
		cities.save(new City("Kuala Lumpur", "mountain"));
		cities.save(new City("Toronto", "mountain"));
		cities.save(new City("Kathmandu", "mountain"));
		log.info("Cities loaded successfully in database");
	}
}
