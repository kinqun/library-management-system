package com.qa.librarysystem.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
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
@Table(name="library_books")
public class Book {

	@Id
	@SequenceGenerator(name="bookIdSeq", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bookIdSeq")
	@Column(name="book_id")
	private int bid;
	
	@Column(name="book_name")
	private String bookName;
	
	@Column(name="book_author")
	private String authorName;
	
	@Column(name="book_published_year")
	private int yearPublished;
	
	@Column(name="book_description")
	private String description;
	
	@Column(name="book_genre")
	private String genre;
	
	@Column(name="book_rating")
	private float rating;
	
	@Column(name="book_quantity")
	private byte bookQty;
	
	@Column(name="book_quantity_available")
	private byte bookQtyAvailable;
	
	@Column(name="book_comments")
	private ArrayList<String> comments;
	
}
