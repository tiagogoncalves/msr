package com.ufba.iws.content;

import java.util.List;

public class Film {
	
	private String filmLabel;
	private String director;
	private String country;
	private List<Actor> actors;
	private String description;
	private String primaryTopic;
	
	public String getFilmLabel() {
		return filmLabel;
	}
	public void setFilmLabel(String filmLabel) {
		this.filmLabel = filmLabel;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<Actor> getActors() {
		return actors;
	}
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrimaryTopic() {
		return primaryTopic;
	}
	public void setPrimaryTopic(String primaryTopic) {
		this.primaryTopic = primaryTopic;
	}
	
	

}
