package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astroenergy.java.astroenergyApplication.model.Enquiry;

public interface EnquiryRepo extends JpaRepository<Enquiry,Integer> {
	
public Enquiry findByNameAndDeletedAtIsNull(String name);

Enquiry findBySnoAndDeletedAtIsNull(int id);

List<Enquiry> findByDeletedAtIsNullOrderBySnoDesc();
}
