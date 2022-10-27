package com.qa.librarysystem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.exceptions.BookAlreadyExistsException;
import com.qa.librarysystem.exceptions.BookNotFoundException;
import com.qa.librarysystem.service.BookServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class BookController {

	@Autowired
	BookServiceImpl bookService;
	
	ResponseEntity<?> responseEntity;
	
	@PostMapping("/book")
	public ResponseEntity<?> addBook(@Valid @RequestBody Book book) throws BookAlreadyExistsException{
		try {
			Book addedBook = this.bookService.addBook(book);
			responseEntity = new ResponseEntity<>(addedBook, HttpStatus.CREATED);
		}catch(BookAlreadyExistsException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@PutMapping("/book")
	public ResponseEntity<?> updateBook(@Valid @RequestBody Book book) throws BookNotFoundException{
		try {
			Book updatedBook = this.bookService.updateBook(book);
			responseEntity = new ResponseEntity<>(updatedBook, HttpStatus.OK);
		}catch(BookNotFoundException e) {
			throw e;
		}catch(Exception e) {
			responseEntity = new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}
