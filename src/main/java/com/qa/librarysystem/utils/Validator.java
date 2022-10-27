package com.qa.librarysystem.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import com.qa.librarysystem.exceptions.InvalidDateInputException;

@Component
public class Validator {
	public boolean validateDate(String inputDate) throws DateTimeParseException, InvalidDateInputException {
		
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		LocalDate parsedDob;
		LocalDate currentDate = LocalDate.now();
		try {
			parsedDob= LocalDate.parse( inputDate , f ) ;
		}catch(DateTimeParseException e) {
			throw e;
		}
		
		if(Math.abs(currentDate.getYear() - parsedDob.getYear()) > 120) {
			throw new InvalidDateInputException();
		}else {
			return true;
		}

	}
}
