package com.astroenergy.java.astroenergyApplication.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.HolidayRepo;
import com.astroenergy.java.astroenergyApplication.dao.TimeSlotRepo;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Day;
import com.astroenergy.java.astroenergyApplication.model.Holiday;
import com.astroenergy.java.astroenergyApplication.model.TimeSlot;

@Service
public class HolidayService {

	@Autowired
	private HolidayRepo holidayRepo;

	@Autowired
	private TimeSlotRepo timeSlotRepo;

	public Holiday deleteHoliday(Long id) throws Exception {
		try {
			Holiday h = holidayRepo.findByIdAndDeletedAtIsNull(id);
			h.setDeletedAt(new Date());
			return holidayRepo.save(h);
		} catch (Exception e) {
			throw e;
		}
	}

	public Holiday saveHoliday(Holiday holiday) throws Exception {
		try {

			return holidayRepo.save(holiday);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

	}

	public Holiday editHoliday(Holiday holiday) throws Exception {
		try {

			
			Set<TimeSlot> savedTimeSlots = new HashSet<>();
			for (TimeSlot t : holiday.getTimeSlots()) {
				TimeSlot savedTimeSlot = timeSlotRepo.findById(t.getId()).get();
				savedTimeSlots.add(savedTimeSlot);
			}
			holiday.setTimeSlots(savedTimeSlots);
			return holidayRepo.save(holiday);

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

	}

	public Holiday findHoliday(long id) throws Exception {
		try {
			return holidayRepo.findByIdAndDeletedAtIsNull(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

	}

	public List<Holiday> getHolidays() throws Exception {
		try {
			return (List<Holiday>) holidayRepo.findByDeletedAtIsNullOrderByIdDesc();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

	}

	public Holiday addTimeSlotsToHoliday(Set<TimeSlot> timeSlots, long id) throws Exception {
		Holiday holiday = null;
		try {
			holiday = holidayRepo.findByIdAndDeletedAtIsNull(id);

			Holiday savedHoliday = holiday;
			Set<TimeSlot> savedTimeSlots = new HashSet<>();
			for (TimeSlot t : timeSlots) {
				TimeSlot savedTimeSlot = timeSlotRepo.findById(t.getId()).get();
				savedTimeSlots.add(savedTimeSlot);
			}
			savedHoliday.setTimeSlots(savedTimeSlots);
			return holidayRepo.save(savedHoliday);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

	}

	public Holiday getHolidayDetail(long id) throws Exception {
		// TODO Auto-generated method stub

		try {
			Holiday holiday = holidayRepo.findByIdAndDeletedAtIsNull(id);
			return holiday;

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

}
