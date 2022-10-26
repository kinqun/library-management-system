package com.qa.librarysystem.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name="library_users")
public class User {
	
	@Id
	@SequenceGenerator(name="userIdSeq", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userIdSeq")
	@Column(name="user_id")
	private int uid;
	
	@Column(name="user_first_name")
	private String fname;
	
	@Column(name="user_middle_names")
	private String middleNames;
	
	@Column(name="user_last_name")
	private String surname;
	
	@Column(name="user_name")
	private String username;
	
	@Column(name="user_password")
	private String password;
	
	@Column(name="user_dob")
	private String dob;
	
	@Column(name="user_email")
	private String email;
	
	@Column(name="user_isAdmin")
	private boolean userIsAdmin;
	
	@Column(name="user_books_borrowed")
	private ArrayList<Book> borrowedBooks;
	
	@Column(name="user_books_favourited")
	private ArrayList<Book> favouriteBooks;
	
	@Column(name="user_books_read")
	private ArrayList<Book> readBooks;
	
}
