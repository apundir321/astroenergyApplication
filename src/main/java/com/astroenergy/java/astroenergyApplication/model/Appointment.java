package com.astroenergy.java.astroenergyApplication.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Appointment {
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private Date appointDate;
	
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "timeslot_id")
	private TimeSlot timeSlot;
	
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id")
	private UserProfile userProfile;
	
	
	private String name;
	
	private String email;
	
	private String country;
	
	private String placeOfBirth;
	
	private Date dob;
	
	private String timeOfBirth;
	
	private String gender;
	
	private String consultationType;
	
	private String amount;
	
	private int noOfSessions;
	
	private String meetingType;
	
	private String watsappNumber;
	
	private String skypeId;
	
	private String phoneNumber;
	
	private String status;
	
	private String orderId;
	
	@Lob
	private String remedy;
	
	@CreationTimestamp
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date createdAt;
	@UpdateTimestamp
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	private String viewed;
	private Date deletedAt;
	
	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getRemedy() {
		return remedy;
	}

	public String getViewed() {
		return viewed;
	}

	public void setViewed(String viewed) {
		this.viewed = viewed;
	}

	public void setRemedy(String remedy) {
		this.remedy = remedy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public String getConsultationType() {
		return consultationType;
	}

	public void setConsultationType(String consultationType) {
		this.consultationType = consultationType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public int getNoOfSessions() {
		return noOfSessions;
	}

	public void setNoOfSessions(int noOfSessions) {
		this.noOfSessions = noOfSessions;
	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	public String getWatsappNumber() {
		return watsappNumber;
	}

	public void setWatsappNumber(String watsappNumber) {
		this.watsappNumber = watsappNumber;
	}

	public String getSkypeId() {
		return skypeId;
	}

	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAppointDate() {
		return appointDate;
	}

	public void setAppointDate(Date appointDate) {
		this.appointDate = appointDate;
	}

	public String getTimeOfBirth() {
		return timeOfBirth;
	}

	public void setTimeOfBirth(String timeOfBirth) {
		this.timeOfBirth = timeOfBirth;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
	
	

}
