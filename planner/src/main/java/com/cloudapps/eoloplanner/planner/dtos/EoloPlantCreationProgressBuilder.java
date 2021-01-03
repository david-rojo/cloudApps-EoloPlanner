package com.cloudapps.eoloplanner.planner.dtos;

public class EoloPlantCreationProgressBuilder {

	private Long id;
	private String city;
	private int progress;
	private boolean completed;
	private String planning;
	
	public EoloPlantCreationProgressBuilder() {
		
	}
	
	public EoloPlantCreationProgressBuilder id(Long id) {
        this.id = id;
        return this;
    }
	
	public EoloPlantCreationProgressBuilder city(String city) {
        this.city = city;
        return this;
    }
	
	public EoloPlantCreationProgressBuilder progress(int progress) {
        this.progress = progress;
        return this;
    }
	
	public EoloPlantCreationProgressBuilder completed(boolean completed) {
        this.completed = completed;
        return this;
    }
	
	public EoloPlantCreationProgressBuilder planning(String planning) {
        this.planning = planning;
        return this;
    }
	
	public EoloPlantCreationProgress build() {
		return new EoloPlantCreationProgress(id, city, progress, completed, planning);
	}
}
