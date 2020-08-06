package ui.addBook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.database.DatabaseHandler;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class addBookController implements Initializable {

    @FXML
    private TextField txtTitleBook;
    @FXML
    private TextField txtIdBook;
    @FXML
    private TextField txtBookAuthor;
    @FXML
    private TextField txtBookPublisher;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane rootPane;

    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = new DatabaseHandler();
        checkData();
    }

    @FXML
    public void addBookOnClick(ActionEvent event){
        String bookID = txtIdBook.getText();
        String bookAuthor = txtBookAuthor.getText();
        String bookTitle = txtTitleBook.getText();
        String bookPublisher =txtBookPublisher.getText();

        if(bookID.isEmpty()||bookAuthor.isEmpty()||bookTitle.isEmpty()||bookPublisher.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Pleas enter in all fields");
            alert.showAndWait();
            return;
        }

//        stmt.execute("CREATE TABLE "+ TABLE_NAME + "("
//                + "     id varchar(200) primary key,\n"
//                + "     title varchar(200),\n"
//                + "     author varchar(200),\n"
//                + "     publisher varchar(100),\n"
//                + "     isAvail boolean default true"
//                + " )");

        String qu = "INSERT INTO BOOK VALUES ("+
                "'"+ bookID +"',"+
                "'"+ bookTitle +"',"+
                "'"+ bookAuthor +"',"+
                "'"+ bookPublisher +"',"+
                ""+ true +""+
                ")";
        System.out.println(qu);
        if(databaseHandler.execAction(qu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }

    @FXML
    public void cancelAddingBookOnClick(ActionEvent event){
        Stage stage = (Stage)rootPane.getScene().getWindow();
        stage.close();
    }


    private void checkData() {
        String qu = "SELECT title FROM BOOK";
        ResultSet rs = databaseHandler.execQuerry(qu);
            try {
                while(rs.next()){
                    String titleFromDB = rs.getString("title");
                    System.out.println(titleFromDB);
                }
            } catch (SQLException ex) {
                Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE,null, ex);
            }
    }

}
