package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astroenergy.java.astroenergyApplication.model.Country;



public interface CountryRepo extends JpaRepository<Country,Integer>{
Country findBySnoAndDeletedAtIsNull(int id);
List<Country> findByDeletedAtIsNullOrderBySnoDesc();

}
