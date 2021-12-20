package com.astroenergy.java.astroenergyApplication.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.CountryRepo;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Country;



@Service
public class CountryServce {
	
	@Autowired
	CountryRepo countryRepo;
	public Country deleteCountry(int id) throws Exception  {
		try {
			Country a=countryRepo.findBySnoAndDeletedAtIsNull(id);
			a.setDeletedAt(new Date());
			return countryRepo.save(a);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public Country addCountry(Country country)throws Exception
	{
		try {
			Country c = countryRepo.save(country);
			 return c;
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
	}
	
	public List<Country> getAll()throws Exception
	{
		try {
			
			 return countryRepo.findByDeletedAtIsNullOrderBySnoDesc();
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
	}
	public Country updateCountry(Country country)throws Exception
	{
		try {
			Country c = countryRepo.save(country);
			 return c;
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
	}

	
	public Country getCountryDetail(int id)throws Exception
	{
		try {
			
			 return countryRepo.findBySnoAndDeletedAtIsNull(id);
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
	}

}
