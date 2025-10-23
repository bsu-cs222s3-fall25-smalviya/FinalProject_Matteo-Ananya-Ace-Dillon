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
        Scene scene = new Scene(new MenuView(), 800, 500);
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );
        stage.setTitle("MAAD Casino.exe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
