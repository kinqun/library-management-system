package com.qa.librarysystem.entity;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="userIdSeq", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userIdSeq")
	@Column(name="user_id")
	private int uid;
	
	@Column(name="user_first_name")
	@NotNull
	@Size(min=2, max=45, message="first name must be at least 2 characters in length")
	@Pattern(regexp="^[a-zA-Z]+$", message="first name can only contain letters")
	private String fname;
	
	@Column(name="user_last_name")
	@NotNull
	@Size(min=2, max=45, message="last name must be at least 2 characters in length")
	@Pattern(regexp="[a-zA-Z]+$", message="last name can only contain letters")
	private String surname;
	
	@Column(name="user_name")
	@NotNull
	@Size(min=6, max=25, message="username length must be between 6-25")
	@Pattern(regexp="^\\w+{6,25}$", message="username must contain alphanumeral characters")
	private String username;
	
	@Column(name="user_password")
	@NotNull
	@Size(min=10, message="password must be at least 10 characters in length")
	@Pattern(regexp=".+{10,}")
	private String password;
	
	@Column(name="user_dob")
	@NotNull
	@Pattern(regexp="^\\d{4}-\\d{1,2}-\\d{1,2}$", message="invalid date format")
	private String dob;
	
	@Column(name="user_email")
	@NotNull
	@Pattern(regexp="^[a-zA-Z][\\w-]+(\\.\\w+)?[A-Za-z0-9]@[a-zA-Z]+\\.[a-zA-Z]{2,4}(\\.[a-zA-Z]{2,4})?$", message="invalid email format")
	private String email;
	
	@Column(name="user_isAdmin")
	private boolean userIsAdmin;
	
	@Column(name="user_books_borrowed")
	private ArrayList<Integer> borrowedBooks;
	
	@Column(name="user_books_favourited")
	private ArrayList<Integer> favouriteBooks;
	
	
}
