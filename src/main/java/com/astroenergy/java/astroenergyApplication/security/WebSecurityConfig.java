package com.astroenergy.java.astroenergyApplication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.astroenergy.java.astroenergyApplication.jwt.JwtAuthenticationEntryPoint;
import com.astroenergy.java.astroenergyApplication.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable caching
        http.headers().cacheControl();

        http.cors().and().csrf().disable() // disable csrf for our requests.
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers(HttpMethod.POST,"/user/registration").permitAll()
        .antMatchers(HttpMethod.POST,"/login").permitAll()
        .antMatchers(HttpMethod.POST,"/order/**").permitAll()
        .antMatchers(HttpMethod.PUT,"/order/**").permitAll()
        .antMatchers(HttpMethod.GET,"/registrationConfirm").permitAll()
        .antMatchers(HttpMethod.GET,"/getProfilePic/**").permitAll()
        .antMatchers(HttpMethod.GET,"/getProfilePicByProfileId/**").permitAll()
        .antMatchers(HttpMethod.GET,"/console").permitAll()
        .antMatchers(HttpMethod.GET,"/getLocations").permitAll()
        .antMatchers(HttpMethod.GET,"/getBlogPic/**").permitAll()
        .antMatchers(HttpMethod.GET,"/getAllBlogs").permitAll()
        .antMatchers(HttpMethod.GET,"/getBlog").permitAll()
        .antMatchers(HttpMethod.GET,"/getBlogBySlug").permitAll()
        .antMatchers(HttpMethod.GET,"/getBlogByStatus").permitAll()
        .antMatchers(HttpMethod.GET,"/getUserByProfile/**").permitAll()
        .antMatchers(HttpMethod.POST,"/addComment").permitAll()
        .antMatchers(HttpMethod.POST,"/editComment").permitAll()
        .antMatchers(HttpMethod.GET,"/getCommentById").permitAll()
        .antMatchers(HttpMethod.GET,"/getBlogComments").permitAll()
        .antMatchers(HttpMethod.GET,"/getCommentReplies").permitAll()
        .antMatchers(HttpMethod.GET,"/getRateByCountry/**").permitAll()
        .antMatchers(HttpMethod.GET,"/getTimeSlotByDay/**").permitAll()
        .antMatchers(HttpMethod.GET,"/showSlots").permitAll()
        .antMatchers(HttpMethod.POST,"/AddEnquiry").permitAll()
        .antMatchers(HttpMethod.POST,"/saveAppointment").permitAll()
        .antMatchers(HttpMethod.POST,"/user/resetPassword").permitAll()
        .antMatchers(HttpMethod.POST,"/addFeedback").permitAll()
        .antMatchers("/registrationAccountConfirm").permitAll()
        
        .antMatchers("/badUser").permitAll()
        
        
        
        
        
        .anyRequest().authenticated()
        .and().
        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        // Create a default account
    	auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
    
    

}
