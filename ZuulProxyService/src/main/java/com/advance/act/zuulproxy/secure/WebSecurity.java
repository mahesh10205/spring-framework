package com.advance.act.zuulproxy.secure;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	Environment environment;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
         
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/user-ws/login").permitAll()
		.antMatchers(HttpMethod.POST, "/user-ws/users").permitAll()
		.antMatchers("/user-ws/h2-console/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilter(new AutherizationFilter(authenticationManager(), environment));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
}
