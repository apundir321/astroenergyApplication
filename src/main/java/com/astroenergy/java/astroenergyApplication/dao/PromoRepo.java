package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astroenergy.java.astroenergyApplication.model.PromoCode;



public interface PromoRepo extends JpaRepository<PromoCode,Integer> {
List<PromoCode> findByDeletedAtIsNullOrderBySnoDesc();
PromoCode findBySnoAndDeletedAtIsNull(int id);
}
