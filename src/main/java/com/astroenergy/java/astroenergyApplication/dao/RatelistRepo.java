package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astroenergy.java.astroenergyApplication.model.Ratelist;



public interface RatelistRepo extends JpaRepository<Ratelist,Integer> {
Ratelist findBySnoAndDeletedAtIsNull(int id);
List<Ratelist> findByDeletedAtIsNullOrderBySnoDesc();
Ratelist findByCountryNameAndConsultationTypeAndDeletedAtIsNull(String country,String consult);
}
