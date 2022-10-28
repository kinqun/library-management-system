package com.qa.librarysystem.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class BookIssueServiceImpl implements BookIssureService {

	@Autowired
	BookIssueRepository bookIssueRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Override
	public BookIssue createBookIssue(int uid, int bid) throws BookNotAvailableException, UserAlreadyCheckedOutBookException, BookNotFoundException, UserNotFoundException {
		BookIssue newBookIssue = new BookIssue();
		newBookIssue.setUid(uid);
		newBookIssue.setBid(bid);
		newBookIssue.setDateIssued(LocalDate.now());
		long dueDate = newBookIssue.getDateIssued().toEpochDay() + 10;
		newBookIssue.setDueReturnDate(LocalDate.EPOCH.plusDays(dueDate));
		newBookIssue.setPeriod((int) (newBookIssue.getDueReturnDate().toEpochDay() - newBookIssue.getDateIssued().toEpochDay()));
		
		Optional<Book> bookToReserve = this.bookRepo.findById(bid);
		Optional<User> currentUser = this.userRepo.findById(uid);
		Book updatedBook = null;
		
		if(!bookToReserve.isEmpty()) {
			Book book = bookToReserve.get();
			
			if(book.getBookAvailableQty() == 0) {
				throw new BookNotAvailableException();
			}else {
				book.setBookAvailableQty((byte)(book.getBookAvailableQty() - 1));
				updatedBook = book;
			}
		}else {
			throw new BookNotFoundException();
		}
		
		if(!currentUser.isEmpty()) {
			User user = currentUser.get();
			ArrayList<Integer> usersBurrowedBooks = user.getBorrowedBooks();
			
			for(int book_id : usersBurrowedBooks) {
				if(book_id == bid) {
					throw new UserAlreadyCheckedOutBookException();
				}
			}
			
			usersBurrowedBooks.add(bid);
			user.setBorrowedBooks(usersBurrowedBooks);
			this.userRepo.save(user);
			this.bookRepo.save(updatedBook);
			
		}else {
			throw new UserNotFoundException();
		}
		
		return this.bookIssueRepo.save(newBookIssue);
	}

	@Override
	public BookIssue returnBook(BookIssue bookIssue) throws BookNotFoundException, UserNotFoundException {
		LocalDate d = LocalDate.now();
		bookIssue.setReturnedDate(d);
		Optional<User> userOptional = this.userRepo.findById(bookIssue.getUid());
		Optional<Book> bookOptional = this.bookRepo.findById(bookIssue.getBid());
		Book book = null;

		if(bookOptional.isPresent()) {
			book = bookOptional.get();
			book.setBookAvailableQty((byte)(book.getBookAvailableQty() + 1));
		}else {
			throw new BookNotFoundException();
		}
		if(userOptional.isPresent()) {
			User user = userOptional.get();
			ArrayList<Integer> userBurrowedBooks = user.getBorrowedBooks();
			userBurrowedBooks.remove(Integer.valueOf(bookIssue.getBid()));
			user.setBorrowedBooks(userBurrowedBooks);
			this.userRepo.save(user);
			this.bookRepo.save(book);
		}else {
			throw new UserNotFoundException();
		}
		return this.bookIssueRepo.save(bookIssue);
	}

}
