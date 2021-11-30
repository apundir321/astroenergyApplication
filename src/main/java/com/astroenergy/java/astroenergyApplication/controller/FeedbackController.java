package com.astroenergy.java.astroenergyApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astroenergy.java.astroenergyApplication.model.Country;
import com.astroenergy.java.astroenergyApplication.model.Enquiry;
import com.astroenergy.java.astroenergyApplication.model.Feedback;
import com.astroenergy.java.astroenergyApplication.service.FeedbackService;

@RestController
public class FeedbackController {
@Autowired
FeedbackService feedbackService;
@RequestMapping("/addFeedback")
public ResponseEntity<?> addFeedback(@RequestBody Feedback feedback) {
	try {
	Feedback f = feedbackService.addFeedback(feedback);
	 return new ResponseEntity<Feedback>(f, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/getAllFeedback")
public ResponseEntity<?> allFeedback() {
	try {
	List<Feedback> f= feedbackService.getAll();
	 return new ResponseEntity<>(f, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@RequestMapping("/getFeedback")
public ResponseEntity<?> getFeedback(@RequestParam String name ) {
	try {
	List<Feedback> f= feedbackService.getFeedback(name);
	 return new ResponseEntity<>(f, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
}
