package com.advance.act.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advance.act.dao.UserEntity;
import com.advance.act.dto.UserDto;
import com.advance.act.model.UserModel;
import com.advance.act.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping("/status/check")
	public String getStatus() {

		return "I'm in user service";
	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
	             produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserEntity> createUser(@RequestBody UserModel userModel) {
		
		ModelMapper modelMapper= new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto= modelMapper.map(userModel, UserDto.class);
		
		UserEntity createdUserEnitity= userService.saveUser(userDto);
		
		return  ResponseEntity.status(HttpStatus.OK).body(createdUserEnitity);
		
	}
}
