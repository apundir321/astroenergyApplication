package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astroenergy.java.astroenergyApplication.model.Feedback;

public interface FeedbackRepo extends JpaRepository<Feedback,Integer> {
public List<Feedback> findByFirstNameAndDeletedAtIsNull(String name );
List<Feedback> findByDeletedAtIsNullOrderBySnoDesc();
Feedback findBySnoAndDeletedAtIsNull(int id);
}
