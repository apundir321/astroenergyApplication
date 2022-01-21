package com.astroenergy.java.astroenergyApplication.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.registration.OnAppointmentBookedEvent;
import com.astroenergy.java.astroenergyApplication.registration.OnRegistrationCompleteEvent;
@Component
public class AppointmentListener implements ApplicationListener<OnAppointmentBookedEvent>{
	   @Autowired
	    private Environment env;
	   @Autowired
	    private JavaMailSender mailSender;
	 @Override
	    public void onApplicationEvent(final OnAppointmentBookedEvent event) {
		 final Appointment appointment=event.getAppointment();
	        final SimpleMailMessage email= constructAppointmentMail(event,appointment);
	        mailSender.send(email);
	        
	    }
	 final SimpleMailMessage constructAppointmentMail(final OnAppointmentBookedEvent event,final Appointment appointment) {
		 final String recipientAddress=event.getUser().getEmail();
		 final String adminAddress="akashhvashisht@gmail.com";
		 final String subject="Appointment Booked";
		 final String message="Appointment Booked for \r\n Name: "+appointment.getName()+
				               "\r\n Appointment date: "+appointment.getAppointDate()+
				               "\r\n Consultation type: "+appointment.getConsultationType()+
		                       "\r\n Number of sessions: "+appointment.getNoOfSessions()+
		                       "\r\n Meeting Type: "+appointment.getMeetingType()+
		                       "\r\n Amount:"+appointment.getAmount();
		 final SimpleMailMessage email=new SimpleMailMessage();
		 email.setTo(recipientAddress);
		 email.setCc(adminAddress);
		 email.setSubject(subject);
		 email.setFrom(env.getProperty("support.email"));
		 email.setText(message);
		 return email;
		 
	 }

	 
}
