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

        Hyperlink javaFxBasicsLink = new Hyperlink("How To Play Blackjack");
        javaFxBasicsLink.setOnAction(event ->
                openVideoInBrowser("https://youtu.be/eyoh-Ku9TCI?si=b72FARAlVvsBi4ee"));

        Hyperlink blackjackRulesLink = new Hyperlink("How To Play War");
        blackjackRulesLink.setOnAction(event ->
                openVideoInBrowser("https://youtu.be/03s-hH-DE7E?si=sH85SaCp6d1lcNQ5"));

        Hyperlink rouletteRulesLink = new Hyperlink("How To Play Roulette");
        rouletteRulesLink.setOnAction(event ->
                openVideoInBrowser("https://youtu.be/wRciBlaiCMU?si=-fPxGo-xuxj52d59"));

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
