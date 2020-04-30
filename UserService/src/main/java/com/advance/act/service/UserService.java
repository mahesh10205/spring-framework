package com.advance.act.service;

import org.springframework.stereotype.Service;

import com.advance.act.dao.UserEntity;
import com.advance.act.dto.UserDto;
@Service
public interface UserService {
	
	UserEntity saveUser(UserDto userDto);

}
