package ui.main;

import com.jfoenix.effects.JFXDepthManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.database.DatabaseHandler;
import ui.addBook.addBookController;
import ui.listbook.listBookController;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainController implements Initializable {

    @FXML
    public Button issueButton;
    @FXML
    public Button addMemberButton;
    @FXML
    public Button addBookButton;
    @FXML
    public Button memberListButton;
    @FXML
    public Button bookListButton;
    @FXML
    public Button settingButton;
    @FXML
    public HBox bookInfo;
    @FXML
    public HBox memberInfo;
    @FXML
    public TextField txtBookID;
    @FXML
    public Text bookNameField;
    @FXML
    public Text authorField;
    @FXML
    public Text bookStatusField;
    @FXML
    public TextField txtMemberID;
    @FXML
    public Text memberNameFIeld;
    @FXML
    public Text contactMemberField;

    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JFXDepthManager.setDepth(bookInfo, 1);
        JFXDepthManager.setDepth(memberInfo, 3);

        databaseHandler = DatabaseHandler.getInstance();
    }


    @FXML
    public void loadAddMemberWindowOnClick(ActionEvent event) {
        loadWindow("/ui/addMember/addMemberForm.fxml","Add Member");
    }

    @FXML
    public void loadAddBookWindowOnClick(ActionEvent event) {
        loadWindow("/ui/addBook/addBookForm.fxml","Add Book");
    }

    @FXML
    public void loadMemberListWindowOnClik(ActionEvent event) {
        loadWindow("/ui/listMember/listMemberForm.fxml","Member List");
    }

    @FXML
    public void loadBookListWindowOnClik(ActionEvent event) {
        loadWindow("/ui/listbook/listBookForm.fxml","Book List");
    }

    @FXML
    public void loadSettingWindowOnClik(ActionEvent event) {
        //loadWindow("/ui/addBook/addBookForm.fxml","Add Member");
    }

    void loadWindow(String loc, String title){

        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void loadBookInfoOnClik(ActionEvent event) {
        clearBookCache();

        String bookID = txtBookID.getText();
        String qu = "SELECT * FROM BOOK WHERE id ='"+bookID+"'";
        ResultSet rs = databaseHandler.execQuerry(qu);

        Boolean flag = false;
        String bookName = "";
        String author = "";

        try {
            while(rs.next()){
                bookName = rs.getString("title");
                author = rs.getString("author");
                Boolean statusFromDB = rs.getBoolean("isAvail");
                flag = true;

                bookNameField.setText(bookName);
                authorField.setText(author);

                String status = (statusFromDB)?"Avaulable":"Unavailable";
                bookStatusField.setText(status);

                flag = true;

                if(!flag){
                    bookStatusField.setText("Unavailable");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE,null, ex);
        }
        didDataAreInBase(bookName, "No book with this ID");
    }

    @FXML
    public void loadMemberInfoOnClik(ActionEvent event) throws SQLException {
        clearMemberCache();

        String memberID = txtMemberID.getText();
        String qu = "SELECT * FROM MEMBER WHERE id ='"+memberID+"'";
        ResultSet rs = databaseHandler.execQuerry(qu);

        String memberName = "";
        String memberContact = "";

        try {
            while(rs.next()){
                memberName= rs.getString("name");
                memberContact = rs.getString("mobile")+ "   "+rs.getString("email");;
                memberNameFIeld.setText(memberName);
                contactMemberField.setText(memberContact);
            }
        } catch (SQLException ex) {
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE,null, ex);
        }
        didDataAreInBase(memberName, "No member with this ID");
    }

    public static void didDataAreInBase(String dateToCheck, String informationToPrint){
        if(dateToCheck.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(informationToPrint);
            alert.showAndWait();
        }
    }

    void clearBookCache(){
        bookNameField.setText("");
        authorField.setText("");
        bookStatusField.setText("");
    }

    void clearMemberCache(){
        memberNameFIeld.setText("");
        contactMemberField.setText("");
    }

    @FXML
    public void loadIssueOperationOnClick(ActionEvent event){
        String memberID = txtMemberID.getText();
        String bookID = txtBookID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm issue operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to isse the book -------- to =======");

        Optional<ButtonType> response = alert.showAndWait();

        if(response.get()==ButtonType.OK){
            String str = "INSERT INTO ISSUE(memberID, bookID) VALUES ("+
                    "'"+ memberID +"',"+
                    "'"+ bookID + "')";

            String str2 = "UPDATE BOOK SET isAvail = false WHERE id = '" + bookID + "'";

            if(databaseHandler.execAction(str) && databaseHandler.execAction(str2)){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Success. Book Issue Complete");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Failed. ");
            }
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Issue operation has been canceled");
        }






    }
}
