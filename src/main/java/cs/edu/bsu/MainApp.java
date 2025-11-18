package cs.edu.bsu;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Objects;

import static javafx.application.Application.launch;

public class MainApp extends Application{
    @Override
    public void start(Stage stage) {
        hostServicesReference = getHostServices();

        Font.loadFont(getClass().getResourceAsStream("/Fonts/BitcountGridSingle-VariableFont_CRSV,ELSH,ELXP,slnt,wght.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/Fonts/digital-7 (italic).ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/Fonts/digital-7 (mono italic).ttf"), 14);
        MenuView menuView = new MenuView();
        Scene scene = new Scene(new MenuView(), 850, 620);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm()
        );
        stage.setTitle("MAAD Casino.exe");
        stage.setScene(scene);
        stage.show();
    }

    private static HostServices hostServicesReference;

    public static HostServices getHostServicesReference() {
        return hostServicesReference;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

