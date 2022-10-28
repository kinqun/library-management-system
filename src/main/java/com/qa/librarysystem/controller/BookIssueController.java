package com.qa.librarysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.librarysystem.entity.BookIssue;
import com.qa.librarysystem.exceptions.BookNotAvailableException;
import com.qa.librarysystem.exceptions.UserAlreadyCheckedOutBookException;
import com.qa.librarysystem.service.BookIssueServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class BookIssueController {
	@Autowired
	BookIssueServiceImpl bookIssueService;

	ResponseEntity<?> responseEntity;
	
	@PostMapping("/issue/{uid}/{bid}")
	public ResponseEntity<?> createBookIssue(@PathVariable("uid") int uid, @PathVariable("bid") int bid) throws UserAlreadyCheckedOutBookException, BookNotAvailableException {
		try {
			BookIssue newBookIssue = this.bookIssueService.createBookIssue(uid, bid);
			responseEntity = new ResponseEntity<>(newBookIssue, HttpStatus.CREATED);
			
		}catch(UserAlreadyCheckedOutBookException e) {
			throw e;
		}catch(BookNotAvailableException e) {
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
}
