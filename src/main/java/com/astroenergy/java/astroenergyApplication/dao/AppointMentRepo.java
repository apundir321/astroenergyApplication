package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.UserProfile;

@Repository
public interface AppointMentRepo   extends JpaRepository<Appointment, Long> {
	
	public List<Appointment> findByStatusAndDeletedAtIsNull(String status);
	
	public List<Appointment> findByUserProfileIdAndDeletedAtIsNull(int id);
//	@Query(value="SELECT a FROM Appointment a WHERE a.status != 'DELETED' ORDER BY a.id DESC")
    public List<Appointment> findByDeletedAtIsNullOrderByIdDesc();
//	@Query(value="SELECT a FROM Appointment a WHERE a.id = ?1")
	public Appointment findByIdAndDeletedAtIsNull(Long id);
}
