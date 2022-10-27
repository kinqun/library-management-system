package com.qa.librarysystem.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

		book1 = new Book(1001,"BookNameA","A Author",2001, "this is a book description 1","cooking", 3.4f , (byte)5, (byte)5,new ArrayList<>());
		book2 = new Book(1002,"BookNameB","B Author",2002, "this is a book description 2","sci fi", 1.5f , (byte)3, (byte)3,new ArrayList<>());
		book3 = new Book(1003,"BookNameC","C Author",2003, "this is a book description 3","motivational", 4.4f , (byte)4, (byte)4,new ArrayList<>());
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
		assertEquals("BookNameA", addedBook.getBookName());
		
	}
}
