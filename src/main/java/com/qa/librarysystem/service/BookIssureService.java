package com.qa.librarysystem.service;

import java.util.List;

import com.qa.librarysystem.entity.BookIssue;
import com.qa.librarysystem.exceptions.BookIssueNotFoundException;
import com.qa.librarysystem.exceptions.BookNotAvailableException;
import com.qa.librarysystem.exceptions.BookNotFoundException;
import com.qa.librarysystem.exceptions.UserAlreadyCheckedOutBookException;
import com.qa.librarysystem.exceptions.UserNotFoundException;

public interface BookIssureService {
	
	public BookIssue createBookIssue(int uid , int bid) throws BookNotAvailableException, UserAlreadyCheckedOutBookException, BookNotFoundException, UserNotFoundException;
	public boolean deleteBookIssue(int id) throws BookIssueNotFoundException;
	public BookIssue returnBook(BookIssue bookIssue) throws BookNotFoundException, UserNotFoundException;
	public BookIssue updateBookIssue(BookIssue bookIssue) throws BookIssueNotFoundException;
	public BookIssue getBookIssueById(int id) throws BookIssueNotFoundException;
	
	public List<BookIssue> getAllBookIssues();
	public List<BookIssue> getUserCheckedOutBooks(int id);
}
