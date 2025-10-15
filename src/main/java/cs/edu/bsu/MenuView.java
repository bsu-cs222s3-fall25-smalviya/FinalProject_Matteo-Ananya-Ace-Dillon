package cs.edu.bsu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MenuView extends BorderPane {
    public MenuView() {
        Label title = new Label("MAAD Casino");
        setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        Button blackjackButton = new Button("Blackjack");
        Button warButton = new Button("War");

        VBox box = new VBox(10, blackjackButton, warButton);
        box.setAlignment(Pos.CENTER);
        setCenter(box);
    }
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}