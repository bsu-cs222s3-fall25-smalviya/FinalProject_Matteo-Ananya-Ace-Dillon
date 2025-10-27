package cs.edu.bsu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class MainApp extends Application{
    @Override
    public void start(Stage stage) {
        Font.loadFont(getClass().getResourceAsStream("/BitcountGridSingle-VariableFont_CRSV,ELSH,ELXP,slnt,wght.ttf"), 14);
        MenuView menuView = new MenuView();
        Scene scene = new Scene(menuView, 800,500);
        stage.setTitle("MAAD Casino.exe");
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
