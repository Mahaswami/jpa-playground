package com.mahaswami.training2019.jpa.ui;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.table.Table;
import com.mahaswami.training2019.jpa.model.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseWindow {

    protected WindowBasedTextGUI textGUI;
    protected EntityManager em;
    protected List uiFields = new ArrayList();
    protected Table<String> table = null;
    protected Panel contentPanel = null;
    protected User currentUser;

    public BaseWindow(WindowBasedTextGUI textGUI, EntityManager em, User curentUser) {
        this.textGUI = textGUI;
        this.em = em;
        this.currentUser = curentUser;
        BasicWindow myWindow = new BasicWindow(getTitle());

        if(curentUser != null) {

            myWindow.setCloseWindowWithEscape(true);
            myWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
            Panel panel = new Panel();

            buildTable();
            if (table != null)
                panel.addComponent(table);
            Panel buttonPanel = new Panel();
            buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

            buttonPanel.addComponent(new Button("Add", () -> {
                BasicWindow addWindow = buildAddWindow("Add");
                textGUI.addWindowAndWait(addWindow);

            }));
            buttonPanel.addComponent(new Button("Edit", () -> {
                BasicWindow addWindow = buildAddWindow("Update");
                textGUI.addWindowAndWait(addWindow);

            }));
            buttonPanel.addComponent(new Button("Delete", () -> {
                deleteRecord();
            }));
            buttonPanel.addComponent(new Button("Close", () -> {
                textGUI.getActiveWindow().close();
            }));
            panel.addComponent(buttonPanel);
            myWindow.setComponent(panel);
            textGUI.addWindowAndWait(myWindow);
        }else {

            BasicWindow loginWindow = new BasicWindow("Login");
            loginWindow.setCloseWindowWithEscape(true);
            loginWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));

            contentPanel = new Panel(new GridLayout(2));

            GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
            gridLayout.setHorizontalSpacing(3);

            buildForm(contentPanel);

            contentPanel.addComponent(
                    new Button("Login", () -> {
                        login();
                        loginWindow.close();
                    }).setLayoutData(
                            GridLayout.createHorizontallyEndAlignedLayoutData(2)));

            contentPanel.addComponent(
                    new Button("Cancel", () -> {
                        loginWindow.close();
                        Main.login(em);
                    }).setLayoutData(
                            GridLayout.createHorizontallyEndAlignedLayoutData(2)));


            loginWindow.setComponent(contentPanel);
            textGUI.addWindowAndWait(loginWindow);
        }

    }

    private BasicWindow buildAddWindow(String action) {
        BasicWindow addWindow = new BasicWindow("Add " + getTitle());
        addWindow.setCloseWindowWithEscape(true);
        addWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));

        contentPanel = new Panel(new GridLayout(2));

        GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        buildForm(contentPanel);

        contentPanel.addComponent(
                new Button("Cancel", () -> {
                    addWindow.close();
                }).setLayoutData(
                        GridLayout.createHorizontallyEndAlignedLayoutData(2)));

        if (action == "Add") {
            contentPanel.addComponent(
                    new Button("Save", () -> {
                        saveRecord();
                        addWindow.close();
                    }).setLayoutData(
                            GridLayout.createHorizontallyEndAlignedLayoutData(2)));
        }
        else if (action == "Update") {
            loadRecord();
            contentPanel.addComponent(
                    new Button("Update", () -> {
                        updateRecord();
                        addWindow.close();
                    }).setLayoutData(
                            GridLayout.createHorizontallyEndAlignedLayoutData(2)));
        }
        addWindow.setComponent(contentPanel);
        return addWindow;
    }

    protected void addField(String label, AbstractComponent component) {
        contentPanel.addComponent(new Label(label));
        uiFields.add(component);
        contentPanel.addComponent(
                component
                        .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));

    }

    protected void showInfoMessage(String title, String message) {
        MessageDialog.showMessageDialog(textGUI, title, message, MessageDialogButton.OK);
    }

    abstract protected String getTitle();
    abstract protected void buildTable();
    abstract protected void buildForm(Panel contentPanel);
    abstract protected void deleteRecord();
    abstract protected void saveRecord() ;
    protected abstract void login();
    protected abstract void loadRecord();
    protected abstract void updateRecord();

}
