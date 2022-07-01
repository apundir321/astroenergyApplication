package com.astroenergy.java.astroenergyApplication.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.astroenergy.java.astroenergyApplication.dao.ContactRepo;
import com.astroenergy.java.astroenergyApplication.dao.EnquiryRepo;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Contact;
import com.astroenergy.java.astroenergyApplication.model.Enquiry;
import com.astroenergy.java.astroenergyApplication.model.SearchEnquiry;



@Service
@Transactional
public class EnquiryService {
	@PersistenceContext
	EntityManager em;
	
	
@Autowired
EnquiryRepo enquiryRepo;

@Autowired
ContactRepo contactRepo;
private static final Logger log=LogManager.getLogger(EnquiryService.class);
public Enquiry deleteEnquiry(int id) throws Exception  {
	try {
		Enquiry a=enquiryRepo.findBySnoAndDeletedAtIsNull(id);
		a.setDeletedAt(new Date());
		return enquiryRepo.save(a);
	}
	catch(Exception e) {
		throw e;
	}
}
public Enquiry enquiryViewed(int id,String viewed) {
	try {
		Enquiry e=enquiryRepo.findBySnoAndDeletedAtIsNull(id);
		e.setViewed(viewed);
		return enquiryRepo.save(e);
	}
	catch(Exception e) {
		throw e;
	}
}
public List<Enquiry> getAll(){
try {
	return enquiryRepo.findByDeletedAtIsNullOrderBySnoDesc();
}catch(Exception e){
	throw e;
	}
	
}
public Enquiry addEnquiry(Enquiry enquiry) {
	try {
		Enquiry e=enquiryRepo.save(enquiry);
		return e;
	}
	catch(Exception e) {
		throw e;
	}
}

public Enquiry getEnquiryDetail(int id) {
	try {
		Enquiry e=enquiryRepo.findBySnoAndDeletedAtIsNull(id);
		return e;
	}
	catch(Exception e) {
		throw e;
	}
}

public Enquiry searchByName(String name) {
	try {
		Enquiry e=enquiryRepo.findByNameAndDeletedAtIsNull(name);
	return e;}
	catch(Exception e){throw e;}
}

public List<Enquiry> getEnquiry(SearchEnquiry searchEnquiry) {
	 CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Enquiry> cq = cb.createQuery(Enquiry.class);
	    
	    Root<Enquiry> enquiry = cq.from(Enquiry.class);
	    List<Predicate> predicates = new ArrayList<>();
	  
	    if(searchEnquiry.getName()!=null) {
	    	try{predicates.add(cb.equal(enquiry.get("name"), searchEnquiry.getName()));
	    	
	    }
	  catch(Exception e) {
		  e.printStackTrace();
		  
	  }}
	    if(searchEnquiry.getStatus()!=null) {
	    	try{predicates.add(cb.equal(enquiry.get("status"), searchEnquiry.getStatus()));
	    	
	    }
	  catch(Exception e) {
		  e.printStackTrace();
		  
	  }}
		    if(searchEnquiry.getFromDate()!=null) {
		    	try{predicates.add(cb.greaterThanOrEqualTo(enquiry.get("date"), searchEnquiry.getFromDate()));
		    	}
		    
		  catch(Exception e) {
			  e.printStackTrace();
			  
		  }}
		    if(searchEnquiry.getToDate()!=null) {
		    	try{predicates.add(cb.lessThanOrEqualTo(enquiry.get("date"), searchEnquiry.getToDate()));
		    	}
		    
		  catch(Exception e) {
			  e.printStackTrace();
			  
		  }}
	    cq.where(predicates.toArray(new Predicate[0]));

	    return em.createQuery(cq).getResultList();
}


public Contact addContact(Contact contact) {
	try {
		Contact e=contactRepo.save(contact);
		return e;
	}
	catch(Exception e) {
		throw e;
	}
}

	public List<Contact> getContacts()
	{
		try {
			return contactRepo.findAll();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public List<Enquiry> deleteAllEnquiries() {
		try {
			log.info("Getting stored enquiries...Start");
			List<Enquiry> storedEnquiries=enquiryRepo.findByDeletedAtIsNullOrderBySnoDesc();
			log.info("Getting stored enquiries...End");
			storedEnquiries.forEach(enquiry->{
				enquiry.setDeletedAt(new Date());
				enquiryRepo.save(enquiry);
			});
		return storedEnquiries;
		}
		catch(Exception e) {
			throw e;
		}
	}


}
