package com.cloudapps.eoloplanner.toposervice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudapps.eoloplanner.toposervice.model.City;
import com.cloudapps.eoloplanner.toposervice.service.CityService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/topographicdetails")
public class CityController {
	
	private Logger log = LoggerFactory.getLogger(CityController.class);
	
	@Autowired
	CityService cities;
	
	@GetMapping("/{id}")
	public Mono<City> getPost(@PathVariable String id) {

		log.info("getPost method invoked");
		return null;
	}

}
