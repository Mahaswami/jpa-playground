package com.mahaswami.training2019.jpa.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;
import com.mahaswami.training2019.jpa.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class BooksWindow extends BaseWindow {

    public BooksWindow(WindowBasedTextGUI textGUI, EntityManager em) {
        super(textGUI, em);
    }

    protected void deleteRecord() {
        List<String> data = table.getTableModel().getRow(table.getSelectedRow());
        String bookId = data.get(0);
        showInfoMessage("Book", "About to Delete Book ID: ");
        Query deleteQuery = em.createNamedQuery("find_book_by_id");
        deleteQuery.setParameter("id", new Long(bookId));
        Book book = (Book)deleteQuery.getResultList().get(0);
        em.remove(book);
        table.getTableModel().removeRow(table.getSelectedRow());
    }

    protected void saveRecord() {
        Book newBook = new Book();
        newBook.setTheTitle(((TextBox)uiFields.get(0)).getText());
        newBook.setIsbn(((TextBox)uiFields.get(1)).getText());
        em.getTransaction().begin();
        em.persist(newBook);
        em.getTransaction().commit();

        Query findByISBN = em.createNamedQuery("find_book_by_isbn");
        findByISBN.setParameter("isbn", newBook.getIsbn());
        Book foundBook = (Book)findByISBN.getResultList().get(0);
        table.getTableModel().addRow(String.valueOf(foundBook.getId()), foundBook.getTheTitle(), foundBook.getIsbn());
    }

    protected void buildForm(Panel contentPanel) {
        addField("Book Title", new TextBox(new TerminalSize(30,1)));
        addField("Book ISBN", new TextBox(new TerminalSize(30,1)));
    }

    protected void buildTable() {
        table = new Table<String>("ID", "Title", "ISBN");
        Query books = em.createNamedQuery("all_books");
        List<Book> list = books.getResultList();
        for (Book b: list) {
            table.getTableModel().addRow(String.valueOf(b.getId()), b.getTheTitle(), b.getIsbn());
        }
    }

    protected String getTitle() {return "Book"; }

}
