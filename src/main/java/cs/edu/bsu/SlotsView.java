package cs.edu.bsu;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SlotsView extends BorderPane {
    // connects SlotsLogic class
    private final SlotsLogic slots = new SlotsLogic();

    // back to menu
    private final Button backBtn = new Button("Back to Menu");
    // spin button
    private final Button spin = new Button("Spin");
    // title
    private final Label title = new Label("Slots");
    private final Label outcomeLabel = new Label("");


    public SlotsView() {
        Label title = new Label("Slots");
        setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        spin.setOnAction(e -> {
            //SlotsLogic.run();
        });

        getChildren().add(spin);
    }

}
