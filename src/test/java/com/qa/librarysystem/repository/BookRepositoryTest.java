package com.qa.librarysystem.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.qa.librarysystem.controller.BookController;
import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.service.BookServiceImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class BookRepositoryTest {
	
	@Autowired
	BookRepository bookRepo;
	
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
		
	}
	
	@AfterEach
	public void tearDown() {
		book1 = book2 = book3 = null;
		booksList= null;
		bookRepo.deleteAll();
	}
	
	@Test
	@DisplayName("save-book-test")
	public void givenValidBook_whenAddBook_returnAddedBook() {
		Book addedBook = this.bookRepo.save(book1);
		assertNotNull(addedBook);
		assertEquals("Book Name A", addedBook.getBookName());
		
	}
	
	@Test
	@DisplayName("find-book-by-id-test")
	public void givenValidBookId_whenFindBookById_returnBook() {
		this.bookRepo.save(book1);
		Optional<Book> fetchedBook = this.bookRepo.findById(1001);
		assertEquals("Book Name A", fetchedBook.get().getBookName());
	}
	
	@Test
	@DisplayName("find-book-by-author-and-name-test")
	public void givenValidBooknameAndValidAuthor_whenFindBookByAuthorAndName_returnBook() {
		this.bookRepo.save(book1);
		
		Book fetchedBook = this.bookRepo.findByAuthorAndName("Book Name A", "A Author");
		assertEquals("Book Name A", fetchedBook.getBookName());
	}
	
	@Test
	@DisplayName("find-book-by-invalid-author-and-valid-name-test")
	public void givenValidBooknameAndInvalidAuthor_whenFindBookByAuthorAndName_returnNull() {
		this.bookRepo.save(book1);
		
		Book fetchedBook = this.bookRepo.findByAuthorAndName("Book Name A", "Invalid Author");
		assertNull(fetchedBook);
	}
	
	@Test
	@DisplayName("find-book-by-valid-author-and-invalid-name-test")
	public void givenInvalidBooknameAndValidAuthor_whenFindBookByAuthorAndName_returnNull() {
		this.bookRepo.save(book1);
		
		Book fetchedBook = this.bookRepo.findByAuthorAndName("invalid book Name A", "A Author");
		assertNull(fetchedBook);
	}
	
	@Test
	@DisplayName("delete-book-test")
	public void givenExistingBookId_whenDeleteBook_return() {
		this.bookRepo.save(book1);
		this.bookRepo.deleteById(1001);
		Optional<Book> fetchedBook = this.bookRepo.findById(1001);
		assertThat(fetchedBook).isEmpty();
	}
	
	@Test
	@DisplayName("get-all-books")
	public void given_whenGetAllBooks_returnBooksList() {
		this.bookRepo.save(book1);
		this.bookRepo.save(book2);
		this.bookRepo.save(book3);
		List<Book> newBookList = this.bookRepo.findAll();
		assertEquals(3, newBookList.size());
		assertEquals("Book Name A", newBookList.get(0).getBookName());
	}
	
}
