package com.mahaswami.training2019.jpa.service;

import com.googlecode.lanterna.gui2.TextBox;
import com.mahaswami.training2019.jpa.model.Rocket;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class RocketService {
	private EntityManager em;
	private EntityTransaction tx;

	public RocketService(EntityManager entityManager) {
		this.em = entityManager;
		this.tx = entityManager.getTransaction();
	}

	public Rocket create(String title, String ssn) {
		Rocket newRocket = new Rocket();
		newRocket.setTitle(title);
		newRocket.setSsnNumber(ssn);
		em.getTransaction().begin();
		em.persist(newRocket);
		em.getTransaction().commit();
		tx.begin();
		em.persist(newRocket);
		tx.commit();
		return newRocket;
	}

	public Rocket getRocket(Long id) {
		Query namedQuery = em.createNamedQuery("find_rocket_by_id");
		namedQuery.setParameter("id", id);
		return (Rocket) namedQuery.getResultList().get(0);
	}

	public Rocket findRocket(Long id) {
		Rocket rocket = em.find( Rocket.class, id);
		return rocket;
	}

	public void update() {
		tx.begin();
		em.flush();
		tx.commit();
	}
}
