package com.qa.librarysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.EmailAlreadyRegisteredException;
import com.qa.librarysystem.exceptions.InvalidDateInputException;
import com.qa.librarysystem.exceptions.UserAlreadyExistingExcecption;
import com.qa.librarysystem.exceptions.UserInvalidCredentialsException;
import com.qa.librarysystem.service.UserServiceImpl;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	@Mock
	UserServiceImpl userService;
	
	@Autowired
	@InjectMocks
	UserController userController;
	
	@Autowired
	MockMvc mockMvc;
	
	User user1;
	User user2;
	User user3;
	List<User> usersList;
	
	
	@BeforeEach
	public void setUp() {

		user1 = new User(1001,"Adam","Smith","user_adam", "mypassword1","1999-9-9", "userA@gmail.com" , true, new ArrayList<>() ,new ArrayList<>(),new ArrayList<>()) ;
		user2 = new User(1002,"Bob", "Joe","user_bob", "mypassword2","2010-5-20", "userB@gmail.com", false, new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		user3 = new User(1003,"Charlie", "Ricardo", "user_charlie", "mypassword3", "2001-12-30", "userC@gmail.com", false,  new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		usersList = Arrays.asList(user1,user2,user3);
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@AfterEach
	public void tearDown() {
		user1 = user2 = user3 = null;
		usersList= null;
	}
	
	@Test
	@DisplayName("user-login-test")
	public void givenValidUsernameAndPassword_whenUserLogin_returnUserAsJSON() throws Exception {
		when(userService.userLogin(any(),any())).thenReturn(user1);
		mockMvc.perform(get("/api/v1/user/login/{username}/{pw}","user_adam","mypassword1").accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.fname").value("Adam"));
	}
	
	@Test
	@DisplayName("user-login-invalid-username-test")
	public void givenInvalidUsername_whenUserLogin_returnThrowUserAlreadyExistingException() throws Exception {
		when(userService.userLogin(any(), any())).thenThrow(new UserInvalidCredentialsException());
		mockMvc.perform(get("/api/v1/user/login/{username}/{pw}","invalid_username!","mypassword1").accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isBadRequest())
			.andExpect(res->assertEquals("incorrect username/password", res.getResponse().getErrorMessage()))
			.andExpect(res->assertTrue(res.getResolvedException() instanceof UserInvalidCredentialsException));
		
	}
	
	@Test
	@DisplayName("user-login-invalid-pw-test")
	public void givenInvalidPassword_whenUserLogin_returnThrowInvalidUserInvalidCredentialsException() throws Exception {
		when(userService.userLogin(any(), any())).thenThrow(new UserInvalidCredentialsException());
		mockMvc.perform(get("/api/v1/user/login/{username}/{pw}","user_adam","invalidpassword!").accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isBadRequest())
			.andExpect(res->assertEquals("incorrect username/password", res.getResponse().getErrorMessage()))
			.andExpect(res->assertTrue(res.getResolvedException() instanceof UserInvalidCredentialsException));
	}
	
	@Test
	@DisplayName("user-register-test")
	public void givenValidUser_whenUserRegister_returnUserAAsJSON() throws Exception {
		when(userService.userRegister(any())).thenReturn(user1);
		mockMvc.perform(post("/api/v1/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(user1)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.fname").value("Adam"));
	}
	
	@Test
	@DisplayName("user-register-existing-email-test")
	public void givenExistingEmail_whenUserRegister_returnThrowEmailAlreadyRegisteredException() throws Exception {
		when(userService.userRegister(any())).thenThrow(new EmailAlreadyRegisteredException());
		mockMvc.perform(post("/api/v1/user/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(user1)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isConflict())
				.andExpect(res->assertEquals("Email is aleady registered",res.getResponse().getErrorMessage()))
				.andExpect(res->assertTrue(res.getResolvedException() instanceof EmailAlreadyRegisteredException));
	}
	
	@Test
	@DisplayName("user-register-existing-username-test")
	public void givenExistingUsername_whenUserRegister_returnThrowUserAlreadyExistingException() throws Exception {
		when(userService.userRegister(any())).thenThrow(new UserAlreadyExistingExcecption());
		mockMvc.perform(post("/api/v1/user/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(user1)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isConflict())
				.andExpect(res->assertEquals("User already exists",res.getResponse().getErrorMessage()))
				.andExpect(res->assertTrue(res.getResolvedException() instanceof UserAlreadyExistingExcecption));
	}
	
	@Test
	@DisplayName("user-register-invalid-date-test")
	public void givenInvalidDate_whenUserRegister_returnThrowInvalidDateInputException() throws Exception {
		when(userService.userRegister(any())).thenThrow(new InvalidDateInputException());
		mockMvc.perform(post("/api/v1/user/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(user1)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isBadRequest())
				.andExpect(res->assertEquals("invalid date input",res.getResponse().getErrorMessage()))
				.andExpect(res->assertTrue(res.getResolvedException() instanceof InvalidDateInputException));
	}
	
	@Test
	@DisplayName("get-all-users-test")
	public void given_whenGetAllUsers_returnUsersList() throws Exception {
		when(userService.getAllUsers()).thenReturn(usersList);
		mockMvc.perform(get("/api/v1/user").accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].fname").value("Adam"));
		
	}
	
	
	
	public static String asJsonString(Object obj) {
		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = null;
		
		try {
			jsonStr = Obj.writeValueAsString(obj);
			System.out.println(jsonStr);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
}
