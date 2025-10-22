package cs.edu.bsu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WarView extends VBox {

    public WarView() {
        Label title = new Label("🂡 War 🂱");
        title.getStyleClass().add("title");

        Button back = new Button("Return to Menu");
        back.getStyleClass().add("red");
        back.setOnAction(e -> getScene().setRoot(new MenuView()));

        setAlignment(Pos.CENTER);
        setSpacing(20);
        getChildren().addAll(title, back);
    }
}
