package com.qa.librarysystem.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteBook(@Min(0) @PathVariable("id") int id) throws BookNotFoundException{
		try {
			boolean isDeleted = this.bookService.deleteBook(id);
			if(isDeleted) {
				responseEntity = new ResponseEntity<>("book is deleted", HttpStatus.OK);
			}
		}catch(BookNotFoundException e) {
			throw e;
		}catch(Exception e) {
			responseEntity = new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	} 
	
	@GetMapping("/book/{id}")
	public ResponseEntity<?> getBookById(@Min(0) @PathVariable("id") int id) throws BookNotFoundException{
		try {
			Book fetchedBook = this.bookService.getBookById(id);
			responseEntity = new ResponseEntity<>(fetchedBook, HttpStatus.OK);
			
		}catch(BookNotFoundException e) {
			throw e;
		}catch(Exception e) {
			responseEntity = new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	} 
	
	@GetMapping("/book")
	public ResponseEntity<?> getAllBooks() {
		try {
			List<Book> allBooks = this.bookService.getAllBooks();
			responseEntity = new ResponseEntity<>(allBooks, HttpStatus.OK);
			
		}catch(Exception e) {
			responseEntity = new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	} 
}
