package com.astroenergy.java.astroenergyApplication.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Ratelist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sno;
	private String countryName;
	private String consultationType;
	private int rateOfValues;
	private String status;
	private String remarks;
	@CreationTimestamp
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date createdAt;
	@UpdateTimestamp
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	private Date deletedAt;
	public Ratelist() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getConsultationType() {
		return consultationType;
	}
	public void setConsultationType(String consultationType) {
		this.consultationType = consultationType;
	}
	public int getRateOfValues() {
		return rateOfValues;
	}
	public void setRateOfValues(int rateOfValues) {
		this.rateOfValues = rateOfValues;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Ratelist [sno=" + sno + ", countryName=" + countryName + ", consultationType=" + consultationType
				+ ", rateOfValues=" + rateOfValues + ", status=" + status + ", remarks=" + remarks + "]";
	}
	
	
	
}
