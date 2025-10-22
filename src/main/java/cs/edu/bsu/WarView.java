// ==============================
// File: WarView.java
// Package: cs.edu.bsu
// ==============================
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
    private final Button dealBtn = new Button("Deal");
    private final Button backBtn = new Button("Back to Menu");

    private final Label playerCardLabel = new Label("-");
    private final Label dealerCardLabel = new Label("-");
    private final Label outcomeLabel   = new Label("");

    public WarView() {
        setPadding(new Insets(16));

        Label title = new Label("WAR (1:1 Payout)");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        // Controls
        betField.setPromptText("Enter bet (chips)");
        betField.setPrefWidth(120);

        // Wire actions to the SAME button instance that we add to the layout
        dealBtn.setOnAction(e -> onDeal());
        backBtn.setOnAction(e -> {
            getScene().setRoot(new MenuView());
        });


        HBox controls = new HBox(10, new Label("Bet:"), betField, dealBtn, backBtn);
        controls.setAlignment(Pos.CENTER_LEFT);

        VBox left = new VBox(10, controls);
        left.setPadding(new Insets(10));
        setLeft(left);

        // Card display
        playerCardLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        dealerCardLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        outcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox playerBox = new VBox(6, new Label("You"), playerCardLabel);
        playerBox.setAlignment(Pos.CENTER);
        VBox dealerBox = new VBox(6, new Label("Dealer"), dealerCardLabel);
        dealerBox.setAlignment(Pos.CENTER);

        HBox table = new HBox(40, playerBox, dealerBox);
        table.setAlignment(Pos.CENTER);

        VBox center = new VBox(16, table, outcomeLabel);
        center.setAlignment(Pos.CENTER);
        setCenter(center);
    }

    private void onDeal() {
        int bet;
        try {
            String raw = betField.getText();
            if (raw == null || raw.isBlank()) {
                outcomeLabel.setText("Enter a bet amount first.");
                return;
            }
            bet = Integer.parseInt(raw.trim());
        } catch (NumberFormatException nfe) {
            outcomeLabel.setText("Bruh try a number this time");
            return;
        }
        if (bet <= 0) {
            outcomeLabel.setText("Bruh try a number this time");
            return;
        }

        WarLogic.RoundResult result = war.playRound(bet);

        playerCardLabel.setText(WarLogic.cardToString(result.playerCard));
        dealerCardLabel.setText(WarLogic.cardToString(result.dealerCard));
        outcomeLabel.setText(result.summary(bet));
    }
}
