package cs.edu.bsu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class MainApp extends Application{
    @Override
    public void start(Stage stage) {
        Font.loadFont(getClass().getResourceAsStream("/Fonts/BitcountGridSingle-VariableFont_CRSV,ELSH,ELXP,slnt,wght.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/Fonts/digital-7 (italic).ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/Fonts/digital-7 (mono italic).ttf"), 14);
        MenuView menuView = new MenuView();
        Scene scene = new Scene(new MenuView(), 850, 600);
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );
        stage.setTitle("MAAD Casino.exe");
        stage.setScene(scene);
        stage.show();

        System.out.println("=== AVAILABLE FONT FAMILIES ===");
        for (String name : Font.getFamilies()) {
            System.out.println(name);
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
