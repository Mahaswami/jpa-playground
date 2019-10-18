package com.mahaswami.training2019.jpa.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.menu.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.mahaswami.training2019.jpa.model.Book;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;

        try {
            screen = terminalFactory.createScreen();
            screen.startScreen();
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

            final Window window = new BasicWindow("Valluvar Bookstore");

            MenuBar menubar = new MenuBar();

            // "File" menu
            Menu menuFile = new Menu("File");
            menubar.addMenu(menuFile);
            menuFile.addMenuItem("Books...", () -> {
                    BasicWindow booksWindow = new BasicWindow("Books");
                    booksWindow.setCloseWindowWithEscape(true);
                    booksWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
                    Panel panel = new Panel();
                    Table<String> table = new Table<String>("ID", "Title", "ISBN");;

                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Publisher");
                    EntityManager em = emf.createEntityManager();
                    Query books = em.createNamedQuery("all_books");
                    List<Book> list = books.getResultList();

                    for (Book b: list) {
                        table.getTableModel().addRow(String.valueOf(b.getId()), b.getTheTitle(), b.getIsbn());
                    }
                    panel.addComponent(table);
                    panel.addComponent(new Button("Add Book", () -> {
                            //MessageDialog.showMessageDialog(textGUI, "MessageBox", "This is a message box", MessageDialogButton.OK);

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
                                new Button("Save", () ->{
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
                    panel.addComponent(new Button("Delete Book", () -> {
                        List<String> data = table.getTableModel().getRow(table.getSelectedRow());
                        String bookId = data.get(0);
                        MessageDialog.showMessageDialog(textGUI, "Book", "About to Delete Book ID: "  + bookId, MessageDialogButton.OK);
                        Query deleteQuery = em.createNamedQuery("find_book_by_id");
                        deleteQuery.setParameter("id", new Long(bookId));
                        Book book = (Book)deleteQuery.getResultList().get(0);
                        em.remove(book);
                        table.getTableModel().removeRow(table.getSelectedRow());
                    }));
                    booksWindow.setComponent(panel);
                    textGUI.addWindowAndWait(booksWindow);
                }
            );
            menuFile.addMenuItem("Exit", () -> {
                    System.exit(0);
            });

            // "Help" menu
            Menu menuHelp = new Menu("Help");
            menubar.addMenu(menuHelp);
            menuHelp.addMenuItem("About", () -> {
                    MessageDialog.showMessageDialog(
                            textGUI, "About", "Valluvar Bookstore by Mahaswami Software", MessageDialogButton.OK);
            });

            window.setComponent(menubar);

            textGUI.addWindowAndWait(window);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(screen != null) {
                try {
                    screen.stopScreen();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void mainOld(String[] args) {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;

        try {
            screen = terminalFactory.createScreen();
            screen.startScreen();
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

            final Window window = new BasicWindow("My Root Window");

            Panel contentPanel = new Panel(new GridLayout(2));

            GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
            gridLayout.setHorizontalSpacing(3);

            Label title = new Label("This is a label that spans two columns");
            title.setLayoutData(GridLayout.createLayoutData(
                    GridLayout.Alignment.BEGINNING, // Horizontal alignment in the grid cell if the cell is larger than the component's preferred size
                    GridLayout.Alignment.BEGINNING, // Vertical alignment in the grid cell if the cell is larger than the component's preferred size
                    true,       // Give the component extra horizontal space if available
                    false,        // Give the component extra vertical space if available
                    2,                  // Horizontal span
                    1));                  // Vertical span
            contentPanel.addComponent(title);

            contentPanel.addComponent(new Label("Text Box (aligned)"));
            contentPanel.addComponent(
                    new TextBox()
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));

            contentPanel.addComponent(new Label("Password Box (right aligned)"));
            contentPanel.addComponent(
                    new TextBox()
                            .setMask('*')
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));

            contentPanel.addComponent(new Label("Read-only Combo Box (forced size)"));
            List<String> timezonesAsStrings = new ArrayList<String>();
            for(String id: TimeZone.getAvailableIDs()) {
                timezonesAsStrings.add(id);
            }
            ComboBox<String> readOnlyComboBox = new ComboBox<String>(timezonesAsStrings);
            readOnlyComboBox.setReadOnly(true);
            readOnlyComboBox.setPreferredSize(new TerminalSize(20, 1));
            contentPanel.addComponent(readOnlyComboBox);

            contentPanel.addComponent(new Label("Editable Combo Box (filled)"));
            contentPanel.addComponent(
                    new ComboBox<String>("Item #1", "Item #2", "Item #3", "Item #4")
                            .setReadOnly(false)
                            .setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1)));

            //contentPanel.addComponent(new Label("Table"));

//            Table<String> table = new Table<String>("Column 1", "C 2", "C 3", "C 4");;
//            table.getTableModel().addRow("1", "2", "3", "4");
//            contentPanel.addComponent(table).setLayoutData(
//                    GridLayout.createHorizontallyFilledLayoutData(2));

            contentPanel.addComponent(new Label("Button (centered)"));
            contentPanel.addComponent(new Button("Button", () -> {
                MessageDialog.showMessageDialog(textGUI, "MessageBox", "This is a message box", MessageDialogButton.OK);
            }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)));

            contentPanel.addComponent(
                    new EmptySpace()
                            .setLayoutData(
                                    GridLayout.createHorizontallyFilledLayoutData(2)));
            contentPanel.addComponent(
                    new Separator(Direction.HORIZONTAL)
                            .setLayoutData(
                                    GridLayout.createHorizontallyFilledLayoutData(2)));
            contentPanel.addComponent(
                    new Button("Close", () ->{
                          //  window.close();

                        // Create window to hold the panel
                        BasicWindow window1 = new BasicWindow();
                        window1.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
                        window1.setTitle("Guru Krupa");
                        window1.setCloseWindowWithEscape(true);
                        //textGUI.getActiveWindow()
                        textGUI.addWindowAndWait(window1);

                    }).setLayoutData(
                            GridLayout.createHorizontallyEndAlignedLayoutData(2)));

            window.setComponent(contentPanel);

            textGUI.addWindowAndWait(window);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(screen != null) {
                try {
                    screen.stopScreen();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
