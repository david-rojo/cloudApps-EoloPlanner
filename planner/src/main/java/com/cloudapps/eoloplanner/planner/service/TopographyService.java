package com.cloudapps.eoloplanner.planner.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloudapps.eoloplanner.planner.dtos.TopographyRequest;
import com.cloudapps.eoloplanner.planner.dtos.TopographyResponse;

@Service
public class TopographyService {

	@Value("${topographyservice.url}")
	private String topographyServiceUrl;
	
	private Logger log = LoggerFactory.getLogger(TopographyService.class);
	
	@Async
    public CompletableFuture<TopographyResponse> getLandscape(String city) {
		log.info("Executing topography request for city {}...", city);
		TopographyRequest request = new TopographyRequest(city);
		
        TopographyResponse response = new RestTemplate().getForObject(
        		getEndpointUrl(request),
        		TopographyResponse.class);
        
        return CompletableFuture.completedFuture(response);
    }
	
	private String getEndpointUrl(TopographyRequest request) {
		String url = topographyServiceUrl + "/" + request.getCity();
		log.info("topography url build: {}", url);
		return url;
	}
	
}
