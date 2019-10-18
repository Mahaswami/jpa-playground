package com.mahaswami.training2019.jpa.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import javax.persistence.NamedQuery;

/**
 * Entity implementation class for Entity: Book
 *
 */
@Entity

@NamedQuery(query = "Select b from Book b", name = "all_books")
@NamedQuery(query = "Select b from Book b where b.id = :id", name = "find_book_by_id")
@NamedQuery(query = "Select b from Book b where b.isbn = :isbn", name = "find_book_by_isbn")

public class Book implements Serializable {
	@Id  @GeneratedValue
	private long id;
	private String isbn;
	private String title	;
	private static final long serialVersionUID = 1L;

	public Book() {
		super();
	}   
  
	public Book(String isbn, String title) {
		super();
		this.isbn= isbn;
		this.title= title;
	}   
	
	public long getId() {
		return this.id;
	}
 
	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}   
  
	public String getTheTitle() {
		return this.title;
	}

	public void setTheTitle(String title) {
		this.title = title;
	}
   
}
