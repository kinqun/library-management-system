package com.qa.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.qa.librarysystem.exceptions.BookNotFoundException;
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

		book1 = new Book(1001,"Book Name A","A Author",2001, "this is a book description 1","cooking", 3.4f , (byte)5, (byte)5,new ArrayList<>());
		book2 = new Book(1002,"Book Name B","B Author",2002, "this is a book description 2","sci fi", 1.5f , (byte)3, (byte)3,new ArrayList<>());
		book3 = new Book(1003,"Book Name C","C Author",2003, "this is a book description 3","motivational", 4.4f , (byte)4, (byte)4,new ArrayList<>());
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
		when(this.bookRepo.findByAuthorAndName(any(), any())).thenReturn(null);
		when(this.bookRepo.save(any())).thenReturn(book1);
		Book addedBook = this.bookService.addBook(book1);
		assertNotNull(addedBook);
		assertEquals("Book Name A", addedBook.getBookName());
	}
	
	@Test
	@DisplayName("add-existing-id-book-test")
	public void givenExistingBookId_whenAddBook_returnThrowsBookAleadyExistsException() throws BookAlreadyExistsException {
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.of(book1));
		when(this.bookRepo.findByAuthorAndName(any(), any())).thenReturn(null);
		assertThrows(BookAlreadyExistsException.class, ()->this.bookService.addBook(book1));
	}
	
	@Test
	@DisplayName("add-existing-author-and-name-book-test")
	public void givenExistingBookAuthorAndName_whenAddBook_returnThrowsBookAleadyExistsException() throws BookAlreadyExistsException {
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.empty());
		when(this.bookRepo.findByAuthorAndName(any(), any())).thenReturn(book1);
		assertThrows(BookAlreadyExistsException.class, ()->this.bookService.addBook(book1));
	}
	
	@Test
	@DisplayName("update-book-test")
	public void givenValidBookId_whenUpdateBook_returnUpdatedBook() throws BookNotFoundException {
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.of(book1));
		when(this.bookRepo.save(any())).thenReturn(book1);
		Book updatedBook = this.bookService.updateBook(book1);
		assertNotNull(updatedBook);
		assertEquals("Book Name A", updatedBook.getBookName());
	}
	
	@Test
	@DisplayName("update-nonexisting-book-test")
	public void givenNonExistingBookId_whenUpdateBook_returnThrowsBookNotFoundException() throws BookNotFoundException {
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(BookNotFoundException.class, ()->bookService.updateBook(book1));
	}
	
	@Test
	@DisplayName("delete-book-test")
	public void givenExistingBookId_whenDeleteBook_returnTrue() throws BookNotFoundException {		
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.of(book1));
		boolean deletedBook = this.bookService.deleteBook(book1.getId());
		assertTrue(deletedBook);
	}
	
	@Test
	@DisplayName("delete-non-existing-book-test")
	public void givenNonExistingBookId_whenDeleteBook_returnThrowsBookNotFoundException() throws BookNotFoundException {		
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(BookNotFoundException.class, ()->this.bookService.deleteBook(1001));
	}
	
}
