package com.cloudapps.eoloplanner.planner.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueuesConfiguration {
	
	@Value("${broker.creationrequest.queue}")
	private String creationRequestQueue;
	
	@Value("${broker.creationprogress.queue}")
	private String creationProgressQueue;
			
	@Bean
	public Queue eoloplantCreationRequests() {
		return new Queue(creationRequestQueue, false);
	}

	@Bean
	public Queue eoloplantCreationProgressNotifications() {
		return new Queue(creationProgressQueue, false);
	}
	
}
