package com.mahaswami.training2019.jpa.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.table.Table;
import com.mahaswami.training2019.jpa.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

public class BooksWindow {

    public BooksWindow(WindowBasedTextGUI textGUI, EntityManager em) {
        BasicWindow booksWindow = new BasicWindow("Books");
        booksWindow.setCloseWindowWithEscape(true);
        booksWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
        Panel panel = new Panel();
        Table<String> table = new Table<String>("ID", "Title", "ISBN");

        Query books = em.createNamedQuery("all_books");
        List<Book> list = books.getResultList();

        for (Book b: list) {
            table.getTableModel().addRow(String.valueOf(b.getId()), b.getTheTitle(), b.getIsbn());
        }
        panel.addComponent(table);
        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        buttonPanel.addComponent(new Button("Add Book", () -> {

            BasicWindow addWindow = new BasicWindow("Add Book");
            addWindow.setCloseWindowWithEscape(true);
            addWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));

            Panel contentPanel = new Panel(new GridLayout(2));

            GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
            gridLayout.setHorizontalSpacing(3);

            contentPanel.addComponent(new Label("Book Title"));
            TextBox textTitle = new TextBox(new TerminalSize(30,1));
            contentPanel.addComponent(
                    textTitle
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));

            contentPanel.addComponent(new Label("Book ISBN"));
            TextBox textISBN = new TextBox(new TerminalSize(30,1));
            contentPanel.addComponent(
                    textISBN
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));

            contentPanel.addComponent(
                    new Button("Save", () -> {
                        Book newBook = new Book();
                        newBook.setTheTitle(textTitle.getText());
                        newBook.setIsbn(textISBN.getText());
                        em.getTransaction().begin();
                        em.persist(newBook);
                        em.getTransaction().commit();

                        Query findByISBN = em.createNamedQuery("find_book_by_isbn");
                        findByISBN.setParameter("isbn", newBook.getIsbn());
                        Book foundBook = (Book)findByISBN.getResultList().get(0);
                        table.getTableModel().addRow(String.valueOf(foundBook.getId()), foundBook.getTheTitle(), foundBook.getIsbn());
                        addWindow.close();

                    }).setLayoutData(
                            GridLayout.createHorizontallyEndAlignedLayoutData(2)));

            addWindow.setComponent(contentPanel);
            textGUI.addWindowAndWait(addWindow);

        }));
        buttonPanel.addComponent(new Button("Delete Book", () -> {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());
            String bookId = data.get(0);
            MessageDialog.showMessageDialog(textGUI, "Book", "About to Delete Book ID: "  + bookId, MessageDialogButton.OK);
            Query deleteQuery = em.createNamedQuery("find_book_by_id");
            deleteQuery.setParameter("id", new Long(bookId));
            Book book = (Book)deleteQuery.getResultList().get(0);
            em.remove(book);
            table.getTableModel().removeRow(table.getSelectedRow());
        }));
        buttonPanel.addComponent(new Button("Close", () -> {
            textGUI.getActiveWindow().close();
        }));
        panel.addComponent(buttonPanel);
        booksWindow.setComponent(panel);
        textGUI.addWindowAndWait(booksWindow);
    }
}
