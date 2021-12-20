package com.astroenergy.java.astroenergyApplication.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.FeedbackRepo;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Feedback;

@Service
public class FeedbackService {
	@Autowired
	FeedbackRepo feedbackRepo;
	
	public Feedback deleteFeedback(int id) throws Exception  {
		try {
			Feedback f=feedbackRepo.findBySnoAndDeletedAtIsNull(id);
			f.setDeletedAt(new Date());
			return feedbackRepo.save(f);
		}
		catch(Exception e) {
			throw e;
		}
	}
	public Feedback feedbackViewed(int id,String viewed) {
		try {
			Feedback f=feedbackRepo.findBySnoAndDeletedAtIsNull(id);
			f.setViewed(viewed);
			return feedbackRepo.save(f);
		}
		catch(Exception e) {
			throw e;
		}
		
	}

	public List<Feedback> getAll() {

		try {

			return feedbackRepo.findByDeletedAtIsNullOrderBySnoDesc();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;

		}

	}

	public Feedback addFeedback(Feedback feedback) throws Exception {
		try {
			Feedback f = feedbackRepo.save(feedback);
			return f;
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Feedback> getFeedback(String name) {
		try {
			List<Feedback> f = feedbackRepo.findByFirstNameAndDeletedAtIsNull(name);
			return f;
		} catch (Exception e) {
			throw e;
		}
	}

	public Feedback getFeedbackDetail(int id) {
		try {
			Feedback f = feedbackRepo.findBySnoAndDeletedAtIsNull(id);
			return f;
		} catch (Exception e) {
			throw e;
		}
	}
}
