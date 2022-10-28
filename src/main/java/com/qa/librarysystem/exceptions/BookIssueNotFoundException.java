package com.qa.librarysystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="book issue doesnt exist")
public class BookIssueNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
