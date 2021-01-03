package com.cloudapps.eoloplanner.planner.broker;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudapps.eoloplanner.planner.dtos.EoloPlantCreationRequest;
import com.cloudapps.eoloplanner.planner.dtos.TopographyResponse;
import com.cloudapps.eoloplanner.planner.manager.EoloPlantCreationManager;
import com.cloudapps.eoloplanner.planner.service.TopographyService;
import com.cloudapps.eoloplanner.planner.service.WeatherService;
import com.cloudapps.eoloplanner.weatherservice.WeatherServiceOuterClass.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BrokerConsumer {
	
	private Logger log = LoggerFactory.getLogger(BrokerConsumer.class);
	
	@Autowired
	TopographyService topographyService;
	
	@Autowired
	WeatherService weatherService;
	
	@Autowired
	BrokerProducer producer;
	
	ObjectMapper mapper = new ObjectMapper();
	
	EoloPlantCreationManager manager;
	
	@RabbitListener(queues = "${broker.creationrequest.queue}", ackMode = "AUTO")
	public void received(String message) {
		
		log.info("Message received in queue: {}", message);
		
		try {
			
			EoloPlantCreationRequest request = mapper.readValue(message, EoloPlantCreationRequest.class);
			manager = new EoloPlantCreationManager(request.getId(), request.getCity());
			
			CompletableFuture<TopographyResponse> topography = topographyService
					.getLandscape(request.getCity());
	        CompletableFuture<Weather> weather = weatherService
	        		.getWeather(request.getCity());
	        
	        manager.requestsSentToServices();
	        producer.send(manager.getCreationProgress());
	        
	        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(topography, weather);
	        
	        weather.thenRun(() -> {
				manager.weatherResponseReceived(weather.join().getWeather());
				producer.send(manager.getCreationProgress());
			});
	        
	        topography.thenRun(() -> {
				manager.topographyResponseReceived(topography.join().getLandscape());
				producer.send(manager.getCreationProgress());
			});
	        
	        combinedFuture.thenRun(() -> producer.send(manager.getCreationProgress()));
	        	
		} catch (JsonMappingException e) {
			log.error("Json Mapping Error with message {}: {}", message, e.getMessage());
		} catch (JsonProcessingException e) {
			log.error("Json Processing Error with message {}: {}", message, e.getMessage());
		}		
	}

}
