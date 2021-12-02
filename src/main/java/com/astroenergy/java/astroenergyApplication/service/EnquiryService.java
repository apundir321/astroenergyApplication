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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.astroenergy.java.astroenergyApplication.dao.EnquiryRepo;
import com.astroenergy.java.astroenergyApplication.model.Enquiry;
import com.astroenergy.java.astroenergyApplication.model.SearchEnquiry;



@Service
@Transactional
public class EnquiryService {
	@PersistenceContext
	EntityManager em;
	
	
@Autowired
EnquiryRepo enquiryRepo;

public List<Enquiry> getAll(){
try {
	return enquiryRepo.findAll();
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
		Enquiry e=enquiryRepo.findById(id).get();
		return e;
	}
	catch(Exception e) {
		throw e;
	}
}

public Enquiry searchByName(String name) {
	try {
		Enquiry e=enquiryRepo.findByName(name);
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


}
