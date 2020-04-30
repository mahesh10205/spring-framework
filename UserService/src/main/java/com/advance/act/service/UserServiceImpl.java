package com.advance.act.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.advance.act.dao.UserDao;
import com.advance.act.dao.UserEntity;
import com.advance.act.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	UserDao userDao;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserEntity saveUser(UserDto userDto) {

		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
		UserEntity createdUserEntity=  userDao.save(userEntity);
		
		return createdUserEntity;

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity= userDao.findByEmail(username);
		
		if(userEntity == null) throw new UsernameNotFoundException(username);
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(), true, true, true, true , new ArrayList<>());
	}
	
	public UserDto getUserByEmail(String username) throws UsernameNotFoundException {
		UserEntity userEntity= userDao.findByEmail(username);
		
		if(userEntity == null) throw new UsernameNotFoundException(username);
		
		UserDto userDto= new ModelMapper().map(userEntity, UserDto.class);
		return userDto;
	}

}
