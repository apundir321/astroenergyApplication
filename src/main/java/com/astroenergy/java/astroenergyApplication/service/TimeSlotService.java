package com.astroenergy.java.astroenergyApplication.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.DayRepo;
import com.astroenergy.java.astroenergyApplication.dao.TimeSlotRepo;
import com.astroenergy.java.astroenergyApplication.model.Day;
import com.astroenergy.java.astroenergyApplication.model.TimeSlot;

@Service
public class TimeSlotService {
	
	@Autowired
	TimeSlotRepo timeSlotRepo;
	
	@Autowired
	DayRepo dayRepo;
	
	
	public TimeSlot saveTimeSlot(TimeSlot timeSlot)throws Exception
	{
		try {
			Set<Day> savedDays = new HashSet<>();
			for(Day d : timeSlot.getDays())
			{
				Day savedDay = dayRepo.findByDay(d.getDay());
				savedDays.add(savedDay);
			}
			
			timeSlot.setDays(savedDays);
			return timeSlotRepo.save(timeSlot);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	
	public TimeSlot getTimeSlotDetail(long id) throws Exception
	{
		Optional<TimeSlot> timeSlot = null;
		try {
			timeSlot = timeSlotRepo.findById(id);
			if(timeSlot.isPresent())
			{
				return timeSlot.get();
			}else
			{
				throw new Exception("Time slot not found");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public TimeSlot updateTimeSlot(TimeSlot timeSlot)throws Exception
	{
		try {
			return timeSlotRepo.save(timeSlot);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	
	public TimeSlot addDaysToTimeSlot(Set<Day> days,long id) throws Exception
	{
		Optional<TimeSlot> timeSlot = null;
		try {
			timeSlot = timeSlotRepo.findById(id);
			if(timeSlot.isPresent())
			{
				Set<Day> savedDays = new HashSet<>();
				for(Day d : days)
				{
					Day savedDay = dayRepo.findByDay(d.getDay());
					savedDays.add(savedDay);
				}
				TimeSlot slot =  timeSlot.get();
				slot.setDays(savedDays);
				return timeSlotRepo.save(slot);
			}else
			{
				throw new Exception("Time slot not found");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
	}
	
	public List<TimeSlot> getSlots()
	{
		return (List<TimeSlot>) timeSlotRepo.findAll();
	}
	
	
	public List<Day> getDays()
	{
		return (List<Day>) dayRepo.findAll();
	}

	public List<TimeSlot> getSlotsByDay(Date date){
		try {
			String dayWeek = new SimpleDateFormat("EEEE").format(date);
			List<TimeSlot> t=timeSlotRepo.findByDay(dayWeek);
			return t;
			
		}
		catch(Exception e) {
			throw e;
		}
	}
}
