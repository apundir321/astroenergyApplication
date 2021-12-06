package com.astroenergy.java.astroenergyApplication.controller;

import java.util.Date;
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
import com.astroenergy.java.astroenergyApplication.model.SearchEnquiry;
import com.astroenergy.java.astroenergyApplication.service.EnquiryService;

@RestController
public class EnquiryController {
	@Autowired
	EnquiryService enquiryService;

	@RequestMapping("/getAllEnquiries")
	public ResponseEntity<?> allEnquiries() {
		try {
			List<Enquiry> e = enquiryService.getAll();
			return new ResponseEntity<>(e, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/AddEnquiry")
	public ResponseEntity<?> addEnquiry(@RequestBody Enquiry enquiry) {
		try {
			Enquiry e = enquiryService.addEnquiry(enquiry);
			return new ResponseEntity<Enquiry>(e, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/editEnquiry")
	public ResponseEntity<?> editEnquiry(@RequestBody Enquiry enquiry) {
		try {
			Enquiry e = enquiryService.addEnquiry(enquiry);
			return new ResponseEntity<Enquiry>(e, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/getEnquiryDetail")
	public ResponseEntity<?> getEnquiry(@RequestParam int id) {
		try {
			Enquiry e = enquiryService.getEnquiryDetail(id);
			return new ResponseEntity<>(e, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/getEnquiry")
	public ResponseEntity<?> getEnquiry(@RequestBody SearchEnquiry enquiry) {
		try {
			List<Enquiry> e = enquiryService.getEnquiry(enquiry);
			return new ResponseEntity<>(e, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
