package cs.edu.bsu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application{
    @Override
    public void start(Stage stage) {
        MenuView menuView = new MenuView();
        Scene scene = new Scene(menuView, 500,300);
        stage.setTitle("MAADs Casino");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
