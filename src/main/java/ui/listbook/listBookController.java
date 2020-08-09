package ui.listbook;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import library.database.DatabaseHandler;
import ui.addBook.addBookController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listBookController implements Initializable {

    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    public AnchorPane rootPane;
    @FXML
    public TableView<Book> tableView;
    @FXML
    public TableColumn<Book, String> titleCol;
    @FXML
    public TableColumn<Book, String> idCol;
    @FXML
    public TableColumn<Book, String> authorCol;
    @FXML
    public TableColumn<Book, String> publisherCol;
    @FXML
    public TableColumn<Book, Boolean> availabilityCol;


    public void initialize(URL url, ResourceBundle rb) {
        initCol();

        loadData();
    }

    private void loadData() {
        DatabaseHandler handler = DatabaseHandler.getInstance();

        String qu = "SELECT * FROM BOOK";
        ResultSet rs = handler.execQuerry(qu);
        try {
            while(rs.next()){
                String id = rs.getString("id");
                String titleFromDB = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                Boolean avail = rs.getBoolean("isAvail");

                list.add((new Book(titleFromDB, id, author, publisher, avail)));

            }
        } catch (SQLException ex) {
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE,null, ex);
        }

        tableView.getItems().setAll(list);
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory("availability"));
    }

    public static class Book{
        private SimpleStringProperty title;
        private SimpleStringProperty id;
        private SimpleStringProperty author;
        private SimpleStringProperty publisher;
        private SimpleBooleanProperty availability;

        Book(String title, String id, String author, String publisher, Boolean availability){
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(publisher);
            this.availability = new SimpleBooleanProperty(availability);
        }


        public String getTitle() {
            return title.get();
        }

        public SimpleStringProperty titleProperty() {
            return title;
        }

        public String getId() {
            return id.get();
        }

        public SimpleStringProperty idProperty() {
            return id;
        }

        public String getAuthor() {
            return author.get();
        }

        public SimpleStringProperty authorProperty() {
            return author;
        }

        public String getPublisher() {
            return publisher.get();
        }

        public SimpleStringProperty publisherProperty() {
            return publisher;
        }

        public boolean isAvailability() {
            return availability.get();
        }

        public SimpleBooleanProperty availabilityProperty() {
            return availability;
        }
    }

}
