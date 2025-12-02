package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TutorialView extends BorderPane {

    public TutorialView() {
        setStyle(
                "-fx-background-color: radial-gradient(center 50% 0%, radius 120%, #004000, #001000);"
        );
        setPadding(new Insets(8));

        Label titleLabel = new Label("GAME TUTORIALS");
        titleLabel.setFont(Font.font("Bitcount Grid Single", 56));
        titleLabel.setTextFill(Color.web("#FFD700"));

        Label subtitle = new Label("Watch these quick videos before you bet your MAAD Coins.");
        subtitle.setTextFill(Color.web("#FFFFFF"));
        subtitle.setFont(Font.font("digital-7 (italic)", 24));

        VBox titleBox = new VBox(3, titleLabel, subtitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10, 10, 14, 10));
        titleBox.setStyle(
                "-fx-background-color: linear-gradient(to right, #004000, #006b38);"
        );
        setTop(titleBox);

        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(14, 22, 14, 22));
        card.setStyle(
                "-fx-background-color: rgba(0,0,0,0.85);" +
                        "-fx-background-radius: 18;" +
                        "-fx-border-color: #FF0000;" +
                        "-fx-border-radius: 18;" +
                        "-fx-border-width: 2;"
        );

        Label chooseLabel = new Label("Pick a tutorial to open in your browser:");
        chooseLabel.setTextFill(Color.web("#FFD700"));
        chooseLabel.setFont(Font.font("digital-7 (mono)", 25));

        Hyperlink blackjackLink = createTutorialLink(
                "🃏  How To Play Blackjack",
                "https://youtu.be/eyoh-Ku9TCI?si=b72FARAlVvsBi4ee"
        );

        Hyperlink warLink = createTutorialLink(
                "⚔️  How To Play War",
                "https://youtu.be/03s-hH-DE7E?si=sH85SaCp6d1lcNQ5"
        );

        Hyperlink rouletteLink = createTutorialLink(
                "🎡  How To Play Roulette",
                "https://youtu.be/wRciBlaiCMU?si=-fPxGo-xuxj52d59"
        );

        card.getChildren().addAll(chooseLabel, blackjackLink, warLink, rouletteLink);

        VBox centerBox = new VBox(12, card);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(16, 10, 10, 10));

        setCenter(centerBox);

        Button returnToMenuButton = new Button("Back to Menu");
        returnToMenuButton.getStyleClass().add("black");
        returnToMenuButton.setOnAction(event -> {
            if (getScene() != null) {
                getScene().setRoot(new MenuView());
            }
        });

        HBox bottomBar = new HBox(returnToMenuButton);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(6, 10, 10, 10));

        setBottom(bottomBar);
    }

    private Hyperlink createTutorialLink(String text, String url) {
        Hyperlink link = new Hyperlink(text);
        link.setTextFill(Color.web("#FFD700"));
        link.setFont(Font.font("digital-7 (mono)", 17));
        link.setBorder(null);
        link.setPadding(new Insets(2, 0, 2, 0));

        String baseStyle =
                "-fx-text-fill: #FFD700;" +
                        "-fx-underline: false;" +
                        "-fx-cursor: hand;";
        String hoverStyle =
                "-fx-text-fill: #FFFFFF;" +
                        "-fx-underline: true;" +
                        "-fx-cursor: hand;";

        link.setStyle(baseStyle);
        link.setOnAction(_ -> openVideoInBrowser(url));
        link.setOnMouseEntered(e -> link.setStyle(hoverStyle));
        link.setOnMouseExited(e -> link.setStyle(baseStyle));

        return link;
    }

    private void openVideoInBrowser(String websiteAddress) {
        MainApp.getHostServicesReference().showDocument(websiteAddress);
    }
}
