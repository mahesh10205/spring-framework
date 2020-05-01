package com.advance.act.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.advance.act.secure.AuthenticationFilter;
import com.advance.act.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	UserService userService;
	
	Environment environment;
	
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Autowired
	public  WebSecurity(UserService userService,
			Environment environment,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.environment= environment;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
//		hasIpAddress(environment.getProperty("secure.ip"))
		http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("secure.ip"))
		.and().addFilter(getAuthenticationFilter());
		
		http.headers().frameOptions().disable();

	}


	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter= new AuthenticationFilter(userService, environment,authenticationManager());
		return authenticationFilter;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
		
	}

}
