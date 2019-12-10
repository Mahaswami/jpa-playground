package com.mahaswami.training2019.jpa.ui;

import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.mahaswami.training2019.jpa.model.User;

import javax.persistence.EntityManager;

public class BlankWindow extends BaseWindow {

    public BlankWindow(WindowBasedTextGUI textGUI, EntityManager em, User user) {
        super(textGUI, em, user);
    }

    protected String getTitle() {return "Blank"; }

    @Override
    protected void buildTable() {

    }

    @Override
    protected void buildForm(Panel contentPanel) {

    }

    @Override
    protected void deleteRecord() {

    }

    @Override
    protected void saveRecord() {

    }

    @Override
    protected void login() {

    }

    @Override
    protected void loadRecord() {

    }

    @Override
    protected void updateRecord() {

    }

}
