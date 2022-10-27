package com.qa.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.librarysystem.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	@Query("select b from Book b where (b.authorName= :author and b.bookName= :bookName)")
	public Book findByAuthorAndName(String bookName, String author);
}
