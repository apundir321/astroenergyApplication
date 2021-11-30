package com.astroenergy.java.astroenergyApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astroenergy.java.astroenergyApplication.model.Enquiry;

public interface EnquiryRepo extends JpaRepository<Enquiry,Integer> {
	
public Enquiry findByName(String name);
}
