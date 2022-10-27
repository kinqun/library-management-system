package com.qa.librarysystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.exceptions.BookAlreadyExistsException;
import com.qa.librarysystem.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookRepository bookRepo;

	@Override
	public Book addBook(Book book) throws BookAlreadyExistsException {
		Optional<Book> existingBook = this.bookRepo.findById(book.getBid());
		
		if(existingBook.isPresent()) {
			throw new BookAlreadyExistsException();
		}else {
			return this.bookRepo.save(book);
		}
	}

}
