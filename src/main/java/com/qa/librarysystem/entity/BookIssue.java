package com.qa.librarysystem.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

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
	//@PrimaryKeyJoinColumn(name = "user_id")
	//@JoinColumn(name="uid")
	private int uid;
	
	@Column(name="book_id")
	//@ManyToOne
	//@PrimaryKeyJoinColumn
	//@JoinColumn(name="book_id", referencedColumnName = "book_id")
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
