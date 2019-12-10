//package com.mahaswami.training2019.jpa.tests;
//
//import org.junit.jupiter.api.Test;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.Query;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//public class BookTests {
//
//	@Test
//	public void createBook() {
////		Book book = new Book("978-3-16-148410-0", "Pragmatic Programmer");
////		BookService bs = new BookService();
////		bs.create(book);
//
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Publisher");
//		EntityManager em = emf.createEntityManager();
//
//		Query deleteQuery = em.createNamedQuery("find_book_by_id");
//		deleteQuery.setParameter("id", new Long("8"));
//		Book book = (Book)deleteQuery.getResultList().get(0);
//		System.out.println(book.getId());
//
//	}
//}
