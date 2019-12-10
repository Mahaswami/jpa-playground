package com.mahaswami.training2019.jpa.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.table.Table;
import com.mahaswami.training2019.jpa.model.Rocket;
import com.mahaswami.training2019.jpa.model.User;
import com.mahaswami.training2019.jpa.service.RocketService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.util.List;

public class RocketWindow extends BaseWindow {

    private static int TITLE_FIELD_INDEX = 0;
    private static int ISBN_FIELD_INDEX = 1;

    public RocketWindow(WindowBasedTextGUI textGUI, EntityManager em, RocketService rocketService, User currentUser) {
        super(textGUI, em, rocketService, currentUser);
    }

    protected void deleteRecord() {
        List<String> data = table.getTableModel().getRow(table.getSelectedRow());
        String bookId = data.get(0);
        showInfoMessage("Rocket", "About to Delete Rocket ID: ");
        Query deleteQuery = em.createNamedQuery("find_book_by_id");
        deleteQuery.setParameter("id", new Long(bookId));
        Rocket rocket = (Rocket)deleteQuery.getResultList().get(0);
        em.getTransaction().begin();
        em.remove(rocket);
        em.getTransaction().commit();

        table.getTableModel().removeRow(table.getSelectedRow());
    }

    protected void saveRecord() {
        Rocket newRocket = new Rocket();
        newRocket.setTheTitle(((TextBox)uiFields.get(0)).getText());
        em.getTransaction().begin();
        em.persist(newRocket);
        em.getTransaction().commit();

        Query findByISBN = em.createNamedQuery("find_book_by_isbn");
        Rocket rocket = (Rocket)findByISBN.getResultList().get(0);
        table.getTableModel().addRow(String.valueOf(rocket.getId()), rocket.getTheTitle());
    }

    protected void loadRecord() {
        List<String> data = table.getTableModel().getRow(table.getSelectedRow());
        Rocket rocket = rocketService.getRocket(new Long(data.get(0)));

        if (rocket.getTheTitle() != null)
            ((TextBox) uiFields.get(TITLE_FIELD_INDEX)).setText(rocket.getTheTitle());
    }

    protected void updateRecord() {
        try {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());
            Long id = Long.valueOf(data.get(0));
            Rocket updateBook = rocketService.findRocket(id);
            setBookDetails(updateBook);
            rocketService.update();

            table.getTableModel().removeRow(table.getSelectedRow());
            updateTable(updateBook.getId());
        } catch (ParseException ex) {
            showInfoMessage("Message","Incorrect Date format");
        }
    }

    private void setBookDetails(Rocket rocket) throws ParseException {
        rocket.setTheTitle(((TextBox) uiFields.get(TITLE_FIELD_INDEX)).getText());
    }

    private void updateTable(Long id) {
        Rocket rocket = rocketService.getRocket(new Long(id));
        addRow(rocket);
    }

    private void addRow(Rocket rocket) {
        table.getTableModel().addRow(String.valueOf(rocket.getId()), rocket.getTheTitle());
    }

    protected void buildForm(Panel contentPanel) {
        addField("Rocket Title", new TextBox(new TerminalSize(30,1)));
        addField("Rocket ISBN", new TextBox(new TerminalSize(30,1)));
    }

    protected void buildTable() {
        table = new Table<String>("ID", "Title", "ISBN");
        Query rockets = em.createNamedQuery("all_books");
        List<Rocket> list = rockets.getResultList();
        for (Rocket r: list) {
            table.getTableModel().addRow(String.valueOf(r.getId()), r.getTheTitle());
        }
    }

    protected BasicWindow buildLoginForm() {
        return null;
    }

    protected String getTitle() {return "Rocket"; }

    protected void login(){};
}
