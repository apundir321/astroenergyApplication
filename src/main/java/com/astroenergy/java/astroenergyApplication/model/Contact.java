package com.astroenergy.java.astroenergyApplication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Contact {
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String displayName;
	private String displayAddress;
	private String displayPhone;
	private String displayMobile;
	private String displayWatsapp;
	
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String displaySkype;
	private String displayFaceBook;
	private String displayTwitter;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDisplayAddress() {
		return displayAddress;
	}
	public void setDisplayAddress(String displayAddress) {
		this.displayAddress = displayAddress;
	}
	public String getDisplayPhone() {
		return displayPhone;
	}
	public void setDisplayPhone(String displayPhone) {
		this.displayPhone = displayPhone;
	}
	public String getDisplayMobile() {
		return displayMobile;
	}
	public void setDisplayMobile(String displayMobile) {
		this.displayMobile = displayMobile;
	}
	public String getDisplayWatsapp() {
		return displayWatsapp;
	}
	public void setDisplayWatsapp(String displayWatsapp) {
		this.displayWatsapp = displayWatsapp;
	}
	public String getDisplaySkype() {
		return displaySkype;
	}
	public void setDisplaySkype(String displaySkype) {
		this.displaySkype = displaySkype;
	}
	public String getDisplayFaceBook() {
		return displayFaceBook;
	}
	public void setDisplayFaceBook(String displayFaceBook) {
		this.displayFaceBook = displayFaceBook;
	}
	public String getDisplayTwitter() {
		return displayTwitter;
	}
	public void setDisplayTwitter(String displayTwitter) {
		this.displayTwitter = displayTwitter;
	}
	

}
