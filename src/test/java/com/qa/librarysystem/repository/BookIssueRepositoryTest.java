package com.qa.librarysystem.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
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
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.qa.librarysystem.controller.BookIssueController;
import com.qa.librarysystem.entity.BookIssue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class BookIssueRepositoryTest {
	@Autowired
	BookIssueRepository bookIssueRepo;
	
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
	}
	
	@AfterEach
	public void tearDown() {
		issue1 = issue2 = issue3 = null;
		issuesList= null;
		bookIssueRepo.deleteAll();
	}
	
	@Test
	@DisplayName("save-book-issue-test")
	public void givenBoookIssue_whenCreateBookIssue_returnBookIssue() {
		BookIssue bookIssue = this.bookIssueRepo.save(issue1);
		assertEquals(1001, bookIssue.getBid());
		assertEquals(1001, bookIssue.getUid());
		assertEquals("2000-01-01", bookIssue.getDateIssued().toString());
	}
}
