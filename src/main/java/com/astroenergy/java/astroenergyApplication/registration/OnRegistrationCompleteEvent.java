package com.astroenergy.java.astroenergyApplication.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.astroenergy.java.astroenergyApplication.model.User;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final User user;
    private final boolean appointmentUser;

    public OnRegistrationCompleteEvent(final User user, final Locale locale, final String appUrl,final boolean appointmentUser) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.appointmentUser=appointmentUser;
    }

    //

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }

	public boolean isAppointmentUser() {
		return appointmentUser;
	}
    

}
