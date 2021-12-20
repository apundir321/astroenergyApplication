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

import com.astroenergy.java.astroenergyApplication.dao.CountryRepo;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Country;
import com.astroenergy.java.astroenergyApplication.service.CountryServce;





@RestController
public class CountryController {
@Autowired
private CountryRepo countryRepo;
	@Autowired
	CountryServce countryService;

	@DeleteMapping("/deleteCountry/{id}")
	public ResponseEntity<?> deleteCountry(@PathVariable int id) {
		try {
		Country c= countryService.deleteCountry(id);
		 return new ResponseEntity<>(c, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
@RequestMapping("/AddCountry")
public ResponseEntity<?> addCountry(@RequestBody Country country) {
	try {
	Country c = countryService.addCountry(country);
	 return new ResponseEntity<Country>(c, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@GetMapping("/AllCountries")
public ResponseEntity<?> allCountry() {
	try {
	List<Country> c= countryService.getAll();
	 return new ResponseEntity<>(c, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@GetMapping("/getCountryDetail")
public ResponseEntity<?> allCountry(@RequestParam int id) {
	try {
	Country c= countryService.getCountryDetail(id);
	 return new ResponseEntity<>(c, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@RequestMapping("/UpdateCountry")
public ResponseEntity<?> updateCountry(@RequestBody Country country) {
	try {
	//Country c = countryRepo.getById(country.getSno());
	//c.setCode(country.getCode());
	//c.setName(country.getName());
//	c.setRemarks(country.getRemarks());
	//c.setStatus(country.getStatus());
	Country c=countryService.updateCountry(country);
	 return new ResponseEntity<>(c, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


}
