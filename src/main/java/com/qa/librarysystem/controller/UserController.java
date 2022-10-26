package com.qa.librarysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.EmailAlreadyRegisteredException;
import com.qa.librarysystem.exceptions.UserAlreadyExistingExcecption;
import com.qa.librarysystem.exceptions.UserInvalidCredentialsException;
import com.qa.librarysystem.service.UserServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	@Autowired
	UserServiceImpl userService;

	ResponseEntity<?> responseEntity;
	

	@GetMapping("user/login/{username}/{pw}")
	public ResponseEntity<?> userLogin(@PathVariable("username") String username, @PathVariable("pw") String pw) throws UserInvalidCredentialsException {
		try {
			User user = this.userService.userLogin(username, pw);
			responseEntity = new ResponseEntity<>("successful login", HttpStatus.OK);
		}catch(UserInvalidCredentialsException e) {
			throw e;
			//redirect to register
		}catch(Exception e) {
			responseEntity = new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@PostMapping("user/register")
	public ResponseEntity<?> userRegister(@RequestBody User user) throws UserAlreadyExistingExcecption, EmailAlreadyRegisteredException{
		try {
			User addedUser = this.userService.userRegister(user);
			responseEntity = new ResponseEntity<>(addedUser,HttpStatus.CREATED);
		}catch(EmailAlreadyRegisteredException e) {
			throw e;
			//redirect to register for another attempt
		}catch(UserAlreadyExistingExcecption e) {
			throw e;
		}catch(Exception e) {
			responseEntity = new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
}
