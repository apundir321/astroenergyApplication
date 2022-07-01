package com.astroenergy.java.astroenergyApplication.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Contact;
import com.astroenergy.java.astroenergyApplication.model.Country;
import com.astroenergy.java.astroenergyApplication.model.Enquiry;
import com.astroenergy.java.astroenergyApplication.model.SearchEnquiry;
import com.astroenergy.java.astroenergyApplication.service.EnquiryService;

@RestController
public class EnquiryController {
	@Autowired
	EnquiryService enquiryService;
	private static final Logger log =LogManager.getLogger(EnquiryController.class);
	@DeleteMapping("/deleteEnquiry/{id}")
	public ResponseEntity<?> deleteEnquiry(@PathVariable int id) {
		try {
		Enquiry e= enquiryService.deleteEnquiry(id);
		 return new ResponseEntity<>(e, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
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
	@RequestMapping("/enquiryViewed")
	public ResponseEntity<?> enquiryViewed(@RequestParam int id,String viewed) {
		try {
			Enquiry e = enquiryService.enquiryViewed(id,viewed);
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
	
	@RequestMapping("/addContact")
	public ResponseEntity<?> addContact(@RequestBody Contact contact) {
		try {
			Contact e = enquiryService.addContact(contact);
			return new ResponseEntity<Contact>(e, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping("/getContact")
	public ResponseEntity<?> getContact() {
			Contact e = null;
		try {
			List<Contact> contacts = enquiryService.getContacts();
			if(contacts.size()>0)
			{
				e = contacts.get(0);
				return new ResponseEntity<Contact>(e, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Contact not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (Exception ex) {
			// TODO: handle exception
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// delete all enquiries
	@DeleteMapping("/deleteAllEnquiries")
	public ResponseEntity<?> deleteAllEnquiries(){
	try {
		log.info("Deleting all enquiries...Start");
		List<Enquiry> result= enquiryService.deleteAllEnquiries();
		log.info("Deleting all enquiries...End");
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	catch(Exception e) {
		log.error("Error occured while deleting enquiries",e.getMessage());
	return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	}
	

}
