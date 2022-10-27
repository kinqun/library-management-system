package com.qa.librarysystem.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.qa.librarysystem.entity.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class UserRepositoryTest {
	@Autowired
	UserRepository userRepo;
	
	User user1;
	User user2;
	User user3;
	List<User> usersList;
	
	
	@BeforeEach
	public void setUp() {

		user1 = new User(1001,"Adam","Smith","user_adam", "mypassword1","1999-9-9", "userA@gmail.com" , true, new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		user2 = new User(1002,"Bob", "Joe","user_bob", "mypassword2","2010-5-20", "userB@gmail.com", false, new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		user3 = new User(1003,"Charlie", "Ricardo", "user_charlie", "mypassword3", "2001-12-30", "userC@gmail.com", false,  new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
		usersList = Arrays.asList(user1,user2,user3);
		
	}
	
	@AfterEach
	public void tearDown() {
		user1 = user2 = user3 = null;
		usersList= null;
		userRepo.deleteAll();
	}
	
	@Test
	@DisplayName("verify-username-and-password-test")
	public void givenValidUsernameAndPassword_whenVerfiyUserLogin_returnUser() {
		this.userRepo.save(user1);
		Optional<User> fetchedUser = Optional.ofNullable(this.userRepo.verifyUserLogin("user_adam", "mypassword1"));
		assertEquals("Adam", fetchedUser.get().getFname());
	}
	
	@Test
	@DisplayName("verify-invalid-username-test")
	public void givenInvalidUsername_whenVerifyUserLogin_returnNoRecords() {
		this.userRepo.save(user1);
		Optional<User> fetchedUser = Optional.ofNullable(this.userRepo.verifyUserLogin("invalidUser", "mypassword1"));

		assertThat(fetchedUser).isEmpty();
	}
	
	@Test
	@DisplayName("verify-invalid-password-test")
	public void givenInvalidPassword_whenVerifyUserLogin_returnNoRecords() {
		this.userRepo.save(user1);
		Optional<User> fetchedUser = Optional.ofNullable(this.userRepo.verifyUserLogin("user_adam", "invalidpassword!"));

		assertThat(fetchedUser).isEmpty();
	}
	
	@Test
	@DisplayName("get-user-by-email-test")
	public void givenValidEmail_whenFindUserByEmail_returnUser() {
		this.userRepo.save(user1);
		User fetchedUser = this.userRepo.findUserByEmail("userA@gmail.com");

		assertNotNull(fetchedUser);
		assertEquals("Adam", fetchedUser.getFname());
	}
	
	@Test
	@DisplayName("get-user-by-invalid-email-test")
	public void givenInvalidEmail_whenFindUserByEmail_returnNoRecord() {
		this.userRepo.save(user1);
		User fetchedUser = this.userRepo.findUserByEmail("invalid@gmail.com");

		assertNull(fetchedUser);
	}
	
	@Test
	@DisplayName("get-user-by-username-test")
	public void givenValidUsername_whenFindUserByUsername_returnUser() {
		this.userRepo.save(user1);
		User fetchedUser = this.userRepo.findUserByUsername("user_adam");

		assertNotNull(fetchedUser);
		assertEquals("Adam", fetchedUser.getFname());
	}
	
	@Test
	@DisplayName("get-user-by-invalid-username-test")
	public void givenInvalidUsername_whenFindUserByUsername_returnUser() {
		this.userRepo.save(user1);
		User fetchedUser = this.userRepo.findUserByUsername("invalid_user_adam");

		assertNull(fetchedUser);
	}
	
	@Test
	@DisplayName("register-user-test")
	public void givenValidUser_whenSaveUser_returnUser() {
		User savedUser = this.userRepo.save(user1);
		assertNotNull(savedUser);
		assertEquals("Adam", savedUser.getFname());
		
	}
	
	@Test
	@DisplayName("get-all-users-test")
	public void given_whenGetAllUsers_returnUsersList() {
		userRepo.save(user1);
		userRepo.save(user2);
		userRepo.save(user3);
		List<User> allUsers = userRepo.findAll();
		
		assertEquals(3, allUsers.size());
		assertEquals("Adam", allUsers.get(0).getFname());
	}
	
	@Test
	@DisplayName("find-by-user-id-test")
	public void givenValidId_whenFindUserById_returnUser() {
		userRepo.save(user1);
		Optional<User> fetchedUser = userRepo.findById(user1.getUid());
		assertThat(fetchedUser).isNotEmpty();
		assertEquals("Adam", fetchedUser.get().getFname());
	}
	
	@Test
	@DisplayName("find-by-user-invalid-id-test")
	public void givenInValidId_whenFindUserById_returnEmpty() {
		Optional<User> fetchedUser = userRepo.findById(user1.getUid());
		assertThat(fetchedUser).isEmpty();
	}
		
}
