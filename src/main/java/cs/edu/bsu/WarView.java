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
        Label betLbl = new Label("Bet:");
        betField.setPromptText("Enter whole number");
        betField.setPrefColumnCount(8);

        Button playBtn = new Button("PLAY WAR");
        playBtn.getStyleClass().add("green");
        playBtn.setOnAction(e -> onPlay());

        HBox topBar = new HBox(10, betLbl, betField, playBtn);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(12));
        setTop(topBar);

        playerCardLabel.getStyleClass().add("card-text");
        dealerCardLabel.getStyleClass().add("card-text");
        outcomeLabel.getStyleClass().add("outcome-text");

        VBox centerBox = new VBox(12, playerCardLabel, dealerCardLabel, outcomeLabel);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(24));
        setCenter(centerBox);

        Button backBtn = new Button("Return to Menu");
        backBtn.getStyleClass().add("red");
        backBtn.setOnAction(e -> getScene().setRoot(new MenuView()));

        HBox bottomBar = new HBox(backBtn);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(12));
        setBottom(bottomBar);

        setPadding(new Insets(10));
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
