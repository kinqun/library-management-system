package com.qa.librarysystem.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

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
public class BookIssues {
	
	@Id
	@SequenceGenerator(name="issuesIdSeq", initialValue = 101, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "issuesIdSeq")
	@Column(name="issue_id")
	private int issueId;

	@Column(name="user_id")
	//@OneToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name="user_id")
	/*
	@JoinTable(name="library_users", 
		joinColumns = {@JoinColumn(name="user_id",referencedColumnName = "user_id")},
		inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName = "user_id")})
	*/
	private int uid;
	
	@Column(name="book_id")
	private int bid;
	
	@Column(name="book_issue_date")
	private String dateIssued;
	
	@Column(name="start_borrow_date")
	private String initialBurrowDate;
	
	@Column(name="date_to_return_book")
	private String returnDate;
	
	@Column(name="borrow_period")
	private int burrowedDays;
	
	@Column(name="amount_fined")
	private double fine;
	
	
}
