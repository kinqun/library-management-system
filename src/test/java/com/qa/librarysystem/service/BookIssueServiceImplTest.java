package com.qa.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.qa.librarysystem.entity.Book;
import com.qa.librarysystem.entity.BookIssue;
import com.qa.librarysystem.entity.User;
import com.qa.librarysystem.exceptions.BookNotAvailableException;
import com.qa.librarysystem.exceptions.BookNotFoundException;
import com.qa.librarysystem.exceptions.UserAlreadyCheckedOutBookException;
import com.qa.librarysystem.exceptions.UserNotFoundException;
import com.qa.librarysystem.repository.BookIssueRepository;
import com.qa.librarysystem.repository.BookRepository;
import com.qa.librarysystem.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class BookIssueServiceImplTest {
	@Mock
	BookIssueRepository bookIssueRepo;
	@Mock
	BookRepository bookRepo;
	@Mock
	UserRepository userRepo;
	
	@Autowired
	@InjectMocks
	BookIssueServiceImpl bookIssueService;
	
	BookIssue issue1;
	BookIssue issue2;
	BookIssue issue3;
	User user1;
	Book book1;
	List<BookIssue> issuesList;
	
	
	@BeforeEach
	public void setUp() {
		user1 = new User(1001,"Adam","Smith","user_adam", "mypassword1","1999-9-9", "userA@gmail.com" , true, new ArrayList<>() ,new ArrayList<>()) ;
		book1 = new Book(1001,"Book Name A","A Author",2001, "this is a book description 1","cooking", 3.4f , (byte)5, (byte)5);

		issue1 = new BookIssue(101,1001,1001,LocalDate.parse("2000-01-01"),LocalDate.parse("2000-01-11"),null,10) ;
		issue2 = new BookIssue(102,1001,1001,LocalDate.parse("2001-06-12"),LocalDate.parse("2001-06-22"),null,10) ;
		issue3 = new BookIssue(103,1001,1001,LocalDate.parse("2004-10-15"),LocalDate.parse("2004-10-25"),null,10) ;
		issuesList = Arrays.asList(issue1,issue2,issue3);
	}
	
	@AfterEach
	public void tearDown() {
		issue1 = issue2 = issue3 = null;
		issuesList= null;
		bookIssueRepo.deleteAll();
	}
	
	@Test
	@DisplayName("create-issue-test")
	public void givenUidBid_whenCreateBookIssue_returnBookIssue() throws BookNotAvailableException, UserAlreadyCheckedOutBookException, BookNotFoundException, UserNotFoundException {
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.of(book1));
		when(this.userRepo.findById(anyInt())).thenReturn(Optional.of(user1));
		when(this.bookIssueRepo.save(any())).thenReturn(issue1);
		when(this.userRepo.save(any())).thenReturn(user1);
		when(this.bookRepo.save(any())).thenReturn(book1);
		
		BookIssue newBookIssue = this.bookIssueService.createBookIssue(1001, 1001);
		assertEquals(1001, newBookIssue.getUid());
		assertEquals(1001, newBookIssue.getBid());
		assertEquals(10, newBookIssue.getPeriod());
	}
	
	@Test
	@DisplayName("create-issue-no-book-available-test")
	public void givenUidBidBookUnavailable_whenCreateBookIssue_returnThrowsBookNotAvailableException() throws BookNotAvailableException, UserAlreadyCheckedOutBookException {
		book1.setBookAvailableQty((byte)0);
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.of(book1));
		when(this.userRepo.findById(anyInt())).thenReturn(Optional.of(user1));
		
		assertThrows(BookNotAvailableException.class, ()->this.bookIssueService.createBookIssue(1001, 1001));
	}
	
	@Test
	@DisplayName("create-issue-user-already-checked-out-book-test")
	public void givenUidBidAleradyCheckedOutBook_whenCreateBookIssue_returnThrowsUserAlreadyCheckedOutBookException() throws BookNotAvailableException, UserAlreadyCheckedOutBookException {
		ArrayList<Integer> newBookList = new ArrayList<>();
		newBookList.add(1001);
		user1.setBorrowedBooks(newBookList);
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.of(book1));
		when(this.userRepo.findById(anyInt())).thenReturn(Optional.of(user1));
		
		assertThrows(UserAlreadyCheckedOutBookException.class, ()->this.bookIssueService.createBookIssue(1001, 1001));
	}
	
	@Test
	@DisplayName("return-book-test")
	public void givenBookIssue_whenReturnBook_returnUpdatedBookIssue() throws BookNotAvailableException, UserAlreadyCheckedOutBookException, BookNotFoundException, UserNotFoundException {
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.of(book1));
		when(this.userRepo.findById(anyInt())).thenReturn(Optional.of(user1));
		when(this.bookRepo.save(book1)).thenReturn(book1);
		when(this.userRepo.save(user1)).thenReturn(user1);
		when(this.bookIssueRepo.save(issue1)).thenReturn(issue1);
		issue1.setReturnedDate(LocalDate.parse("2022-10-28"));
		BookIssue updatedBookIssue = this.bookIssueService.returnBook(issue1);

		assertNotNull(updatedBookIssue.getReturnedDate());
	}
	
	@Test
	@DisplayName("return-book-non-existing-uid-test")
	public void givenBookIssueWithNonExistingUid_whenReturnBook_returnThrowsUserNotFoundException() throws BookNotAvailableException, UserAlreadyCheckedOutBookException, BookNotFoundException, UserNotFoundException {
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.of(book1));
		when(this.userRepo.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, ()->this.bookIssueService.returnBook(issue1));
	}
	
	@Test
	@DisplayName("return-book-non-existing-bid-test")
	public void givenBookIssueWithNonExistingBid_whenReturnBook_returnThrowsBookNotFoundException() throws BookNotAvailableException, UserAlreadyCheckedOutBookException, BookNotFoundException, UserNotFoundException {
		when(this.bookRepo.findById(anyInt())).thenReturn(Optional.empty());
		when(this.userRepo.findById(anyInt())).thenReturn(Optional.of(user1));
		assertThrows(BookNotFoundException.class, ()->this.bookIssueService.returnBook(issue1));
	}
	
}
