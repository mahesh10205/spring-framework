package com.advance.act.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.advance.act.dao.UserEntity;
import com.advance.act.dto.UserDto;
@Service
public interface UserService extends UserDetailsService{
	
	UserEntity saveUser(UserDto userDto);
	UserDto getUserByEmail(String email);

}
