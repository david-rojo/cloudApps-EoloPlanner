package com.cloudapps.eoloplanner.toposervice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cities")
public class City {
	
	@Id
    private String id;
	private String landscape;
    
    public City() {
    	
    }
    
    public City(String id, String landscape) {
    	this.id = id;
    	this.landscape = landscape;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLandscape() {
		return landscape;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}
	
	@Override
    public String toString() {
        return "City {" +
        	"id='" + id + "\', " +
        	"landscape='" + landscape + "\'" +
            "}";
    }

}