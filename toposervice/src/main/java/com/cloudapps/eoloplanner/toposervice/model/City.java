package com.cloudapps.eoloplanner.toposervice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class City {

	@Id
    private String id;
	
    private String city;
    private String landscape;
    
    public City() {
    	
    }
    
    public City(String city, String landscape) {
    	this.city = city;
    	this.landscape = landscape;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLandscape() {
		return landscape;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}

}