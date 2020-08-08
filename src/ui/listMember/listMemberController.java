package ui.listMember;

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
import ui.listbook.listBookController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listMemberController implements Initializable {

    ObservableList<Member> list = FXCollections.observableArrayList();

    @FXML
    public AnchorPane rootPane;
    @FXML
    public TableView<Member> tableView;
    @FXML
    public TableColumn<Member, String> idCol;
    @FXML
    public TableColumn<Member, String> nameCol;
    @FXML
    public TableColumn<Member, String> mobileCol;
    @FXML
    public TableColumn<Member, String> emailCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadData();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void loadData() {
        DatabaseHandler handler = DatabaseHandler.getInstance();

        String qu = "SELECT * FROM MEMBER";
        ResultSet rs = handler.execQuerry(qu);
        try {
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");

                list.add((new Member(id, name, mobile, email)));

            }
        } catch (SQLException ex) {
            Logger.getLogger(addBookController.class.getName()).log(Level.SEVERE,null, ex);
        }

        tableView.getItems().setAll(list);
    }

    public static class Member {

        private SimpleStringProperty id;
        private SimpleStringProperty name;
        private SimpleStringProperty mobile;
        private SimpleStringProperty email;

        Member(String id, String name, String mobile, String email){
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
        }

        public String getId() {
            return id.get();
        }

        public SimpleStringProperty idProperty() {
            return id;
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public String getMobile() {
            return mobile.get();
        }

        public SimpleStringProperty mobileProperty() {
            return mobile;
        }

        public String getEmail() {
            return email.get();
        }

        public SimpleStringProperty emailProperty() {
            return email;
        }
    }

}
