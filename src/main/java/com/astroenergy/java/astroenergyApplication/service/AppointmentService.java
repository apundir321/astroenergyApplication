package com.astroenergy.java.astroenergyApplication.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.astroenergy.java.astroenergyApplication.dao.PromoRepo;
import com.astroenergy.java.astroenergyApplication.dao.TimeSlotRepo;
import com.astroenergy.java.astroenergyApplication.dao.UserProfileRepository;
import com.astroenergy.java.astroenergyApplication.dao.UserRepository;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Enquiry;
import com.astroenergy.java.astroenergyApplication.model.PromoCode;
import com.astroenergy.java.astroenergyApplication.model.SearchAppointment;
import com.astroenergy.java.astroenergyApplication.model.SearchEnquiry;
import com.astroenergy.java.astroenergyApplication.model.TimeSlot;
import com.astroenergy.java.astroenergyApplication.model.User;
import com.astroenergy.java.astroenergyApplication.model.UserProfile;
import com.astroenergy.java.astroenergyApplication.security.UserService;


@Service
public class AppointmentService {
	@Autowired
	PromoRepo promoRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	AppointMentRepo appointMentRepo;
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	TimeSlotRepo timeSlotRepo; 
	@Autowired 
	UserService userService;
	@PersistenceContext
	EntityManager em;
	
	public Appointment appointmentViewed(Long id,String viewed) {
		try {
			Appointment a=appointMentRepo.findByIdAndDeletedAtIsNull(id);
		a.setViewed(viewed);
		return appointMentRepo.save(a);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public Appointment deleteAppointment(Long id) throws Exception  {
		try {
			Appointment a=appointMentRepo.findByIdAndDeletedAtIsNull(id);
			a.setDeletedAt(new Date());
			return appointMentRepo.save(a);
		}
		catch(Exception e) {
			throw e;
		}
	}
	public List<Appointment> getAppointments()throws Exception
	{
		try {
			return (List<Appointment>) appointMentRepo.findByDeletedAtIsNullOrderByIdDesc();
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
	 
	
	
	public Appointment updateAppointmentDate(Long id,Date date) throws Exception{
		try {
			Appointment a=appointMentRepo.findByIdAndDeletedAtIsNull(id);
			a.setAppointDate(date);
			return appointMentRepo.save(a);
		}
		catch(Exception e) {
			throw e; 
		}
	}
	public Appointment addAppointment(Appointment appointment,Long userProfileId)throws Exception
	{
		
		try {
			long userId = userProfileId;
			Optional<User> userOptional = userRepo.findById(userId);
			if(userOptional.isPresent()) {
				appointment.setUserProfile(userOptional.get().getUserProfile());
				if(appointment.getTimeSlot()!=null)
				{
				TimeSlot timeSlot = timeSlotRepo.findById(appointment.getTimeSlot().getId()).get();
				appointment.setTimeSlot(timeSlot);
				}
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
	public List<Appointment> getUserAppointmentByStatus(Long userId,String status){
		try {
			User user=userRepo.findById(userId).get();
			UserProfile userProfile=user.getUserProfile();
			int profileId=userProfile.getId();
			List<Appointment> appointments=appointMentRepo.findByUserProfileIdAndDeletedAtIsNull(profileId);
			List <Appointment> requiredAppointment=new ArrayList<>();
			for(Appointment appoint:appointments) {
				if(appoint.getStatus().equals(status)){
					requiredAppointment.add(appoint);
				}
			}
			return requiredAppointment;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public List<Appointment> getAppointMentByStatus(String status)throws Exception
	{
		try {
			return (List<Appointment>) appointMentRepo.findByStatusAndDeletedAtIsNull(status);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Appointment getAppointMentDetail(long id)throws Exception
	{
		try {
			return appointMentRepo.findByIdAndDeletedAtIsNull(id);
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
		    
		    if(searchAppointment.getId()!=null) {
		    	User user=userRepo.findById(Long.parseLong(searchAppointment.getId())).get();
				UserProfile userProfile=user.getUserProfile();
		    	try{predicates.add(cb.equal(appointment.get("userProfile"), userProfile));		    	
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
		List<Appointment> appointments=appointMentRepo.findByUserProfileIdAndDeletedAtIsNull(profileId);
		return appointments;
		
		}
		catch(Exception e) {
		throw e;	
		}
		
		
	}
	
	
	public Appointment applyPromoCode(Long appointmentId,int promoId) {
		try{Appointment appointment=appointMentRepo.findById(appointmentId).get();
		PromoCode promo= promoRepo.findById(promoId).get();
		
		if(promo.getType().equals("Percentage")) {
			float percent=Float.parseFloat(promo.getAmount());
		    float amount=Float.parseFloat(appointment.getAmount());
			float reduce= amount*(percent/100);
			float actual=amount-reduce;
		    appointment.setAmount(String.valueOf(actual));
		return    appointMentRepo.save(appointment);
			
		}
		else {
			float  amount=Float.parseFloat(appointment.getAmount());
			float reduce=Float.parseFloat(promo.getAmount());
			float actual=amount-reduce;
			appointment.setAmount(String.valueOf(actual));
			return appointMentRepo.save(appointment);
			
			
		}
		
		}
		catch(Exception e) {
			throw e;
		}
		
	}
	public Appointment addRemedyAppointment(Long id,String remedy) {
		try {
			Appointment a=appointMentRepo.findById(id).get();
			a.setRemedy(remedy);
			return appointMentRepo.save(a);
		}
		catch(Exception e) {
			throw e;
		}
	}

}
