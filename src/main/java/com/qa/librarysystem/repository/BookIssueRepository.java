package com.qa.librarysystem.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.librarysystem.entity.BookIssue;

@Repository
@Transactional
public interface BookIssueRepository extends JpaRepository<BookIssue, Integer> {
	

}
