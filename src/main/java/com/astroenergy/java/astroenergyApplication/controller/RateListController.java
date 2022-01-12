package com.astroenergy.java.astroenergyApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astroenergy.java.astroenergyApplication.dao.RatelistRepo;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Ratelist;
import com.astroenergy.java.astroenergyApplication.service.RateListService;

@RestController
public class RateListController {
	@Autowired
	private RatelistRepo rateListRepo;
	@Autowired
	RateListService rateListService;
	@DeleteMapping("/deleteRatelist/{id}")
	public ResponseEntity<?> deleteRatelist(@PathVariable int id) {
		try {
		Ratelist r= rateListService.deleteRatelist(id);
		 return new ResponseEntity<>(r, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping("/addRateList")
	public ResponseEntity<?> addRateList(@RequestBody Ratelist rateList) {
		try {
			Ratelist r = rateListService.addRatelist(rateList);
			return new ResponseEntity<>(r, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/getAllRateList")
	public ResponseEntity<?> getAllRateList() {
		try {
			List<Ratelist> r = rateListService.getAllRatelist();
			return new ResponseEntity<>(r, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/EditRateList")
	public ResponseEntity<?> editRateList(@RequestBody Ratelist rateList) {
		try {
			// Ratelist r = rateListRepo.getById(rateList.getSno());
			// r.setCountryName(rateList.getCountryName());
			// r.setConsultationType(rateList.getConsultationType());
			// r.setRateOfValues(rateList.getRateOfValues());
			// r.setStatus(rateList.getStatus());
			Ratelist rt = rateListService.editRatelist(rateList);

			return new ResponseEntity<>(rt, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}


	
	@RequestMapping("/getRateListDetail/{id}")
	public ResponseEntity<?> getRateListDetail(@PathVariable int id) {
		try {
			// Ratelist r = rateListRepo.getById(rateList.getSno());
			// r.setCountryName(rateList.getCountryName());
			// r.setConsultationType(rateList.getConsultationType());
			// r.setRateOfValues(rateList.getRateOfValues());
			// r.setStatus(rateList.getStatus());
			Ratelist rt = rateListService.getRateListDetail(id);

			return new ResponseEntity<>(rt, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/getRateByCountry")
	public ResponseEntity<?> getRateByCountry(@RequestParam String country,@RequestParam String consult){
		try {
			int  rate=rateListService.getRateByCountry(country,consult);
			return new ResponseEntity<>(rate,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
