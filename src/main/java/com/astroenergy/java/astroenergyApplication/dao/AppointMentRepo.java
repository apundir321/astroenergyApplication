package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.UserProfile;

@Repository
public interface AppointMentRepo   extends JpaRepository<Appointment, Long> {
	
	public List<Appointment> findByStatus(String status);
	public List<Appointment> findByUserProfileId(int id);
           
}
