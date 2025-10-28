package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class WarDisplay extends BorderPane {

    private final Stage stage;

    public WarDisplay(Stage stage ) {
        this.stage = stage;

        setPadding(new Insets(20));
        setStyle("-fx-background-color: #2e2e2e;");

        Label title = new Label("⚔️ WAR GAME ⚔️");
        title.setStyle("-fx-font-size: 26px; -fx-text-fill: white; -fx-font-weight: bold;");
        BorderPane.setAlignment(title, Pos.CENTER);
        setTop(title);

        WarView warView = new WarView();
        setCenter(warView);

        Button backBtn = new Button("Back to Menu");
        backBtn.setStyle("-fx-font-size: 14px;");
        backBtn.setOnAction(e -> {
            Scene scene = getScene();
            scene.setRoot(new MenuView());

            if (scene.getStylesheets().isEmpty()) {
                scene.getStylesheets().add(
                        getClass().getResource("/style.css").toExternalForm()
                );
            }
        });

        VBox bottomBox = new VBox(backBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15));
        setBottom(bottomBox);
    }
}
