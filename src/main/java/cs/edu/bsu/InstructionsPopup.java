package cs.edu.bsu;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstructionsPopup {

    public static void show(String titleText, String instructionsText) {
        Stage instructionsStage = new Stage();
        instructionsStage.setTitle(titleText);

        Label label = new Label(instructionsText);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        VBox box = new VBox(label);
        box.setStyle("-fx-background-color: #1c1c1c;");
        box.setSpacing(10);
        box.setPadding(new javafx.geometry.Insets(10));

        ScrollPane scroll = new ScrollPane(box);
        scroll.setFitToWidth(true);

        Scene scene = new Scene(scroll, 400, 300);
        instructionsStage.setScene(scene);
        instructionsStage.show();
    }
}
