package com.astroenergy.java.astroenergyApplication.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Day;
import com.astroenergy.java.astroenergyApplication.model.TimeSlot;
import com.astroenergy.java.astroenergyApplication.service.TimeSlotService;

@RestController
public class TimeSlotController {
	
	@Autowired
	private TimeSlotService slotService;
	
	@DeleteMapping("/deleteTimeslot/{id}")
	public ResponseEntity<?> deleteTimeSlot(@PathVariable Long id) {
		try {
		TimeSlot t= slotService.deleteTimeSlot(id);
		 return new ResponseEntity<>(t, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/saveTimeSlot")
	public ResponseEntity<?> saveTimeSlot(@RequestBody TimeSlot timeSlot)
	{
		try {
			TimeSlot savedSlot = slotService.saveTimeSlot(timeSlot);
			return new ResponseEntity<TimeSlot>(savedSlot, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>("Error in saving time slot", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	@PostMapping("/editTimeSlot")
	public ResponseEntity<?> editTimeSlot(@RequestBody TimeSlot timeSlot)
	{
		try {
			TimeSlot savedSlot = slotService.updateTimeSlot(timeSlot);
			return new ResponseEntity<TimeSlot>(savedSlot, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>("Error in saving time slot", HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
	}
	
	@GetMapping("/showSlots")
	public ResponseEntity<?> showTimeSlot()
	{
		try {
			List<TimeSlot> slots = slotService.getSlots();
			return new ResponseEntity<List<TimeSlot>>(slots, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>("Error in saving time slot", HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
	}
	
	
	@PostMapping("/addDays")
	public ResponseEntity<?> addDay(@RequestParam long id,@RequestBody Set<Day> days)
	{
		try {
			TimeSlot slot = slotService.addDaysToTimeSlot(days,id);
			return new ResponseEntity<TimeSlot>(slot, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>("Error in adding day", HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
	}
	
	@GetMapping("/getDays")
	public ResponseEntity<?> getDays()
	{
		try {
			List<Day> slots = slotService.getDays();
			return new ResponseEntity<List<Day>>(slots, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>("Error in getting days", HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
	}
	
	@GetMapping("/getTimeSlotDetail")
	public ResponseEntity<?> getTimeSlotDetail(@RequestParam long id)
	{
		try {
			TimeSlot slot = slotService.getTimeSlotDetail(id);
			return new ResponseEntity<TimeSlot>(slot, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>("Error in getting time slot", HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
	}
	
	@GetMapping("/getTimeSlotByDay")
	public ResponseEntity<?> getSlotstByDay(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd")Date date)
	{
		try {
			List<TimeSlot> slot = slotService.getSlotsByDay(date);
			return new ResponseEntity<>(slot, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>("Error in getting time slot", HttpStatus.INTERNAL_SERVER_ERROR);	
		}	
	}
	

}
