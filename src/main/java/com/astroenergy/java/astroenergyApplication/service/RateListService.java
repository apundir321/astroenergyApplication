package com.astroenergy.java.astroenergyApplication.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.RatelistRepo;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Ratelist;



@Service
public class RateListService {
@Autowired
RatelistRepo rateListRepo;
public Ratelist deleteRatelist(int id) throws Exception  {
	try {
		Ratelist r=rateListRepo.findBySnoAndDeletedAtIsNull(id);
		r.setDeletedAt(new Date());
		return rateListRepo.save(r);
	}
	catch(Exception e) {
		throw e;
	}
}
public int getRateByCountry(String country,String consult) {
	try {
		Ratelist r=rateListRepo.findByCountryNameAndConsultationTypeAndDeletedAtIsNull(country, consult);
		int rate=r.getRateOfValues();
		return rate;
	}
	catch(Exception e) {
		throw e;
	}
}

public Ratelist addRatelist(Ratelist rateList)throws Exception
{
	try {
		Ratelist r = rateListRepo.save(rateList);
		 return r;
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
}


public List<Ratelist> getAllRatelist()throws Exception
{
	try {
		return  rateListRepo.findByDeletedAtIsNullOrderBySnoDesc();
		
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
}

public Ratelist getRateListDetail(int id)throws Exception
{
	try {
		return  rateListRepo.findBySnoAndDeletedAtIsNull(id);
		
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
}
public Ratelist editRatelist(Ratelist rateList)throws Exception
{
	try {
		Ratelist r = rateListRepo.save(rateList);
		 return r;
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
}

}
