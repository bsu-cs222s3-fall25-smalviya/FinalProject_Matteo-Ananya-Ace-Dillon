package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class BlackjackView extends BorderPane {
    static TextField dealerOutput = new TextField();
    static TextField playerOutput = new TextField();

    static Label playerLabel = new Label("Player:");
    public BlackjackView() {
        Label title = new Label("🂠 Blackjack 🂠");
        title.getStyleClass().add("title");

        Label player = new Label("Player: Guest");
        player.getStyleClass().add("stat");

        Label dot = new Label("•");
        dot.getStyleClass().add("stat-dot");

        Label coins = new Label("MAAD Coins: " + SlotsLogic.payout());
        coins.getStyleClass().add("stat");

        HBox statusBar = new HBox(10, player, dot, coins);
        statusBar.setAlignment(Pos.CENTER);
        statusBar.setPadding(new Insets(0, 0, 0, 0));

        Button play = new Button("Refresh round");
        play.setOnAction(_ -> {
            BlackjackLogic.play();
            dealerOutput.setText(String.valueOf(BlackjackLogic.dealerHand));
            playerOutput.setText(String.valueOf(BlackjackLogic.playerHand));
        });

        Label dealerLabel = new Label("Dealer:");
        dealerOutput.setEditable(false);

        Label playerLabel = new Label("Player:");
        playerOutput.setEditable(false);

        Button hit = new Button("Hit");
        hit.setOnAction(_ -> {
            BlackjackLogic.playerHit();
            playerOutput.appendText(" | " + BlackjackLogic.playerHit());
        });
        Button stand = new Button("Stand");
        stand.setOnAction(_ -> getScene().setRoot(new MenuView()));

        HBox buttonRow = new HBox(10, hit, stand);

        Label matchOutcome = new Label("");

        VBox titleBar = new VBox(15, title, statusBar);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setPadding(new Insets(0));

        VBox bettingArea = new VBox(15, play, dealerLabel, dealerOutput, playerLabel, playerOutput, buttonRow, matchOutcome);
        bettingArea.setAlignment(Pos.CENTER);
        bettingArea.setPadding(new Insets(100));

        setTop(titleBar);
        setCenter(bettingArea);
    }
}
