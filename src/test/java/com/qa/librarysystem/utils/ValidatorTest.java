package com.qa.librarysystem.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.qa.librarysystem.exceptions.InvalidDateInputException;

@ExtendWith(MockitoExtension.class)
public class ValidatorTest {
	Validator validator;
	
	String validDate;
	String invalidDayDate;
	String invalidMonthDate;
	String invalidYearDate;
	
	@BeforeEach
	public void setUp() {
		validator = new Validator();
		validDate = "2000-01-01";
		invalidDayDate = "2000-01-44";
		invalidMonthDate = "2000-13-01";
		invalidYearDate = "3000-01-01";
	}
	
	@AfterEach
	public void tearDown() {
		validator = null;
		validDate = null;
		invalidDayDate = null;
		invalidMonthDate = null;
		invalidYearDate = null;
	}

	
	@Test
	@DisplayName("date-valid-test")
	public void givenValidDate_whenValidateDate_returnTrue() throws DateTimeParseException, InvalidDateInputException {
		boolean result = this.validator.validateDate(validDate);
		
		assertTrue(result);
	}
	
	@Test
	@DisplayName("date-invalid-day-test")
	public void givenDateWithInvalidDay_whenValidateDate_returnThrowsInvalidDateInputException() throws DateTimeParseException, InvalidDateInputException {
		
		assertThrows(DateTimeParseException.class, ()->this.validator.validateDate(invalidDayDate));
	}
	
	@Test
	@DisplayName("date-invalid-month-test")
	public void givenDateWithInvalidMonth_whenValidateDate_returnThrowsInvalidDateInputException() throws DateTimeParseException, InvalidDateInputException {
		
		assertThrows(DateTimeParseException.class, ()->this.validator.validateDate(invalidMonthDate));
	}
	
	@Test
	@DisplayName("date-invalid-year-test")
	public void givenDateWithInvalidYear_whenValidateDate_returnThrowsInvalidDateInputException() throws DateTimeParseException, InvalidDateInputException {
		
		assertThrows(InvalidDateInputException.class, ()->this.validator.validateDate(invalidYearDate));
	}
}
