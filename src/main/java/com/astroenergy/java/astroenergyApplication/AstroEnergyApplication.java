package com.astroenergy.java.astroenergyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.astroenergy.java.astroenergyApplication.dao.DayRepo;
import com.astroenergy.java.astroenergyApplication.dao.RoleRepository;
import com.astroenergy.java.astroenergyApplication.dao.UserProfileRepository;
import com.astroenergy.java.astroenergyApplication.dao.UserRepository;
import com.astroenergy.java.astroenergyApplication.model.Day;
import com.astroenergy.java.astroenergyApplication.model.Location;
import com.astroenergy.java.astroenergyApplication.model.Role;
import com.astroenergy.java.astroenergyApplication.model.User;
import com.astroenergy.java.astroenergyApplication.model.UserProfile;
import com.astroenergy.java.astroenergyApplication.validation.Name;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class AstroEnergyApplication {

	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	DayRepo dayRepo;
	
	@Autowired
	Name name;
	
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(AstroEnergyApplication.class, args);
	}
	
	@PostConstruct
	public void init()
	{
		ArrayList<String> dayString = new ArrayList<>();
		dayString.add("Monday");
		dayString.add("TuesDay");
		dayString.add("Wednesday");
		dayString.add("Thursday");
		dayString.add("Friday");
		dayString.add("Saturday");
		dayString.add("Sunday");
		
		for(String day : dayString)
		{
			
			Day savedDay = dayRepo.findByDay(day);
			if(savedDay==null)
			{
				Day d = new Day();
				d.setDay(day);
				dayRepo.save(d);
			}
		}
	}

	
	 @Bean
	 public WebMvcConfigurer corsConfigurer() 
	    {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**").allowedOrigins("*")
					.allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
	            }
	        };
	    }

}
