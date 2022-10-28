package com.qa.librarysystem.service;

import com.qa.librarysystem.entity.BookIssue;
import com.qa.librarysystem.exceptions.BookNotAvailableException;
import com.qa.librarysystem.exceptions.BookNotFoundException;
import com.qa.librarysystem.exceptions.UserAlreadyCheckedOutBookException;
import com.qa.librarysystem.exceptions.UserNotFoundException;

public interface BookIssureService {
	
	public BookIssue createBookIssue(int uid , int bid) throws BookNotAvailableException, UserAlreadyCheckedOutBookException, BookNotFoundException, UserNotFoundException;

	public BookIssue returnBook(BookIssue bookIssue) throws BookNotFoundException, UserNotFoundException;
	/*	
	public void favouriteBook();
	public void getFavouriteBooks();

	public void checkinCheckoutHistory();
	
	public void getCheckedOutBooks();
	public void checkOutBook();
	
	public void checkBookIsAvailable()
	*/
}
