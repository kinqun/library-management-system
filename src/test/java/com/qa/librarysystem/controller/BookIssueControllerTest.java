package com.qa.librarysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.qa.librarysystem.entity.BookIssue;
import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.BookNotAvailableException;
import com.qa.librarysystem.exceptions.UserAlreadyCheckedOutBookException;
import com.qa.librarysystem.repository.BookIssueRepository;
import com.qa.librarysystem.service.BookIssueServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookIssueControllerTest {
	@Mock
	BookIssueServiceImpl bookIssueService;
	
	@Autowired
	@InjectMocks
	BookIssueController bookIssueController;
	
	@Autowired
	MockMvc mockMvc;
	
	BookIssue issue1;
	BookIssue issue2;
	BookIssue issue3;
	List<BookIssue> issuesList;
	
	
	@BeforeEach
	public void setUp() {

		issue1 = new BookIssue(101,1001,1001,LocalDate.parse("2000-01-01"),LocalDate.parse("2000-01-11"),null,10) ;
		issue2 = new BookIssue(102,1001,1001,LocalDate.parse("2001-06-12"),LocalDate.parse("2001-06-22"),null,10) ;
		issue3 = new BookIssue(103,1001,1001,LocalDate.parse("2004-10-15"),LocalDate.parse("2004-10-25"),null,10) ;
		issuesList = Arrays.asList(issue1,issue2,issue3);
		
		mockMvc = MockMvcBuilders.standaloneSetup(bookIssueController).build();
	}
	
	@AfterEach
	public void tearDown() {
		issue1 = issue2 = issue3 = null;
		issuesList= null;
	}
	
	@Test
	@DisplayName("create-book-issue-test")
	public void givenValidBookIssue_whenCreateBookIssue_returnBookIssue() throws Exception {
		when(this.bookIssueService.createBookIssue(anyInt(), anyInt())).thenReturn(issue1);
		mockMvc.perform(post("/api/v1/issue/{uid}/{bid}",1001, 1001)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.dateIssued").value("2000-01-01"))
		.andExpect(jsonPath("$.issueId").value(101));		
	}
	
	@Test
	@DisplayName("create-book-issue-no-book-available-test")
	public void givenNoAvailableBook_whenCreateBookIssue_returnThrowBookNotAvailableException() throws Exception {
		when(this.bookIssueService.createBookIssue(anyInt(), anyInt())).thenThrow(new BookNotAvailableException());
		mockMvc.perform(post("/api/v1/issue/{uid}/{bid}",1001, 1001)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isConflict())
		.andExpect(res-> assertEquals("no more copies of the book is available", res.getResponse().getErrorMessage()))
		.andExpect(res-> assertTrue(res.getResolvedException() instanceof BookNotAvailableException));		
	}
	
	@Test
	@DisplayName("create-book-issue-user-has-checkedout-test")
	public void givenUserHasCheckedOutBook_whenCreateBookIssue_returnThrowUserAlreadyCheckedOutBookException() throws Exception {
		when(this.bookIssueService.createBookIssue(anyInt(), anyInt())).thenThrow(new UserAlreadyCheckedOutBookException());
		mockMvc.perform(post("/api/v1/issue/{uid}/{bid}",1001, 1001)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isConflict())
		.andExpect(res-> assertEquals("user already checked out book", res.getResponse().getErrorMessage()))
		.andExpect(res-> assertTrue(res.getResolvedException() instanceof UserAlreadyCheckedOutBookException));		
	}
}
