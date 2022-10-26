package com.qa.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.librarysystem.entity.BookIssues;

@Repository
public interface BookIssueRepository extends JpaRepository<BookIssues, Integer> {

}
