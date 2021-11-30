package com.astroenergy.java.astroenergyApplication.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SearchEnquiry {
private String name;
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date fromDate;
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date toDate;
private String status;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Date getFromDate() {
	return fromDate;
}
public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
}
public Date getToDate() {
	return toDate;
}
public void setToDate(Date toDate) {
	this.toDate = toDate;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}


}
