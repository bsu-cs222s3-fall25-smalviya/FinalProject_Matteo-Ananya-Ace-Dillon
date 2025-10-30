package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WarView extends BorderPane {
    private final WarLogic war = new WarLogic();
    private final TextField betField = new TextField();

    private final Label playerCardLabel = new Label("You: —");
    private final Label dealerCardLabel = new Label("Dealer: —");
    private final Label outcomeLabel = new Label("Place a bet and press PLAY");

    public WarView() {
        Label title = new Label("🛡️️ War 🛡️");
        title.getStyleClass().add("title");

        Label player = new Label("Player: Guest");
        player.getStyleClass().add("stat");

        Label dot = new Label("•");
        dot.getStyleClass().add("stat-dot");

        Label coins = new Label("MAAD Coins: " + SlotsLogic.payout());
        coins.getStyleClass().add("stat");

        HBox statusBar = new HBox(10, player, dot, coins);
        statusBar.setAlignment(Pos.CENTER);
        statusBar.setPadding(new Insets(5, 0, 0, 0));

        Label betLbl = new Label("Bet:");
        betField.setPromptText("Enter whole number");
        betField.setPrefColumnCount(8);

        Button playBtn = new Button("PLAY WAR");
        playBtn.getStyleClass().add("green");
        playBtn.setOnAction(_ -> onPlay());

        HBox centerBar = new HBox(10, betLbl, betField, playBtn);
        centerBar.setAlignment(Pos.CENTER);
        centerBar.setPadding(new Insets(12));
        setCenter(centerBar);

        VBox titleBar = new VBox(12, title, statusBar, centerBar);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setPadding(new Insets(12));
        setTop(titleBar);

        playerCardLabel.getStyleClass().add("card-text");
        dealerCardLabel.getStyleClass().add("card-text");
        outcomeLabel.getStyleClass().add("outcome-text");

        Button backBtn = new Button("Return to Menu");
        backBtn.getStyleClass().add("red");
        backBtn.setOnAction(_ -> getScene().setRoot(new MenuView()));

        HBox bottomBar = new HBox(backBtn);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(12));
        setBottom(bottomBar);

        setPadding(new Insets(10));

        VBox centerBox = new VBox(12, playerCardLabel, dealerCardLabel, outcomeLabel);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(24));
        setCenter(centerBox);
    }

    private void onPlay() {
        String raw = betField.getText();
        int bet;
        try {
            bet = Integer.parseInt(raw.trim());
        } catch (Exception ex) {
            outcomeLabel.setText("Enter a whole number bet.");
            return;
        }
        if (bet <= 0) {
            outcomeLabel.setText("Bet must be greater than 0.");
            return;
        }

        WarLogic.RoundResult result = war.playRound(bet);

        playerCardLabel.setText("You: " + WarLogic.cardToString(result.playerCard));
        dealerCardLabel.setText("Dealer: " + WarLogic.cardToString(result.dealerCard));
        outcomeLabel.setText(result.summary(bet));
    }
}
