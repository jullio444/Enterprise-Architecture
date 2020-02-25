package com.cs544.lab02;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class AppBook {

	private static final SessionFactory sf;
	private static ServiceRegistry serviceRegistry;
	
	static {
		
        Configuration config = new Configuration();
        config.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                config.getProperties()).build();
        sf = config.buildSessionFactory(serviceRegistry);		

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Session session = null;
		Transaction tx = null;
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
			Book b1 = new Book("War and Peace", "TX67534", "Jullio Jymez", 56.6, df.parse("03/23/1994"));
			Book b2 = new Book("Game of Power", "RS74EW", "Mogan Towel", 23.43, df.parse("12/13/2004"));
			Book b3 = new Book("Java and Python coding principles", "THGDF", "Jeferson Brones", 16.7, df.parse("05/18/2015"));

			session.persist(b1);
			session.persist(b2);
			session.persist(b3);
			tx.commit();
		}catch(HibernateException | ParseException e) {
			if(tx!=null){
				System.out.println("Rolling back: "+e.getMessage());
				tx.rollback();
			}
			
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();			
			@SuppressWarnings("unchecked")
			List<Book> books = session.createQuery("from Book").list();
			System.out.println("************Print Books****************");
			for(Book b: books) {
				System.out.println("ISBN:"+b.getISBN()+" TITLE:"+b.getTitle()+" PRICE:"+b.getPrice()
				+" PUBLISH DATE:"+b.getPublish_date());
			}
			System.out.println();
			tx.commit();
		}catch(HibernateException e) {
			if(tx!=null){
				System.out.println("Rolling back: "+e.getMessage());
				tx.rollback();
			}
			
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		
		try {
			session = sf.openSession();
			tx = session.beginTransaction();			
			Book get_editable_book = (Book) session.get(Book.class, 1);
			get_editable_book.setPrice(56.4);
			get_editable_book.setTitle("Head First Java");
			Book get_deleteable_book = (Book) session.get(Book.class, 2);
			session.delete(get_deleteable_book);
			tx.commit();
		}catch(HibernateException e) {
			if(tx!=null){
				System.out.println("Rolling back: "+e.getMessage());
				tx.rollback();
			}
			
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		try {
			session = sf.openSession();
			tx = session.beginTransaction();			
			@SuppressWarnings("unchecked")
			List<Book> books = session.createQuery("from Book").list();
			System.out.println("************Print Books****************");
			for(Book b: books) {
				System.out.println("ISBN:"+b.getISBN()+" TITLE:"+b.getTitle()+" PRICE:"+b.getPrice()
				+" PUBLISH DATE:"+b.getPublish_date());
			}
			System.out.println();
			tx.commit();
		}catch(HibernateException e) {
			if(tx!=null){
				System.out.println("Rolling back: "+e.getMessage());
				tx.rollback();
			}
			
		}finally {
			if(session!=null) {
				session.close();
			}
		}
	}

}
