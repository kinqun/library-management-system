package com.qa.librarysystem.service;

import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.EmailAlreadyRegisteredException;
import com.qa.librarysystem.exceptions.UserAlreadyExistingExcecption;
import com.qa.librarysystem.exceptions.UserInvalidCredentialsException;
import com.qa.librarysystem.exceptions.UserNotFoundException;

public interface UserService {
	//login/auths
	public User userLogin(String username, String pw) throws UserInvalidCredentialsException;
	public User userRegister(User user) throws UserAlreadyExistingExcecption, EmailAlreadyRegisteredException;
	/*
	public void getUser();
	public void deleteUser();
	public void updateUser();
	public void resetUser();
	public void showAllUsers();
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
