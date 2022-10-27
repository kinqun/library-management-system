package com.qa.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.EmailAlreadyRegisteredException;
import com.qa.librarysystem.exceptions.InvalidDateInputException;
import com.qa.librarysystem.exceptions.UserAlreadyExistingExcecption;
import com.qa.librarysystem.exceptions.UserInvalidCredentialsException;
import com.qa.librarysystem.exceptions.UserNotFoundException;
import com.qa.librarysystem.repository.UserRepository;
import com.qa.librarysystem.utils.Validator;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	@Mock
	Validator validator;
	
	@Mock
	UserRepository userRepo;
	
	@Autowired
	@InjectMocks
	UserServiceImpl userService;
	
	User user1;
	User user2;
	User user3;
	List<User> usersList;
	
	
	@BeforeEach
	public void setUp() {
		validator = new Validator();

		user1 = new User(1001,"Adam","Smith","user_adam", "mypassword1","1999-9-9", "userA@gmail.com" , true, new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		user2 = new User(1002,"Bob", "Joe","user_bob", "mypassword2","2010-5-20", "userB@gmail.com", false, new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		user3 = new User(1003,"Charlie", "Ricardo", "user_charlie", "mypassword3", "2001-12-30", "userC@gmail.com", false,  new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		usersList = Arrays.asList(user1,user2,user3);
		
	}
	
	@AfterEach
	public void tearDown() {
		user1 = user2 = user3 = null;
		usersList= null;
	}
	
	@Test
	@DisplayName("user-login-success-test")
	public void givenValidUsernameAndPassword_whenUserLogin_returnUser() throws UserAlreadyExistingExcecption, EmailAlreadyRegisteredException, InvalidDateInputException, UserInvalidCredentialsException {
		when(this.userRepo.verifyUserLogin(any(),any())).thenReturn(user1);
		User fetchedUser = this.userService.userLogin(any(),any());
		assertEquals("Adam",fetchedUser.getFname());
	}
	
	@Test
	@DisplayName("user-login-invalid-credentials-test")
	public void givenInvalidUsername_whenUserLogin_returnThrowsUserInvalidCredentialsException() throws UserInvalidCredentialsException {
		when(this.userRepo.verifyUserLogin(any(),any())).thenReturn(null);
		assertThrows(UserInvalidCredentialsException.class, ()->this.userService.userLogin(any(),any()));
	}
	
	@Test
	@DisplayName("user-register-test")
	public void givenValidUser_whenUserRegister_returnUser() throws DateTimeParseException, InvalidDateInputException, UserAlreadyExistingExcecption, EmailAlreadyRegisteredException {
		when(userService.validator.validateDate(any())).thenReturn(true);
		when(userRepo.findUserByEmail(any())).thenReturn(null);
		when(userRepo.findUserByUsername(any())).thenReturn(null);
		when(userRepo.save(any())).thenReturn(user1);
		User fetchedUser = userService.userRegister(user1);
		assertEquals("Adam", fetchedUser.getFname());
	}
	
	@Test
	@DisplayName("user-register-existing-email-test")
	public void givenUserWithExistingEmail_whenUserRegister_returnThrowEmailAlreadyRegisteredException() throws DateTimeParseException, InvalidDateInputException, UserAlreadyExistingExcecption, EmailAlreadyRegisteredException {
		when(userService.validator.validateDate(any())).thenReturn(true);
		when(userRepo.findUserByEmail(any())).thenReturn(user1);
		when(userRepo.findUserByUsername(any())).thenReturn(null);
		assertThrows(EmailAlreadyRegisteredException.class, ()->userService.userRegister(user1));
	}
	
	@Test
	@DisplayName("user-register-existing-username-test")
	public void givenUserWithExistingUsername_whenUserRegister_returnThrowUserAlreadyExistingException() throws DateTimeParseException, InvalidDateInputException, UserAlreadyExistingExcecption, EmailAlreadyRegisteredException {
		when(userService.validator.validateDate(any())).thenReturn(true);
		when(userRepo.findUserByEmail(any())).thenReturn(null);
		when(userRepo.findUserByUsername(any())).thenReturn(user1);
		assertThrows(UserAlreadyExistingExcecption.class, ()->userService.userRegister(user1));
	}
	
	@Test
	@DisplayName("user-register-invalid-date-test")
	public void givenUserWithInvalidDob_whenUserRegister_returnThrowInvalidDateInputException() throws DateTimeParseException, InvalidDateInputException, UserAlreadyExistingExcecption, EmailAlreadyRegisteredException {
		when(userService.validator.validateDate(any())).thenReturn(false);
		when(userRepo.findUserByEmail(any())).thenReturn(user1);
		when(userRepo.findUserByUsername(any())).thenReturn(user1);
		assertThrows(InvalidDateInputException.class, ()->userService.userRegister(user1));
	}
	
	@Test
	@DisplayName("update-user-test")
	public void givenValidUser_whenUpdateUser_returnUpdatedUser() throws UserNotFoundException {
		when(userRepo.findById(anyInt())).thenReturn(Optional.of(user1));
		when(userRepo.save(any())).thenReturn(user2);
		User updatedUser = userService.updateUser(user2);
		assertEquals("Bob", updatedUser.getFname());
	}
	
	@Test
	@DisplayName("update-non-existing-user-test")
	public void givenNonExistingUser_whenUpdateUser_returnThrowsUserNotFoundException() throws UserNotFoundException {
		when(userRepo.findById(anyInt())).thenReturn(null);
		assertThrows(UserNotFoundException.class, ()-> userService.updateUser(user1));
	}
	
	
	
}
