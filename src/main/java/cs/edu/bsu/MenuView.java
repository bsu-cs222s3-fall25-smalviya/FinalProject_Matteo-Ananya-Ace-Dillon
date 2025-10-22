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
        title.getStyleClass().add("title");
        setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        Button blackjack = new Button("Blackjack");
        blackjack.getStyleClass().add("black");

        Button war = new Button("War");
        war.getStyleClass().add("red");

        Button horseracing = new Button("Horse Racing");
        horseracing.getStyleClass().add("black");

        Button slots = new Button("Slots");
        slots.getStyleClass().add("red");

        Button roulette = new Button("Roulette");
        roulette.getStyleClass().add("black");


        VBox box = new VBox(10, blackjack, war, horseracing, slots, roulette);
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

//TEST BY MATTEO