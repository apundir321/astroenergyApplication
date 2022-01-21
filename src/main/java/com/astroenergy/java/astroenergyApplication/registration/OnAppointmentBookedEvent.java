package com.astroenergy.java.astroenergyApplication.registration;

import org.springframework.context.ApplicationEvent;

import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.User;

@SuppressWarnings("serial")
public class OnAppointmentBookedEvent extends ApplicationEvent  {
	private final Appointment appointment;
	private final User user;
	public Appointment getAppointment() {
		return appointment;
	}
	public User getUser() {
		return user;
	}
	public OnAppointmentBookedEvent( Appointment appointment, User user) {
		super(user);
		this.appointment = appointment;
		this.user = user;
	}
    
	

}
