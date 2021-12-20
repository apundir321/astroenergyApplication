package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.astroenergy.java.astroenergyApplication.model.TimeSlot;
import com.astroenergy.java.astroenergyApplication.model.UserProfile;

public interface TimeSlotRepo  extends CrudRepository<TimeSlot, Long> {
	@Query(value="SELECT t FROM TimeSlot t JOIN t.days d where d.day=:dayWeek")
List<TimeSlot> findByDay(@Param("dayWeek") String dayWeek);
	List<TimeSlot> findByDeletedAtIsNullOrderByIdDesc();
	TimeSlot findByIdAndDeletedAtIsNull(Long id);
}
