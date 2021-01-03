package com.cloudapps.eoloplanner.planner.broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.cloudapps.eoloplanner.planner.dtos.EoloPlantCreationRequest;
import com.cloudapps.eoloplanner.planner.manager.EoloPlantCreationManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BrokerConsumer {
	
	private Logger log = LoggerFactory.getLogger(BrokerConsumer.class);	
	
	ObjectMapper mapper = new ObjectMapper();
	
	EoloPlantCreationManager manager;
	
	@RabbitListener(queues = "${broker.creationrequest.queue}", ackMode = "AUTO")
	public void received(String message) {
		
		log.info("Message received in queue: {}", message);
		
		try {
			
			EoloPlantCreationRequest request = mapper.readValue(message, EoloPlantCreationRequest.class);
			manager = new EoloPlantCreationManager(request.getId(), request.getCity());
			manager.process();
			
	        	
		} catch (JsonMappingException e) {
			log.error("Json Mapping Error with message {}: {}", message, e.getMessage());
		} catch (JsonProcessingException e) {
			log.error("Json Processing Error with message {}: {}", message, e.getMessage());
		}		
	}

}
