package com.qa.librarysystem.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name="library_book_issues")
public class BookIssue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="issuesIdSeq", initialValue = 101, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "issuesIdSeq")
	@Column(name="issue_id")
	private int issueId;

	@Column(name="user_id")
	@NotNull
	@Min(0)
	private int uid;
	
	@Column(name="book_id")
	@NotNull
	@Min(0)
	private int bid;
	
	@Column(name="book_issue_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateIssued;
	
	@Column(name="due_return_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dueReturnDate;
	
	@Column(name="date_returned")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate returnedDate;
	
	@Column(name="borrow_period")
	private int period;
	
	
}
