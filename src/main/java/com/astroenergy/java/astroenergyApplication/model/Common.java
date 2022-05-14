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

public class Common {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
private String image;
@CreationTimestamp
@Temporal(value=TemporalType.DATE)
private Date createdAt;
@UpdateTimestamp
@Temporal(value=TemporalType.DATE)
private Date updatedAt;
private Date deletedAt;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
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

}
