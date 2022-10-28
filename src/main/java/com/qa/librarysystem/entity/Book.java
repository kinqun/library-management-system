package com.qa.librarysystem.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
@Table(name="library_books")
public class Book implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="bookIdSeq", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bookIdSeq")
	@Column(name="book_id")
	private int id;
	
	@Column(name="book_name")
	@NotNull
	@Size(min=2, message="book name must be at least 2")
	@Pattern(regexp="^[\\w:,\\. ]+$")
	private String bookName;
	
	@Column(name="book_author")
	@NotNull
	@Size(min=2, message="author name must be at least 2")
	@Pattern(regexp="^[a-zA-Z]{1,} ([a-zA-Z]+ |[a-zA-Z]\\. )?[a-zA-Z]{2,}$")
	private String authorName;
	
	@Column(name="book_published_year")
	@Min(1)
	@Max(2022)
	private int yearPublished;
	@Column(name="book_description")
	@Size(min=2, max=255)
	private String description;
	
	@Column(name="book_genre")
	@NotNull
	@Size(min=2, max=25)
	@Pattern(regexp="^[a-zA-Z]+([ -])?([a-zA-Z]+)?$")
	private String genre;
	
	@Column(name="book_rating")
	@Min(0)
	@Max(5)
	private float rating;
	
	@Column(name="book_quantity_holding")
	@NotNull
	@Min(0)
	private byte bookHoldingQty;
	
	@Column(name="book_quantity_available")
	@NotNull
	@Min(0)
	private byte bookAvailableQty;
	
	
}
