package com.qa.librarysystem.service;

import java.util.Optional;

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

}
