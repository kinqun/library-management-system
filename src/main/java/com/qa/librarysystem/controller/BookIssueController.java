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

import com.qa.librarysystem.entity.BookIssue;
import com.qa.librarysystem.exceptions.BookIssueNotFoundException;
import com.qa.librarysystem.exceptions.BookNotAvailableException;
import com.qa.librarysystem.exceptions.BookNotFoundException;
import com.qa.librarysystem.exceptions.UserAlreadyCheckedOutBookException;
import com.qa.librarysystem.exceptions.UserNotFoundException;
import com.qa.librarysystem.service.BookIssueServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class BookIssueController {
	@Autowired
	BookIssueServiceImpl bookIssueService;

	ResponseEntity<?> responseEntity;
	
	@PostMapping("/issue/{uid}/{bid}")
	public ResponseEntity<?> createBookIssue(@Min(0) @PathVariable("uid") int uid, @Min(0) @PathVariable("bid") int bid) throws UserAlreadyCheckedOutBookException, BookNotAvailableException, BookNotFoundException, UserNotFoundException {
		try { 
			BookIssue newBookIssue = this.bookIssueService.createBookIssue(uid, bid);
			responseEntity = new ResponseEntity<>(newBookIssue, HttpStatus.CREATED);
			
		}catch(UserAlreadyCheckedOutBookException e) {
			throw e;
		}catch(BookNotAvailableException e) {
			throw e;
		}catch(BookNotFoundException e) {
			throw e;
		}catch(UserNotFoundException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@PutMapping("/issue/return")
	public ResponseEntity<?> returnBook(@Valid @RequestBody BookIssue bookIssue) throws BookNotFoundException, UserNotFoundException  {
		try {
			BookIssue UpdatedBookIssue = this.bookIssueService.returnBook(bookIssue);
			responseEntity = new ResponseEntity<>(UpdatedBookIssue, HttpStatus.OK);
			
		}catch(UserNotFoundException e) {
			throw e;
		}catch(BookNotFoundException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@DeleteMapping("/issue/{id}")
	public ResponseEntity<?> deleteBookIssue(@Min(0) @PathVariable("id") int id) throws BookIssueNotFoundException {
		try {
			boolean isDeleted = this.bookIssueService.deleteBookIssue(id);
			if(isDeleted) {

				responseEntity = new ResponseEntity<>("book issue is deleted", HttpStatus.OK);
			}
			
		}catch(BookIssueNotFoundException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@GetMapping("/issue/user/{id}")
	public ResponseEntity<?> getBookIssueById(@Min(0) @PathVariable("id") int id) throws BookIssueNotFoundException {
		try {
			BookIssue fetchedBookIssue = this.bookIssueService.getBookIssueById(id);
			
			responseEntity = new ResponseEntity<>(fetchedBookIssue, HttpStatus.OK);
			
			
		}catch(BookIssueNotFoundException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@GetMapping("/issue")
	public ResponseEntity<?> getAllBookIssues()  {
		try {
			List<BookIssue> allBookIssues = this.bookIssueService.getAllBookIssues();
			responseEntity = new ResponseEntity<>(allBookIssues, HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@GetMapping("/issue/{id}")
	public ResponseEntity<?> getUserCheckedOutBooks(@Min(0) @PathVariable("id") int id)  {
		try {
			List<BookIssue> allBookIssuesOfUser = this.bookIssueService.getUserCheckedOutBooks(id);
			responseEntity = new ResponseEntity<>(allBookIssuesOfUser, HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@PutMapping("/issue")
	public ResponseEntity<?> updateBookIssue(@Valid @RequestBody BookIssue bookIssue) throws BookIssueNotFoundException  {
		try {
			BookIssue updatedBookIssue = this.bookIssueService.updateBookIssue(bookIssue);
			responseEntity = new ResponseEntity<>(updatedBookIssue, HttpStatus.OK);
			
		}catch(BookIssueNotFoundException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
}
