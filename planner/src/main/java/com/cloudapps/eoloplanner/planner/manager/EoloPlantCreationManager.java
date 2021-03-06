package com.cloudapps.eoloplanner.planner.manager;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudapps.eoloplanner.planner.broker.BrokerProducer;
import com.cloudapps.eoloplanner.planner.dtos.EoloPlantCreationProgress;
import com.cloudapps.eoloplanner.planner.dtos.EoloPlantCreationProgressBuilder;
import com.cloudapps.eoloplanner.planner.dtos.TopographyResponse;
import com.cloudapps.eoloplanner.planner.service.TopographyService;
import com.cloudapps.eoloplanner.planner.service.WeatherService;
import com.cloudapps.eoloplanner.weatherservice.WeatherServiceOuterClass.Weather;

public class EoloPlantCreationManager {

	/**
	 * PROGRESS
	 * 
	 * 25%	when requests to the services has been sent
	 * 50%	when first answer of one service has been received
	 * 75%	when the answer of the second service has been received
	 * 100% when planification has been created (concatenating the two strings)
	 */

	private final static int PROGRESS_INITIAL = 0;
	private final static int PROGRESS_REQUESTS_SENT = 25;
	private final static int PROGRESS_COMPLETED = 100;
	
	private final static int MIN_WAIT = 1;
	private final static int MAX_WAIT = 3;
	
	@Autowired
	TopographyService topographyService;
	
	@Autowired
	WeatherService weatherService;
	
	@Autowired
	BrokerProducer producer;
	
	private Logger log = LoggerFactory.getLogger(EoloPlantCreationManager.class);
	
	private Long id;
	private String city;
	private int progress;
	private boolean weather;
	private boolean topography;
	private String planning;
	
	public EoloPlantCreationManager(Long id, String city) {
		this.id = id;
		this.city = city;
		this.setProgress(PROGRESS_INITIAL);
		this.setWeather(false);
		this.setTopography(false);
		this.setPlanning(city);
	}
	
	public void process() {
		CompletableFuture<TopographyResponse> topography = topographyService
				.getLandscape(city);
        CompletableFuture<Weather> weather = weatherService
        		.getWeather(city);
        
        this.requestsSentToServices();
        producer.send(this.getCreationProgress());
        
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(topography, weather);
        
        weather.thenRun(() -> {
        	this.weatherResponseReceived(weather.join().getWeather());
			producer.send(this.getCreationProgress());
		});
        
        topography.thenRun(() -> {
        	this.topographyResponseReceived(topography.join().getLandscape());
			producer.send(this.getCreationProgress());
		});
        
        combinedFuture.thenRun(() -> producer.send(this.getCreationProgress()));
	}
	
	private EoloPlantCreationProgress getCreationProgress() {
		
		boolean completed = this.isCompleted();
		String planning = this.getPlanningResult(completed);
		
		EoloPlantCreationProgress message = new EoloPlantCreationProgressBuilder()
				.id(id)
				.city(city)
				.progress(progress)
				.planning(planning)
				.completed(completed)
				.build();
		
		if (completed) {
			int randomProcessTime = new Random().ints(MIN_WAIT, MAX_WAIT+1).findFirst().getAsInt();
			log.info("Random process time for completed plant creation for id {} is {} second(s)", id, randomProcessTime);
			try {
				TimeUnit.SECONDS.sleep(randomProcessTime);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
			
		}
		return message;
	}
	
	private String getPlanningResult(boolean completed) {
		
		if (!completed) {
			log.info("No planning generated because progress is not completed");
			return null;
		}
		else if (this.cityStartsByCharPreviousM()) {
			log.info("Planning value: {}", planning.toLowerCase());
			return planning.toLowerCase();
		}
		else {
			log.info("Planning value: {}", planning.toUpperCase());
			return planning.toUpperCase();
		}
	}
	
	private boolean cityStartsByCharPreviousM() {
		return city.toLowerCase().charAt(0) <= 'm' ? true : false;
	}

	private boolean isCompleted() {
		boolean completed = progress == PROGRESS_COMPLETED;
		if (completed) {
			log.info("Progress: {}% Completed", this.getProgress());
		}
		return completed;
	}

	private void requestsSentToServices() {
		this.setProgress(PROGRESS_REQUESTS_SENT);
		log.info("Progress: {}% Requests sent to the services", this.getProgress());
	}
	
	private void weatherResponseReceived(String weather) {
		this.setProgress(this.getProgress() + 25);
		this.setPlanning(this.getPlanning() + "-" + weather);
		log.info("Progress: {}% Result received from weather service: {}", this.getProgress(), weather);
		this.setWeather(true);
	}
	
	private void topographyResponseReceived(String topography) {
		this.setProgress(this.getProgress() + 25);
		this.setPlanning(this.getPlanning() + "-" + topography);
		log.info("Progress: {}% Result received from topography service: {}", this.getProgress(), topography);
		this.setTopography(true);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getPlanning() {
		return planning;
	}

	public void setPlanning(String planning) {
		this.planning = planning;
	}

	public boolean isWeather() {
		return weather;
	}

	public void setWeather(boolean weather) {
		this.weather = weather;
	}

	public boolean isTopography() {
		return topography;
	}

	public void setTopography(boolean topography) {
		this.topography = topography;
	}
	
}
