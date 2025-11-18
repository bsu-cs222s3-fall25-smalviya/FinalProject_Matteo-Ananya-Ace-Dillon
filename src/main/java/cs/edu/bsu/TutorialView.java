package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class TutorialView extends BorderPane {

    public TutorialView() {
        Label titleLabel = new Label("Tutorial Videos");
        titleLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");

        Hyperlink javaFxBasicsLink = new Hyperlink("Rick Roll 1");
        javaFxBasicsLink.setOnAction(event ->
                openVideoInBrowser("https://youtu.be/dQw4w9WgXcQ?si=ZOZe22vcOwE9eh_0"));

        Hyperlink blackjackRulesLink = new Hyperlink("Rick Roll 2");
        blackjackRulesLink.setOnAction(event ->
                openVideoInBrowser("https://youtu.be/dQw4w9WgXcQ?si=ZOZe22vcOwE9eh_0"));

        Hyperlink rouletteRulesLink = new Hyperlink("Rick Roll 3");
        rouletteRulesLink.setOnAction(event ->
                openVideoInBrowser("https://youtu.be/dQw4w9WgXcQ?si=ZOZe22vcOwE9eh_0"));

        Button returnToMenuButton = new Button("Return to Menu");
        returnToMenuButton.setOnAction(event -> {
            if (getScene() != null) {
                getScene().setRoot(new MenuView());
            }
        });

        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(15));

        centerBox.getChildren().add(titleLabel);
        centerBox.getChildren().add(javaFxBasicsLink);
        centerBox.getChildren().add(blackjackRulesLink);
        centerBox.getChildren().add(rouletteRulesLink);
        centerBox.getChildren().add(returnToMenuButton);

        setCenter(centerBox);
        setPadding(new Insets(10));
    }

    private void openVideoInBrowser(String websiteAddress) {
        MainApp.getHostServicesReference().showDocument(websiteAddress);
    }
}
