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
	/*
	 * getUser
	public void resetUser();
	*/
	
	/*
	public void addBook();
	public void updateBook();
	public void deleteBook();
	public void searchBook();
	public void showAllBooks();
	
	public void bookmarkBook();
	public void getBookmarkedBooks();
	public void favouriteBook();
	public void getFavouriteBooks();
	public void reserveBook();
	public void getReservedBooks();
	public void checkoutBook();
	public void returnBook();
	*/
}
