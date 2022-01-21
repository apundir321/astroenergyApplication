package com.astroenergy.java.astroenergyApplication.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astroenergy.java.astroenergyApplication.dao.UserRepository;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Country;
import com.astroenergy.java.astroenergyApplication.model.SearchAppointment;
import com.astroenergy.java.astroenergyApplication.model.User;
import com.astroenergy.java.astroenergyApplication.registration.OnAppointmentBookedEvent;
import com.astroenergy.java.astroenergyApplication.registration.OnRegistrationCompleteEvent;
import com.astroenergy.java.astroenergyApplication.security.UserService;
import com.astroenergy.java.astroenergyApplication.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
public class AppointmentController {
	@Autowired
	UserService userService;
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	@Autowired
	AppointmentService appointmentService;
	@DeleteMapping("/deleteAppointment/{id}")
	public ResponseEntity<?> deleteAppoinment(@PathVariable Long id) {
		try {
		Appointment a= appointmentService.deleteAppointment(id);
		 return new ResponseEntity<>(a, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
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
	
	 private String getAppUrl(HttpServletRequest request) {
	        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	    }
	@PostMapping("/saveAppointment")
	public ResponseEntity<?> saveAppointment(@RequestBody Appointment appointment,@RequestParam Long userId,final HttpServletRequest request) {
		try {
			if(userId==0) {
					User registered = userService.registerNewUserAccount(appointment);
					if(registered!=null) {
						eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request),true));
					}else
					{
						registered = userService.findUserByEmail(appointment.getEmail());
					}
				   userId=registered.getId();
				   eventPublisher.publishEvent(new OnAppointmentBookedEvent(appointment,registered));
				   Appointment a=appointmentService.addAppointment(appointment, userId);
				   return new ResponseEntity<>(a, HttpStatus.OK);
			}else {
				User user=userService.getUserByID(userId).get();
				eventPublisher.publishEvent(new OnAppointmentBookedEvent(appointment,user));
		Appointment savedAppointment= appointmentService.addAppointment(appointment, userId);
		 return new ResponseEntity<>(savedAppointment, HttpStatus.OK);}
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping("/appointmentViewed")
	public ResponseEntity<?> appointmentViewed(@RequestBody Long id,@RequestParam String viewed ) {
		try {
		Appointment savedAppointment= appointmentService.appointmentViewed(id,viewed );
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
	@GetMapping("/getUserAppointmentByStatus")
	public ResponseEntity<?> getUserAppointmentByStatus(@RequestParam Long userId,@RequestParam String status) {
		try {
			List<Appointment> c= appointmentService.getUserAppointmentByStatus(userId,status);
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
	public ResponseEntity<?> editAppointment(@RequestBody Appointment appointment,@RequestParam Long userId) {
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
	@GetMapping("/updateAppointmentDate")
	public ResponseEntity<?> updateAppointmentDate(@RequestParam Long id,@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
		try {
			Appointment a= appointmentService.updateAppointmentDate(id,date);
			 return new ResponseEntity<>(a, HttpStatus.OK);
		
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
