package com.mahaswami.training2019.jpa.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.mahaswami.training2019.jpa.model.User;
import com.mahaswami.training2019.jpa.service.RocketService;

import javax.persistence.EntityManager;

public class LoginWindow extends BaseWindow {

    public LoginWindow(WindowBasedTextGUI textGUI, EntityManager em, RocketService rocketService, User currentUser) {
        super(textGUI, em, rocketService, currentUser);
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected void buildTable() {

    }

    @Override
    protected void buildForm(Panel contentPanel) {
        addField("Username", new TextBox(new TerminalSize(30,1)));
        addField("Password", new TextBox(new TerminalSize(30,1)));
    }

    @Override
    protected void deleteRecord() {

    }

    @Override
    protected void saveRecord() {

    }

    protected BasicWindow buildLoginForm() {
        BasicWindow loginWindow = new BasicWindow("Login");
        return loginWindow;
    }

    protected void login() {
        User user = new User();
        user.setUsername(((TextBox)uiFields.get(0)).getText());
        user.setPassword(((TextBox)uiFields.get(1)).getText());
        Main.currentUser = user;
        Main.start(em, rocketService);
    }

    @Override
    protected void loadRecord() {

    }

    @Override
    protected void updateRecord() {

    }

}
