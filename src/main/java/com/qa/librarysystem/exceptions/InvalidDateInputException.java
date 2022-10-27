package com.qa.librarysystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="invalid date input")
public class InvalidDateInputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
