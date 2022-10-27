package com.qa.librarysystem.service;

import java.util.List;

import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.exceptions.BookAlreadyExistsException;

public interface BookService {

	public Book addBook(Book book) throws BookAlreadyExistsException;
	//public Book updateBook(Book book);
//	public boolean deleteBook(Book book);
//	public Book getBookById(int id);
	
//	public List<Book> getAllBooks();
//	public List<Book> getBooksByName(String name);
//	public List<Book> getBooksByGenre(String genre);
//	public List<Book> getBooksByRating(int rating);
//	public List<Book> getBooksByRating();
//	public List<Book> getBooksByAuthor(String author);
}
