package com.astroenergy.java.astroenergyApplication.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Country {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int sno;

private String name;
private String code;
private String status;
private String remarks;
@CreationTimestamp
@Temporal(value=TemporalType.TIMESTAMP)
private Date createdAt;
@UpdateTimestamp
@Temporal(value=TemporalType.TIMESTAMP)
private Date updatedAt;

private Date deletedAt;

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
public int getSno() {
	return sno;
}
public void setSno(int sno) {
	this.sno = sno;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
@Override
public String toString() {
	return "Country [sno=" + sno + ", name=" + name + ", code=" + code + ", status=" + status + ", remarks=" + remarks
			+ "]";
}
}
