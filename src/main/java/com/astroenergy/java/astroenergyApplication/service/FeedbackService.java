package com.astroenergy.java.astroenergyApplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.FeedbackRepo;
import com.astroenergy.java.astroenergyApplication.model.Feedback;

@Service
public class FeedbackService  {
@Autowired
FeedbackRepo feedbackRepo;

public List<Feedback> getAll(){
	
	
	try {
		
		 return feedbackRepo.findAll();
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		
}

}
public Feedback addFeedback(Feedback feedback) throws Exception{
	try {
		Feedback f=feedbackRepo.save(feedback);
		return f;
	}catch (Exception e) {
		throw e;
	}
}

public List<Feedback>getFeedback(String name) {
	try{
		List<Feedback> f=feedbackRepo.findByFirstName(name);
    return f;
}catch(Exception e) {
throw e;	
}
	}
}




