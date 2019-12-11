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
    private static int SSN_NUMBER_FIELD_INDEX = 1;

    public RocketWindow(WindowBasedTextGUI textGUI, EntityManager em, RocketService rocketService, User currentUser) {
        super(textGUI, em, rocketService, currentUser);
    }

    protected void deleteRecord() {
        List<String> data = table.getTableModel().getRow(table.getSelectedRow());
        String bookId = data.get(0);
        showInfoMessage("Rocket", "About to Delete Rocket ID: ");
        Query deleteQuery = em.createNamedQuery("find_rocket_by_id");
        deleteQuery.setParameter("id", new Long(bookId));
        Rocket rocket = (Rocket)deleteQuery.getResultList().get(0);
        em.getTransaction().begin();
        em.remove(rocket);
        em.getTransaction().commit();

        table.getTableModel().removeRow(table.getSelectedRow());
    }

    protected void saveRecord() {

        String title = ((TextBox)uiFields.get(0)).getText();
        String ssn = ((TextBox)uiFields.get(1)).getText();
        rocketService.create(title, ssn);
        Query findBySSN = em.createNamedQuery("find_rocket_by_ssn_number");
        findBySSN.setParameter("ssnNumber", ssn);
        Rocket rocket = (Rocket)findBySSN.getResultList().get(0);
        table.getTableModel().addRow(String.valueOf(rocket.getId()), rocket.getTitle(), rocket.getSsnNumber());
    }

    protected void loadRecord() {
        List<String> data = table.getTableModel().getRow(table.getSelectedRow());
        Rocket rocket = rocketService.findRocket(new Long(data.get(0)));

        if (rocket.getTitle() != null)
            ((TextBox) uiFields.get(TITLE_FIELD_INDEX)).setText(rocket.getTitle());

        if(rocket.getSsnNumber() != null)
            ((TextBox) uiFields.get(SSN_NUMBER_FIELD_INDEX)).setText(rocket.getSsnNumber());
    }

    protected void updateRecord() {
        try {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());
            Long id = Long.valueOf(data.get(0));
            Rocket updateRocket = rocketService.findRocket(id);
            setRocketDetails(updateRocket);
            rocketService.update();

            table.getTableModel().removeRow(table.getSelectedRow());
            updateTable(updateRocket.getId());
        } catch (ParseException ex) {
            showInfoMessage("Message","Incorrect Date format");
        }
    }

    private void setRocketDetails(Rocket rocket) throws ParseException {
        rocket.setTitle(((TextBox) uiFields.get(TITLE_FIELD_INDEX)).getText());
        rocket.setSsnNumber(((TextBox) uiFields.get(SSN_NUMBER_FIELD_INDEX)).getText());
    }

    private void updateTable(Long id) {
        Rocket rocket = rocketService.findRocket(new Long(id));
        addRow(rocket);
    }

    private void addRow(Rocket rocket) {
        table.getTableModel().addRow(String.valueOf(rocket.getId()), rocket.getTitle());
    }

    protected void buildForm(Panel contentPanel) {
        addField("Rocket SSN Number", new TextBox(new TerminalSize(30,1)));
        addField("Rocket Title", new TextBox(new TerminalSize(30,1)));
    }

    protected void buildTable() {
        table = new Table<String>("ID", "Title", "SSN NUMBER");
        Query rockets = em.createNamedQuery("all_rockets");
        List<Rocket> list = rockets.getResultList();
        for (Rocket r: list) {
            table.getTableModel().addRow(String.valueOf(r.getId()), r.getTitle(), r.getSsnNumber());
        }
    }

    protected BasicWindow buildLoginForm() {
        return null;
    }

    protected String getTitle() {return "Rocket"; }

    protected void login(){};
}
