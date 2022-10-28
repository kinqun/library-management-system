package com.qa.librarysystem.service;

import com.qa.librarysystem.entity.BookIssue;
import com.qa.librarysystem.exceptions.BookNotAvailableException;
import com.qa.librarysystem.exceptions.UserAlreadyCheckedOutBookException;

public interface BookIssureService {
	
	public BookIssue createBookIssue(int uid , int bid) throws BookNotAvailableException, UserAlreadyCheckedOutBookException;
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
