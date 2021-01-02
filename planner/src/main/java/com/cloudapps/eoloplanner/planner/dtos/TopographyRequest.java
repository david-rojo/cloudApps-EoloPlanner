package com.cloudapps.eoloplanner.planner.dtos;

public class TopographyRequest {
	
	private String city;
	
	public TopographyRequest(String city) {
		this.city = city;
	}
	
	public TopographyRequest() {

	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
