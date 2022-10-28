package com.qa.librarysystem.service;

import java.util.List;

import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.entity.BookIssue;
import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.BookAlreadyExistsException;
import com.qa.librarysystem.exceptions.BookNotFoundException;

public interface BookService {

	public Book addBook(Book book) throws BookAlreadyExistsException;
	public Book updateBook(Book book) throws BookNotFoundException;
	public boolean deleteBook(int id) throws BookNotFoundException;
	public Book getBookById(int id) throws BookNotFoundException;
	
	public List<Book> getAllBooks();
	public List<Book> getBooksByName(String name);
	public List<Book> getBooksByGenre(String genre);
	public List<Book> getBooksByMinRating(int rating);
	public List<Book> getBooksByRating();
	public List<Book> getBooksByAuthor(String author);
	
}
