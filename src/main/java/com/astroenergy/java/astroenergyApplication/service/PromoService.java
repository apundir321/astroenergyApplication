package com.astroenergy.java.astroenergyApplication.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.PromoRepo;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.PromoCode;



@Service
public class PromoService {
	@Autowired
	PromoRepo promoRepo;
	public PromoCode addPromo(PromoCode promo)throws Exception
	{
		try {
			PromoCode p = promoRepo.save(promo);
			 return p;
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
	}
	public PromoCode deletePromo(int id) throws Exception  {
		try {
			PromoCode p=promoRepo.findBySnoAndDeletedAtIsNull(id);
			p.setDeletedAt(new Date());
			return promoRepo.save(p);
		}
		catch(Exception e) {
			throw e;
		}
	}

	public PromoCode editPromo(PromoCode promo)throws Exception
	{
		try {
			
			PromoCode p = promoRepo.save(promo);
			 return p;
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
	}

	
	public List<PromoCode> getPromos()throws Exception
	{
		try {
			return promoRepo.findByDeletedAtIsNullOrderBySnoDesc();
			 
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
	}
	
	public PromoCode getPromoDetail(int id)throws Exception
	{
		try {
			return promoRepo.findBySnoAndDeletedAtIsNull(id);
			 
			}catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
	}
}
