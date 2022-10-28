package com.qa.librarysystem.service;

import java.util.List;

import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.EmailAlreadyRegisteredException;
import com.qa.librarysystem.exceptions.InvalidDateInputException;
import com.qa.librarysystem.exceptions.UserAlreadyExistingExcecption;
import com.qa.librarysystem.exceptions.UserInvalidCredentialsException;
import com.qa.librarysystem.exceptions.UserNotFoundException;

public interface UserService {
	//login/auths
	public User userLogin(String username, String pw) throws UserInvalidCredentialsException;
	public User userRegister(User user) throws UserAlreadyExistingExcecption, EmailAlreadyRegisteredException, InvalidDateInputException;
	
	public List<User> getAllUsers();
	public User updateUser(User user) throws UserNotFoundException;
	public boolean deleteUser(int uid) throws UserNotFoundException;
	
	/*	
	public void favouriteBook();
	public void getFavouriteBooks();

	public void checkinCheckoutHistory();
	
	public void getCheckedOutBooks();
	public void returnBook();
	public void checkOutBook();
	
	public void checkBookIsAvailable()
	*/
}
