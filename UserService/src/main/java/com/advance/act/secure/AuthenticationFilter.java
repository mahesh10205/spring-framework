package com.advance.act.secure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.advance.act.dto.UserDto;
import com.advance.act.model.LoginReqModel;
import com.advance.act.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	UserService userService;
	
	Environment environment; 
	
	@Autowired
	public AuthenticationFilter(UserService userService,
			                    Environment environment,
			                    AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.environment= environment;
		super.setAuthenticationManager(authenticationManager);

	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException{
		
		LoginReqModel loginReqModel = null;
		try {
			loginReqModel = new  ObjectMapper().readValue(request.getInputStream(), LoginReqModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
				loginReqModel.getEmail(),
				loginReqModel.getPassword(),
				new ArrayList<>()));
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			                                HttpServletResponse response, 
			                                FilterChain chain, 
			                                Authentication authResult) throws IOException, ServletException{
		
		String userName= ((User)authResult.getPrincipal()).getUsername();
		
		UserDto userDto= userService.getUserByEmail(userName);
		
		String token = Jwts.builder().setSubject(userDto.getUserId()).
				            setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expire"))))
				            .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
				            .compact();
		
		response.setHeader("token", token);
		response.setHeader("UserId", userDto.getUserId());
				            
		
	}


}
