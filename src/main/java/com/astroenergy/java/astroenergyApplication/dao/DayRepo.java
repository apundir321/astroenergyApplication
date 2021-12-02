package com.astroenergy.java.astroenergyApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astroenergy.java.astroenergyApplication.model.Day;
import com.astroenergy.java.astroenergyApplication.model.Enquiry;

@Repository
public interface DayRepo extends JpaRepository<Day,Integer> {
	
	public Day findByDay(String day);

}
