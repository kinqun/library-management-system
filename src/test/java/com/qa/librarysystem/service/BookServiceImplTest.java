package com.qa.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.qa.librarysystem.controller.BookController;
import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.exceptions.BookAlreadyExistsException;
import com.qa.librarysystem.repository.BookRepository;
@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
	@Mock
	BookRepository bookRepo;
	
	@Autowired
	@InjectMocks
	BookServiceImpl bookService;
	
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
		
	}
	
	@AfterEach
	public void tearDown() {
		book1 = book2 = book3 = null;
		booksList= null;
	}
	
	@Test
	@DisplayName("add-book-test")
	public void givenValidBook_whenAddBook_returnAddedBook() throws BookAlreadyExistsException {
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.empty());
		when(this.bookRepo.save(any())).thenReturn(book1);
		Book addedBook = this.bookService.addBook(book1);
		assertNotNull(addedBook);
		assertEquals("BookName1", addedBook.getBookName());
	}
	
	@Test
	@DisplayName("add-invalid-book-test")
	public void givenInvalidBook_whenAddBook_returnThrowsBookAleadyExistsException() throws BookAlreadyExistsException {
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.of(book1));
		assertThrows(BookAlreadyExistsException.class, ()->this.bookService.addBook(book1));
	}
}
