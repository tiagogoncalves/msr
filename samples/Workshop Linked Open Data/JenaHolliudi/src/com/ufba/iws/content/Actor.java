package com.ufba.iws.content;

import java.util.List;

public class Actor {
	
	private String name;
	private String photoUri;
	private String birthDate;
	private String birthPlace;
	private List<Film> starringOf;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhotoUri() {
		return photoUri;
	}
	public void setPhotoUri(String photoUri) {
		this.photoUri = photoUri;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public List<Film> getStarringOf() {
		return starringOf;
	}
	public void setStarringOf(List<Film> starringOf) {
		this.starringOf = starringOf;
	}
	
	
	

}
