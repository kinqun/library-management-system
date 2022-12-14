package com.qa.librarysystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
import com.qa.librarysystem.exceptions.BookNotFoundException;
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

		book1 = new Book(1001,"Book Name A","A Author",2001, "this is a book description 1","cooking", 3.4f , (byte)5, (byte)5);
		book2 = new Book(1002,"Book Name B","B Author",2002, "this is a book description 2","sci fi", 1.5f , (byte)3, (byte)3);
		book3 = new Book(1003,"Book Name C","C Author",2003, "this is a book description 3","motivational", 4.4f , (byte)4, (byte)4);
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
			.andExpect(jsonPath("$.bookName").value("Book Name A"));	
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
	
	@Test
	@DisplayName("update-book-test")
	public void givenValidBook_whenUpdateBook_returnUpdatedBook() throws Exception {
		when(this.bookService.updateBook(any())).thenReturn(book1);
		mockMvc.perform(put("/api/v1/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(book1)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.bookName").value("Book Name A"));
			
	}
	
	@Test
	@DisplayName("update-invalid-book-test")
	public void giveninValidBook_whenUpdateBook_returnThrowsBookNotFoundException() throws Exception {
		when(this.bookService.updateBook(any())).thenThrow(new BookNotFoundException());
		mockMvc.perform(put("/api/v1/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(book1)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isNotFound())
			.andExpect(res->assertEquals("book doesnt exist", res.getResponse().getErrorMessage()))
			.andExpect(res->assertTrue(res.getResolvedException() instanceof BookNotFoundException));
			
	}
	
	@Test
	@DisplayName("delete-book-test")
	public void givenExistingBookId_whenDeleteBook_returnIsDeletedString() throws Exception {
		when(this.bookService.deleteBook(anyInt())).thenReturn(true);
		mockMvc.perform(delete("/api/v1/book/{id}","1001")
				.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(res->assertEquals("book is deleted", res.getResponse().getContentAsString()));
			
	}
	
	@Test
	@DisplayName("delete-non-existing-book-test")
	public void givenNonExistingBookId_whenDeleteBook_returnThrowsBookNotFoundException() throws Exception {
		when(this.bookService.deleteBook(anyInt())).thenThrow(new BookNotFoundException());
		mockMvc.perform(delete("/api/v1/book/{id}","1001")
				.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isNotFound())
			.andExpect(res->assertTrue(res.getResolvedException() instanceof BookNotFoundException));
			
	}
	
	@Test
	@DisplayName("get-book-by-id-test")
	public void givenExistingBookId_whenGetBookById_returnBook() throws Exception {
		when(this.bookService.getBookById(anyInt())).thenReturn(book1);
		mockMvc.perform(get("/api/v1/book/{id}","1001")
				.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.bookName").value("Book Name A"));
			
	}
	
	@Test
	@DisplayName("get-book-by-non-existing-id-test")
	public void givenNonExistingBookId_whenGetBookById_returnThrowsBookNotFoundException() throws Exception {
		when(this.bookService.getBookById(anyInt())).thenThrow(new BookNotFoundException());
		mockMvc.perform(get("/api/v1/book/{id}","1001")
				.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isNotFound())
			.andExpect(res->assertTrue(res.getResolvedException() instanceof BookNotFoundException));
			
	}
	
	@Test
	@DisplayName("get-all-books-test")
	public void given_whenGetAllBooks_returnBooksList() throws Exception {
		when(this.bookService.getAllBooks()).thenReturn(booksList);
		mockMvc.perform(get("/api/v1/book")
				.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].bookName").value("Book Name A"))
			.andExpect(jsonPath("$[1].bookName").value("Book Name B"));
			
	}
	
	@Test
	@DisplayName("get-all-books-by-genre-sorted-test")
	public void given_whenGetAllBooksByGenre_returnBooksListSortedByName() throws Exception {
		when(this.bookService.getBooksByGenre(any())).thenReturn(booksList);
		mockMvc.perform(get("/api/v1/book/genre/{genre}","sci fi")
				.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].bookName").value("Book Name A"));
			
	}
	
	@Test
	@DisplayName("get-all-books-by-min-rating-sorted-test")
	public void givenMinRating_whenGetAllBooksByRating_returnBooksListFilteredByMinRatingAndSortedByRating() throws Exception {
		booksList = booksList.stream().filter(b->b.getRating()> 2).collect(Collectors.toList());
		when(this.bookService.getBooksByGenre(any())).thenReturn(booksList);
		mockMvc.perform(get("/api/v1/book/genre/{rating}","2")
				.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
			
	}
	
	@Test
	@DisplayName("get-all-books-by-author-test")
	public void givenAuthor_whenGetAllBooksByAuthor_returnBooksListFilteredAuthor() throws Exception {
		booksList = booksList.stream().filter(b->b.getAuthorName().equals("C Author")).collect(Collectors.toList());
		when(this.bookService.getBooksByAuthor(any())).thenReturn(booksList);
		mockMvc.perform(get("/api/v1/book/author/{author}","C Author")
				.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
			.andExpect(jsonPath("$[0].bookName").value("Book Name C"));
			
	}
	
	@Test
	@DisplayName("get-all-books-by-bookname-test")
	public void givenBookName_whenGetAllBooksByBookName_returnBooksListFilteredBookName() throws Exception {
		booksList = booksList.stream().filter(b->b.getBookName().equals("Book Name B")).collect(Collectors.toList());
		when(this.bookService.getBooksByName(any())).thenReturn(booksList);
		mockMvc.perform(get("/api/v1/book/bookname/{name}","Book Name B")
				.accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
			.andExpect(jsonPath("$[0].bookName").value("Book Name B"));
			
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
