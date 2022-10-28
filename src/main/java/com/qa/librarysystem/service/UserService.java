package com.qa.librarysystem.service;

import java.util.List;

import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.BookNotFoundException;
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
	public List<Book> getUsersFavouriteBooks(int id) throws UserNotFoundException;
	public User addFavouriteBook(int bid, int uid) throws BookNotFoundException, UserNotFoundException;
}
