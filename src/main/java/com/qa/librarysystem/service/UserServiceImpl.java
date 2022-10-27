package com.qa.librarysystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.EmailAlreadyRegisteredException;
import com.qa.librarysystem.exceptions.InvalidDateInputException;
import com.qa.librarysystem.exceptions.UserAlreadyExistingExcecption;
import com.qa.librarysystem.exceptions.UserInvalidCredentialsException;
import com.qa.librarysystem.repository.BookRepository;
import com.qa.librarysystem.repository.UserRepository;
import com.qa.librarysystem.utils.Validator;

@Service
public class UserServiceImpl implements UserService {
	Validator validator = new Validator();

	@Autowired
	BookRepository bookRepo;

	@Autowired
	UserRepository userRepo;
	
	@Override
	public User userLogin(String username, String pw) throws UserInvalidCredentialsException {
		
		Optional<User> existingUser = Optional.ofNullable(this.userRepo.verifyUserLogin(username, pw));
		if(existingUser.isEmpty()) {
			throw new UserInvalidCredentialsException();
		}else {
			return existingUser.get();
		}
		
		
	}

	@Override
	public User userRegister(User user) throws UserAlreadyExistingExcecption, EmailAlreadyRegisteredException, InvalidDateInputException {
		Optional<User> existingEmail = Optional.ofNullable(this.userRepo.findUserByEmail(user.getEmail()));
		Optional<User> existingUsername = Optional.ofNullable(this.userRepo.findUserByUsername(user.getUsername()));
		boolean validDate = validator.validateDate(user.getDob());

		if(!validDate) {
			throw new InvalidDateInputException();
		}
		
		if(existingEmail.isPresent()) {
			throw new EmailAlreadyRegisteredException();
		}else if(existingUsername.isPresent()) {
			throw new UserAlreadyExistingExcecption();
		}else {
			return this.userRepo.save(user);
		}
	}

	@Override
	public List<User> getAllUsers() {
		return this.userRepo.findAll();
	}

}
