package com.chevtech.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.chevtech.jpa.model.Book;

public class BookService {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
	EntityManager em = emf.createEntityManager();
	EntityTransaction tx = em.getTransaction();
	
	public Book create(Book book){
		tx.begin();
		em.persist(book);
		tx.commit();
		return book;
	}
}
