package com.qa.librarysystem.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.librarysystem.entity.Book;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {
	@Query("select b from Book b where (b.authorName= :author and b.bookName= :bookName)")
	public Book findByAuthorAndName(String bookName, String author);
	
	@Query("select b from Book b where b.genre= :genre")
	public List<Book> findByGenre(String genre);
}
