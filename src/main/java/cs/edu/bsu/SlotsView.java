package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextArea;

public class SlotsView extends BorderPane {
    private final SlotsLogic logic = new SlotsLogic();
    private final TextArea outputBox = new TextArea();

    public SlotsView() {
        // title
        Label title = new Label("🎰 Slots 🎰");
        title.getStyleClass().add("title");

        // back button
        Button back = new Button("Return to Menu");
        back.getStyleClass().add("red");
        back.setOnAction(e -> getScene().setRoot(new MenuView()));

        // spin button
        Button spin = new Button("Spin");
        spin.getStyleClass().add("black");
        spin.setOnAction(e -> {
            // SlotsLogic call
            String result = logic.spin(); // logic returns result text
            outputBox.setText(result + "\n");
        });

        // output area
        outputBox.setEditable(false);
        outputBox.setWrapText(true);
        outputBox.setPrefHeight(40);
        outputBox.setPrefWidth(10);
        outputBox.getStyleClass().add("output-box");

        // layout areas

        VBox center = new VBox(20, title, outputBox, spin, back);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(30));
        setCenter(center);
    }
}
