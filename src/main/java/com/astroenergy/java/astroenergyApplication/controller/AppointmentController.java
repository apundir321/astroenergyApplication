package com.astroenergy.java.astroenergyApplication.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Country;
import com.astroenergy.java.astroenergyApplication.model.SearchAppointment;
import com.astroenergy.java.astroenergyApplication.service.AppointmentService;

@RestController
public class AppointmentController {
	
	@Autowired
	AppointmentService appointmentService;
	
	@GetMapping("/allAppointments")
	public ResponseEntity<?> allAppointments() {
		try {
		List<Appointment> c= appointmentService.getAppointments();
		 return new ResponseEntity<>(c, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/saveAppointment")
	public ResponseEntity<?> saveAppointment(@RequestBody Appointment appointment,@RequestParam int userId) {
		try {
		Appointment savedAppointment= appointmentService.addAppointment(appointment, userId);
		 return new ResponseEntity<>(savedAppointment, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAppointment/{status}")
	public ResponseEntity<?> getAppointmentByStatus(@PathVariable String status) {
		try {
			List<Appointment> c= appointmentService.getAppointMentByStatus(status);
			 return new ResponseEntity<>(c, HttpStatus.OK);
		
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAppointmentDetail")
	public ResponseEntity<?> getAppointmentDetail(@RequestParam long id) {
		try {
			Appointment c= appointmentService.getAppointMentDetail(id);
			 return new ResponseEntity<>(c, HttpStatus.OK);
		
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/editAppointment")
	public ResponseEntity<?> editAppointment(@RequestBody Appointment appointment,@RequestParam int userId) {
		try {
		Appointment savedAppointment= appointmentService.addAppointment(appointment, userId);
		 return new ResponseEntity<>(savedAppointment, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/changeAppointmentStatus")
	public ResponseEntity<?> updateAppointmentStatus(@RequestParam Long id,@RequestParam String status) {
		try {
		Optional<Appointment> savedAppointment= appointmentService.updateAppointmentStatus(id,status);
		 return new ResponseEntity<>(savedAppointment, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/getAppointmentsByCriteria")
	public ResponseEntity<?> editAppointment(@RequestBody SearchAppointment appointment) {
		try {
		List<Appointment> appointments= appointmentService.getAppointmentByCriteria(appointment);
		 return new ResponseEntity<>(appointments, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/getUserAppointments")
	public ResponseEntity<?> getUserAppointments(@RequestParam long id) {
		try {
			List<Appointment> a= appointmentService.getUserAppointments(id);
			 return new ResponseEntity<>(a, HttpStatus.OK);
		
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/ApplyPromo")
	public ResponseEntity<?> applyPromo(@RequestParam Long appointmentId,@RequestParam int promoId) {
		try {
		Appointment savedAppointment= appointmentService.applyPromoCode(appointmentId, promoId);
		 return new ResponseEntity<>(savedAppointment, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/addRemedyAppointment")
	public ResponseEntity<?> addRemedyAppointment(@RequestParam Long id,@RequestParam String remedy) {
		try {
			Appointment a= appointmentService.addRemedyAppointment(id,remedy);
			 return new ResponseEntity<>(a, HttpStatus.OK);
		
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
