package com.astroenergy.java.astroenergyApplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.astroenergy.java.astroenergyApplication.dao.AppointMentRepo;
import com.astroenergy.java.astroenergyApplication.dao.TimeSlotRepo;
import com.astroenergy.java.astroenergyApplication.dao.UserProfileRepository;
import com.astroenergy.java.astroenergyApplication.dao.UserRepository;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Enquiry;
import com.astroenergy.java.astroenergyApplication.model.SearchAppointment;
import com.astroenergy.java.astroenergyApplication.model.SearchEnquiry;
import com.astroenergy.java.astroenergyApplication.model.TimeSlot;
import com.astroenergy.java.astroenergyApplication.model.User;
import com.astroenergy.java.astroenergyApplication.model.UserProfile;


@Service
public class AppointmentService {
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	AppointMentRepo appointMentRepo;
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	TimeSlotRepo timeSlotRepo; 
	
	@PersistenceContext
	EntityManager em;
	
	
	public List<Appointment> getAppointments()throws Exception
	{
		try {
			return (List<Appointment>) appointMentRepo.findAll();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	public Optional<Appointment> updateAppointmentStatus(Long id,String status ) throws Exception{
		try {
		Optional<Appointment> ap=appointMentRepo.findById(id);
              if(ap.isPresent())
              {
            	  Appointment a = ap.get();
            	  a.setStatus(status);
            	  appointMentRepo.save(a);
            	  
              }
              
              else
              {
            	  throw new Exception("appointment not found with this id");
              }
              return ap;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public Appointment addAppointment(Appointment appointment,int userProfileId)throws Exception
	{
		
		try {
			Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
			if(userProfileOptional.isPresent()) {
				appointment.setUserProfile(userProfileOptional.get());
				TimeSlot timeSlot = timeSlotRepo.findById(appointment.getTimeSlot().getId()).get();
				
				appointment.setTimeSlot(timeSlot);
				return appointMentRepo.save(appointment);
				
			}else
			{
				throw new Exception("Not able to find profile with this id");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
	}
	
	
	public List<Appointment> getAppointMentByStatus(String status)throws Exception
	{
		try {
			return (List<Appointment>) appointMentRepo.findByStatus(status);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Appointment getAppointMentDetail(long id)throws Exception
	{
		try {
			return appointMentRepo.findById(id).get();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Appointment> getAppointmentByCriteria(SearchAppointment searchAppointment) {
		 CriteriaBuilder cb = em.getCriteriaBuilder();
		    CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);
		    
		    Root<Appointment> appointment = cq.from(Appointment.class);
		    List<Predicate> predicates = new ArrayList<>();
		  
		   
		    if(searchAppointment.getStatus()!=null) {
		    	try{predicates.add(cb.equal(appointment.get("status"), searchAppointment.getStatus()));
		    	
		    }
		  catch(Exception e) {
			  e.printStackTrace();
			  
		  }}
			    if(searchAppointment.getFromDate()!=null) {
			    	try{predicates.add(cb.greaterThanOrEqualTo(appointment.get("appointDate"), searchAppointment.getFromDate()));
			    	}
			    
			  catch(Exception e) {
				  e.printStackTrace();
				  
			  }}
			    if(searchAppointment.getToDate()!=null) {
			    	try{predicates.add(cb.lessThanOrEqualTo(appointment.get("appointDate"), searchAppointment.getToDate()));
			    	}
			    
			  catch(Exception e) {
				  e.printStackTrace();
				  
			  }}
		    cq.where(predicates.toArray(new Predicate[0]));

		    return em.createQuery(cq).getResultList();
	}
	public List<Appointment> getUserAppointments(Long userId){
		try {
		User user=userRepo.findById(userId).get();
		UserProfile userProfile=user.getUserProfile();
		int profileId=userProfile.getId();
		List<Appointment> appointments=appointMentRepo.findByUserProfileId(profileId);
		return appointments;
		
		}
		catch(Exception e) {
		throw e;	
		}
		
		
	}

}
