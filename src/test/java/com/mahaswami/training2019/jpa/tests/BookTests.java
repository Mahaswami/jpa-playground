package com.mahaswami.training2019.jpa.tests;

import com.mahaswami.training2019.jpa.BookService;
import com.mahaswami.training2019.jpa.model.Book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookTests {

	@Test
	public void createBook() {
		Book book = new Book("978-3-16-148410-0", "Pragmatic Programmer");
		BookService bs = new BookService();
		bs.create(book);	
	}
}
