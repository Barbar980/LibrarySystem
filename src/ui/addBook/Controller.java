package ui.addBook;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField txtTitleBook;
    @FXML
    private TextField txtIdBook;
    @FXML
    private TextField txtBookAuthor;
    @FXML
    private TextField txtBookPublisher;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void saveOnClik(ActionEvent event){

    }

    @FXML
    public void cancelOnClick(ActionEvent event){

    }
}
