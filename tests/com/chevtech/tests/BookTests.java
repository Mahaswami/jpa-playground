package com.chevtech.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import com.chevtech.jpa.BookService;
import com.chevtech.jpa.model.Book;

public class BookTests {

	@Test
	public void test() {
		//fail("Not yet implemented");
		assertEquals(1, 1);
	}

	@Test
	public void createBook() {
		Book book = new Book();
		BookService bs = new BookService();
		bs.create(book);	
	}
}
