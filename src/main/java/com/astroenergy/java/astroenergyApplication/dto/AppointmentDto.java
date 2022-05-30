package com.astroenergy.java.astroenergyApplication.dto;

import javax.persistence.Lob;

public class AppointmentDto {
private Long id;
@Lob
private String remedy;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getRemedy() {
	return remedy;
}
public void setRemedy(String remedy) {
	this.remedy = remedy;
}

}
