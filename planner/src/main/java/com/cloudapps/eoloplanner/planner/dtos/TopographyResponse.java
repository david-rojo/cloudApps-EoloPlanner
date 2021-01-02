package com.cloudapps.eoloplanner.planner.dtos;

public class TopographyResponse {

	private String id;
	private String landscape;
	
	public TopographyResponse(String id, String landscape) {
		this.id = id;
		this.landscape = landscape;
	}

	public TopographyResponse() {
		
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
	
}
