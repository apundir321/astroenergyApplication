package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.UserProfile;

@Repository
public interface AppointMentRepo   extends CrudRepository<Appointment, Long> {
	
	public List<Appointment> findByStatus(String status);

}
