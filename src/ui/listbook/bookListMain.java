package ui.listbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class bookListMain extends Application {
    //https://www.youtube.com/watch?v=qK-yD7gfX7Y&list=PLhs1urmduZ29jTcE1ca8Z6bZNvH_39ayL&index=9&t=0s
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("listBookForm.fxml"));
        stage.setTitle("Book list");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
