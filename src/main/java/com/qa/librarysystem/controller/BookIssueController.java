package com.qa.librarysystem.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.librarysystem.entity.BookIssue;
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
	
	@PutMapping("/issue")
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
}
