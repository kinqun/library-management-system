package com.qa.librarysystem.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.BookAlreadyExistsException;
import com.qa.librarysystem.exceptions.BookNotFoundException;
import com.qa.librarysystem.exceptions.UserNotFoundException;
import com.qa.librarysystem.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookRepository bookRepo;

	@Override
	public Book addBook(Book book) throws BookAlreadyExistsException {
		Optional<Book> existingBook = this.bookRepo.findById(book.getId());
		Optional<Book> existingBookByAuthorAndName = Optional.ofNullable(this.bookRepo.findByAuthorAndName(book.getBookName(), book.getAuthorName()));
		if(existingBook.isPresent()) {
			throw new BookAlreadyExistsException();
		}else if(!existingBookByAuthorAndName.isEmpty()) {
			throw new BookAlreadyExistsException();
		}else {
			return this.bookRepo.save(book);
		}
	}

	@Override
	public Book updateBook(Book book) throws BookNotFoundException {
		Optional<Book> existingBook = this.bookRepo.findById(book.getId());
		
		if(existingBook.isEmpty()) {
			throw new BookNotFoundException();
		}else {
			return this.bookRepo.save(book);
		}
	}

	@Override
	public boolean deleteBook(int id) throws BookNotFoundException {
		boolean isDeleted = false;
		Optional<Book> existingBook = this.bookRepo.findById(id);
		
		if(existingBook.isEmpty()) {
			throw new BookNotFoundException();
		}else {
			this.bookRepo.deleteById(id);
			isDeleted = true;
		}
		return isDeleted;
	}

	@Override
	public Book getBookById(int id) throws BookNotFoundException {
		Optional<Book> existingBook = this.bookRepo.findById(id);
		if(existingBook.isEmpty()) {
			throw new BookNotFoundException();
		}else {
			return existingBook.get();
		}
	}

	@Override
	public List<Book> getAllBooks() {
		return this.bookRepo.findAll();
	}

	@Override
	public List<Book> getBooksByGenre(String genre) {
		List<Book> booksByGenre=  this.bookRepo.findAll().stream().filter(b->b.getGenre().equals(genre)).collect(Collectors.toList());
		booksByGenre.sort((a,b)-> a.getBookName().compareTo(b.getBookName()));
		return booksByGenre;
	}

}
