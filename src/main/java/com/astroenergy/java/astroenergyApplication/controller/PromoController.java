package com.astroenergy.java.astroenergyApplication.controller;

import java.util.List;

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
import com.astroenergy.java.astroenergyApplication.model.PromoCode;
import com.astroenergy.java.astroenergyApplication.service.PromoService;



@RestController
public class PromoController {
@Autowired
PromoService promoService;
@DeleteMapping("/deletePromo/{id}")
public ResponseEntity<?> deletePromo(@PathVariable int id) {
	try {
	PromoCode p= promoService.deletePromo(id);
	 return new ResponseEntity<>(p, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@RequestMapping("/addPromo")
public ResponseEntity<?> addPromo(@RequestBody PromoCode promo) {
	try {
      PromoCode p = promoService.addPromo(promo);
	 return new ResponseEntity<PromoCode>(p, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/editPromo")
public ResponseEntity<?> editPromo(@RequestBody PromoCode promo) {
	try {
      PromoCode p = promoService.editPromo(promo);
	 return new ResponseEntity<PromoCode>(p, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


@RequestMapping("/getPromo")
public ResponseEntity<?> getPromo() {
	try {
      List<PromoCode> p = promoService.getPromos();
	 return new ResponseEntity<List<PromoCode>>(p, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@RequestMapping("/getPromoDetail")
public ResponseEntity<?> getPromoDetail(@RequestParam int id) {
	try {
      PromoCode p = promoService.getPromoDetail(id);
	 return new ResponseEntity<PromoCode>(p, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}



}
