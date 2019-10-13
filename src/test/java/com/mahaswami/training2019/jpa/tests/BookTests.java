package com.mahaswami.training2019.jpa.tests;

//import static org.junit.Assert.*;
//import org.junit.jupiter.api.Test;

import com.mahaswami.training2019.jpa.BookService;
import com.mahaswami.training2019.jpa.model.Book;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookTests {

	@Test
	public void test() {
		//fail("Not yet implemented");
		assertEquals(1, 1);
	}

	@Test
	public void createBook() {
		System.out.printf("Test");
		Book book = new Book("isbn01", "title12");
		BookService bs = new BookService();
		bs.create(book);	
	}
}
