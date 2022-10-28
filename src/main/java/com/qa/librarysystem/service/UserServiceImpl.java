package com.qa.librarysystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.BookNotFoundException;
import com.qa.librarysystem.exceptions.EmailAlreadyRegisteredException;
import com.qa.librarysystem.exceptions.InvalidDateInputException;
import com.qa.librarysystem.exceptions.UserAlreadyExistingExcecption;
import com.qa.librarysystem.exceptions.UserInvalidCredentialsException;
import com.qa.librarysystem.exceptions.UserNotFoundException;
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

	@Override
	public User updateUser(User user) throws UserNotFoundException {
		Optional<User> existingUser = this.userRepo.findById(user.getUid());
		
		if(existingUser.isEmpty()) {
			throw new UserNotFoundException();
		}else {
			return this.userRepo.save(user);
		}
		
	}

	@Override
	public boolean deleteUser(int uid) throws UserNotFoundException {
		boolean isDeleted = false;
		Optional<User> existingUser = this.userRepo.findById(uid);
		
		if(!existingUser.isPresent()) {
			throw new UserNotFoundException();
		}else {
			this.userRepo.deleteById(uid);
			isDeleted = true;
		}
		return isDeleted;
		
	}

	@Override
	public ArrayList<Book> getUsersFavouriteBooks(int id) throws UserNotFoundException {
		ArrayList<Integer> usersFavBooksIds = null;
		try {
			usersFavBooksIds = this.userRepo.findById(id).stream().map(u->u.getFavouriteBooks()).collect(Collectors.toList()).get(0);
		}catch(Exception e) {
			throw new UserNotFoundException();
		}
		ArrayList<Book> usersFavBooks = new ArrayList<>();
		for(Integer n : usersFavBooksIds) {
			Book currentBook = this.bookRepo.findById(n).get();
			usersFavBooks.add(currentBook);
		}
		return usersFavBooks;
	}

	@Override
	public User addFavouriteBook(int bid, int uid) throws BookNotFoundException, UserNotFoundException {
		Optional<User> existingUser = this.userRepo.findById(uid);
		Optional<Book> existingBook = this.bookRepo.findById(bid);
		
		if(existingBook.isEmpty()) {
			throw new BookNotFoundException(); 
		}
		if(existingUser.isEmpty()) {
			throw new UserNotFoundException();
		}else {
			User user = existingUser.get();
			ArrayList<Integer> userFavBooks = user.getFavouriteBooks();
			if(!userFavBooks.contains(bid)) {
				userFavBooks.add(bid);
			}
			user.setFavouriteBooks(userFavBooks);
			return this.userRepo.save(user);
		}
		
	}

}
