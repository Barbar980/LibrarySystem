package ui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.database.DatabaseHandler;
import org.apache.derby.iapi.db.Database;

public class mainMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        stage.setTitle("Library Managment");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        new Thread(() -> DatabaseHandler.getInstance()).start();

    }

    public static void main(String[] args){
        launch(args);
    }
}
