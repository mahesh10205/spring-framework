package com.advance.act.zuulproxy.secure;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AutherizationFilter extends BasicAuthenticationFilter {
 
	Environment environment;

	public AutherizationFilter(AuthenticationManager authenticationManager, Environment environment) {
		super(authenticationManager);
		this.environment= environment;
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null || !(authorizationHeader.startsWith("Bearer"))) {
			filterChain.doFilter(request, response);
		}

		UsernamePasswordAuthenticationToken authToken = getAuthenticationToken(request);

		SecurityContextHolder.getContext().setAuthentication(authToken);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {

		String authorizationHeader = request.getHeader("Authorization");
		
		if(authorizationHeader == null) {
			return null;
			 
		}
		String token = authorizationHeader.replaceAll("Bearer", "");
		String userId = Jwts.parser().setSigningKey("sdfghjkloiuytrdfjklkjhgfdg").parseClaimsJws(token).getBody()
				.getSubject();
		if (userId == null) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
	}

}
