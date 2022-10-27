package com.qa.librarysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.BookAlreadyExistsException;
import com.qa.librarysystem.service.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

	@Mock
	BookServiceImpl bookService;
	
	@Autowired
	@InjectMocks
	BookController bookController;
	
	@Autowired
	MockMvc mockMvc;
	
	Book book1;
	Book book2;
	Book book3;
	List<Book> booksList;
	
	
	@BeforeEach
	public void setUp() {

		book1 = new Book(1001,"BookName1","Author1",2001, "this is a book description 1","cooking", 3.4f , (byte)5, (byte)5,new ArrayList<>());
		book2 = new Book(1002,"BookName2","Author2",2002, "this is a book description 2","sci-fi", 1.5f , (byte)3, (byte)3,new ArrayList<>());
		book3 = new Book(1003,"BookName3","Author3",2003, "this is a book description 3","motivational", 4.4f , (byte)4, (byte)4,new ArrayList<>());
		booksList = Arrays.asList(book1,book2,book3);
		
		mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}
	
	@AfterEach
	public void tearDown() {
		book1 = book2 = book3 = null;
		booksList= null;
	}
	
	@Test
	@DisplayName("add-book-test")
	public void givenValidBook_whenAddBook_returnAddedBook() throws Exception {
		when(this.bookService.addBook(any())).thenReturn(book1);
		mockMvc.perform(post("/api/v1/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(book1)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.bookName").value("BookName1"));	
	}
	
	@Test
	@DisplayName("add-invalid-book-test")
	public void givenInvalidBook_whenAddBook_returnThrowsBookAlreadyExistsException() throws Exception {
		when(this.bookService.addBook(any())).thenThrow(new BookAlreadyExistsException());
		mockMvc.perform(post("/api/v1/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(book1)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isConflict())
			.andExpect(res->assertEquals("book already exists", res.getResponse().getErrorMessage()))
			.andExpect(res->assertTrue(res.getResolvedException() instanceof BookAlreadyExistsException));
			
	}
	
	public static String asJsonString(Object obj) {
		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = null;
		
		try {
			jsonStr = Obj.writeValueAsString(obj);
			System.out.println(jsonStr);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
}
