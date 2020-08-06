package ui.addMember;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.database.DatabaseHandler;
import ui.addBook.addBookController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class addMemberController implements Initializable {

    @FXML
    private TextField txtMemberName;
    @FXML
    private TextField txtMemberID;
    @FXML
    private TextField txtMemberMobile;
    @FXML
    private TextField txtMemberEmail;
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
    public void addMemberOnClick(ActionEvent event) {
        String memberName = txtMemberName.getText();
        String memberID = txtMemberID.getText();
        String memberMobile = txtMemberMobile.getText();
        String memberEmail =txtMemberEmail.getText();

        if(memberName.isEmpty()||memberID.isEmpty()||memberMobile.isEmpty()||memberEmail.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Pleas enter in all fields");
            alert.showAndWait();
            return;
        }

        String qu = "INSERT INTO MEMBER VALUES ("+
                "'"+ memberID +"',"+
                "'"+ memberName +"',"+
                "'"+ memberMobile +"',"+
                "'"+ memberEmail +"'"+
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
    public void cancelAddingMemberOnClick(ActionEvent event) {
        Stage stage = (Stage)rootPane.getScene().getWindow();
        stage.close();
    }

    private void checkData() {
        String qu = "SELECT name FROM MEMBER";
        ResultSet rs = databaseHandler.execQuerry(qu);
        try {
            while(rs.next()){
                String titleFromDB = rs.getString("name");
                System.out.println(titleFromDB);
            }
        } catch (SQLException ex) {
            Logger.getLogger(addMemberController.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
}
