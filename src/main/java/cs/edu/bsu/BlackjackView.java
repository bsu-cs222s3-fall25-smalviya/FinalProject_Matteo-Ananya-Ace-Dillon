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

    static boolean wasClicked = false;


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

        Label matchOutcome = new Label("");
        Button hit = new Button("Hit");
        Button stand = new Button("Stand");

        hit.setDisable(true);
        stand.setDisable(true);

        TextArea winLoseOutcome = new TextArea("");
        winLoseOutcome.setEditable(false);

        TextField betField = new TextField("");
        betField.setPromptText("Place bet here...");

        // ----------------------------------------------

        Button set = new Button("New Round");
        set.setOnAction(_ -> {
            BlackjackLogic.setBet(Integer.parseInt(betField.getText()));
            CoinBalance.balance = BlackjackLogic.payoutCalculator();

            wasClicked = false;
            winLoseOutcome.setText(" ");
            BlackjackLogic.set();
            set.setDisable(true);
            hit.setDisable(false);
            stand.setDisable(false);
            dealerOutput.setText(String.valueOf(BlackjackLogic.dealerHand));
            playerOutput.setText(String.valueOf(BlackjackLogic.playerHand));
            matchOutcome.setText("Dealer Hand: " + BlackjackLogic.totalValueCalculatorDealer() +
            "\nPlayer Hand: " + BlackjackLogic.totalValueCalculatorPlayer());

            if (BlackjackLogic.push) {
                System.out.println("set: push\n" + true);
                winLoseOutcome.setText("Round got pushed...\nit's a tie");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            } else if (BlackjackLogic.dealerBlackjack && BlackjackLogic.playerBlackjack) {
                System.out.println("both get blackjack\n" + true);
                winLoseOutcome.setText("Both of you got a Blackjack...\nits a tie");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            } else if (BlackjackLogic.dealerBlackjack) {
                System.out.println("dealer blackjack\n" + true);
                winLoseOutcome.setText("Dealer got a Blackjack...\nyou lose");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            } else if (BlackjackLogic.playerBlackjack) {
                System.out.println("player blackjack\n" + true);
                winLoseOutcome.setText("You got a Blackjack...\nyou win!");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            }
            BlackjackLogic.payoutCalculator();
        });

        Label dealerLabel = new Label("Dealer:");
        dealerOutput.setEditable(false);

        Label playerLabel = new Label("Player:");
        playerOutput.setEditable(false);


        hit.setOnAction(_ -> {
            BlackjackLogic.playerHit();
            playerOutput.setText(" | " + BlackjackLogic.playerHand);
            matchOutcome.setText("Dealer Hand: " + BlackjackLogic.totalValueCalculatorDealer() +
                    "\nPlayer Hand: " + BlackjackLogic.totalValueCalculatorPlayer());
            if (BlackjackLogic.playerBust) {
                System.out.println("hit: player bust\n" + true);
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                winLoseOutcome.setText("You got a bust...\nyou lose");
            } else if (BlackjackLogic.push) {
                System.out.println("hit: push\n" + true);
                winLoseOutcome.setText("Round got pushed...\nit's a tie");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            } else if (BlackjackLogic.playerRegularWin) {
                System.out.println("hit: player win\n" + true);
                winLoseOutcome.setText("You have more than the dealer...\nyou win!");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            } else if (BlackjackLogic.dealerRegularWin) {
                System.out.println("hit: dealer win\n" + true);
                winLoseOutcome.setText("The dealer has more...\nyou lose");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            }
            BlackjackLogic.payoutCalculator();
            System.out.println(BlackjackLogic.balance);
        });

        stand.setOnAction(_ -> {
            wasClicked = true;
            set.setDisable(true);
            hit.setDisable(true);
            stand.setDisable(true);
            BlackjackLogic.dealerHit();

            dealerOutput.setText("" + BlackjackLogic.dealerHand);
            matchOutcome.setText("Dealer Hand: " + BlackjackLogic.totalValueCalculatorDealer() +
                    "\nPlayer Hand: " + BlackjackLogic.totalValueCalculatorPlayer());
            if (BlackjackLogic.playerBust) {
                System.out.println("stand: player bust\n" + true);
                winLoseOutcome.setText("You got a bust...\nyou lose");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            } else if (BlackjackLogic.dealerBust) {
                System.out.println("stand dealer bust\n" + true);
                winLoseOutcome.setText("The dealer got a bust...\nyou win!");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            } else if (BlackjackLogic.push) {
                System.out.println("stand: push\n" + true);
                winLoseOutcome.setText("Round got pushed...\nit's a tie");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            } else if (BlackjackLogic.playerRegularWin) {
                System.out.println("stand: player win\n" + true);
                winLoseOutcome.setText("You have more than the dealer...\nyou win!");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            } else if (BlackjackLogic.dealerRegularWin) {
                System.out.println("stand: dealer win\n" + true);
                winLoseOutcome.setText("The dealer has more...\nyou lose");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
            } else {
                set.setDisable(false);
            }
            BlackjackLogic.payoutCalculator();
        });

        HBox buttonRow = new HBox(10, hit, stand);

        VBox titleBar = new VBox(15, title, statusBar);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setPadding(new Insets(0));

        VBox bettingArea = new VBox(15, set, dealerLabel, dealerOutput, playerLabel, playerOutput, buttonRow, matchOutcome, winLoseOutcome, betField);
        bettingArea.setAlignment(Pos.CENTER);
        bettingArea.setPadding(new Insets(0));
        VBox.setMargin(bettingArea, Insets.EMPTY);


        setTop(titleBar);
        setCenter(bettingArea);
    }
}