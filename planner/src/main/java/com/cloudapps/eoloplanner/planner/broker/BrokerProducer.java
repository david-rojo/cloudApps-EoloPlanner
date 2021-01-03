package com.cloudapps.eoloplanner.planner.broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cloudapps.eoloplanner.planner.dtos.EoloPlantCreationProgress;

@Component
public class BrokerProducer {

	private Logger log = LoggerFactory.getLogger(BrokerProducer.class);
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Value("${broker.creationprogress.queue}")
	private String creationProgressQueue;
	
	public void send(EoloPlantCreationProgress progress) {

		log.info("publishToQueue: '" + progress.toString() + "'");
		rabbitTemplate.convertAndSend(creationProgressQueue, progress);
	}	
	
}
