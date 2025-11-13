package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BlackjackView extends BorderPane {
    static TextField dealerOutput = new TextField();
    static TextField playerOutput = new TextField();

    static boolean wasClicked = false;

    public BlackjackView() {
        Label title = new Label("🂠 Blackjack 🂠");
        title.getStyleClass().add("title");

        Label player = new Label("Player: Guest");
        player.getStyleClass().add("stat");

        Label dot = new Label("•");
        dot.getStyleClass().add("stat-dot");

        Label coins = new Label("MAAD Coins: " + CoinBalance.gameBalance);
        coins.getStyleClass().add("stat");

        HBox statusBar = new HBox(10, player, dot, coins);
        statusBar.setAlignment(Pos.CENTER);
        statusBar.setPadding(new Insets(0, 0, 0, 0));

        Label matchOutcome = new Label("");
        Button hit = new Button("Hit");
        Button stand = new Button("Stand");
        Button doubleDown = new Button ("Double Down");

        hit.setDisable(true);
        stand.setDisable(true);
        doubleDown.setDisable(true);

        TextArea winLoseOutcome = new TextArea("");
        winLoseOutcome.setEditable(false);
        winLoseOutcome.setPrefSize(100, 100);

        TextField betField = new TextField("");
        betField.setPromptText("Place bet here...");

        Button back = new Button("Back to Menu");
        back.setOnAction(_ -> {
            CoinBalance.balance += CoinBalance.gameBalance;
            CoinBalance.gameBalance = 0;
            getScene().setRoot(new MenuView());
        });

        Button set = new Button("New Round");
        set.setOnAction(_ -> {
            String betAmount = betField.getText();
            System.out.println("betAmount " + betAmount);
            BlackjackLogic.setBet(Integer.parseInt(betAmount));
            BlackjackLogic.set();

            wasClicked = false;
            winLoseOutcome.setText(" ");

            set.setDisable(true);
            hit.setDisable(false);
            stand.setDisable(false);
            doubleDown.setDisable(false);
            dealerOutput.setText("[" + BlackjackLogic.dealerHand.getFirst() + ", ?]");
            BlackjackLogic.dealerHand.add(BlackjackLogic.dealersSecondCard);
            playerOutput.setText(String.valueOf(BlackjackLogic.playerHand));
            matchOutcome.setText("Dealer Hand: ?" + "\nPlayer Hand: " + BlackjackLogic.totalValueCalculatorPlayer());

            if (BlackjackLogic.push) {
                System.out.println("set: push\n" + true);
                winLoseOutcome.setText("Round got pushed...\nit's a tie");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.dealerBlackjack) {
                System.out.println("dealer blackjack\n" + true);
                dealerOutput.setText("" + BlackjackLogic.dealerHand);
                winLoseOutcome.setText("Dealer got a Blackjack...\nyou lose");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.playerBlackjack) {
                System.out.println("player blackjack\n" + true);
                winLoseOutcome.setText("You got a Blackjack...\nyou win!");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
                BlackjackLogic.dealerHand.add(BlackjackLogic.dealersSecondCard);
                dealerOutput.setText("" + BlackjackLogic.dealerHand);
            }
            BlackjackLogic.payoutCalculator();
            long newBalance = CoinBalance.gameBalance;
            coins.setText("MAAD Coins: " + newBalance);
        });

        Label dealerLabel = new Label("Dealer:");
        dealerOutput.setEditable(false);

        Label playerLabel = new Label("Player:");
        playerOutput.setEditable(false);

        doubleDown.setOnAction(_ -> {
            BlackjackLogic.doubleDownCalculator();
            dealerOutput.setText("[" + BlackjackLogic.dealerHand.getFirst() + ", ?]");
            playerOutput.setText(String.valueOf(BlackjackLogic.playerHand));
            matchOutcome.setText("Dealer Hand: ?" + "\nPlayer Hand: " + BlackjackLogic.totalValueCalculatorPlayer());
            if (BlackjackLogic.playerBust) {
                System.out.println("hit: player bust\n" + true);
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
                winLoseOutcome.setText("You got a bust...\nyou lose");
                BlackjackLogic.dealerHand.add(BlackjackLogic.dealersSecondCard);
                dealerOutput.setText("" + BlackjackLogic.dealerHand);
                matchOutcome.setText("Dealer Hand: " + BlackjackLogic.totalValueCalculatorDealer() +
                        "\nPlayer Hand: " + BlackjackLogic.totalValueCalculatorPlayer());
            } else if (BlackjackLogic.push) {
                System.out.println("hit: push\n" + true);
                winLoseOutcome.setText("Round got pushed...\nit's a tie");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.playerRegularWin) {
                System.out.println("hit: player win\n" + true);
                winLoseOutcome.setText("You have more than the dealer...\nyou win!");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.dealerRegularWin) {
                System.out.println("hit: dealer win\n" + true);
                winLoseOutcome.setText("The dealer has more...\nyou lose");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.totalValueCalculatorPlayer() == 21) {
                hit.setDisable(true);
                doubleDown.setDisable(true);
            }
            BlackjackLogic.payoutCalculator();
        });

        hit.setOnAction(_ -> {
            BlackjackLogic.playerHit();
            playerOutput.setText(" | " + BlackjackLogic.playerHand);
            matchOutcome.setText("Dealer Hand: ?" + "\nPlayer Hand: " + BlackjackLogic.totalValueCalculatorPlayer());
            if (BlackjackLogic.playerBust) {
                System.out.println("hit: player bust\n" + true);
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
                winLoseOutcome.setText("You got a bust...\nyou lose");
                BlackjackLogic.dealerHand.add(BlackjackLogic.dealersSecondCard);
                dealerOutput.setText("" + BlackjackLogic.dealerHand);
                matchOutcome.setText("Dealer Hand: " + BlackjackLogic.totalValueCalculatorDealer() +
                        "\nPlayer Hand: " + BlackjackLogic.totalValueCalculatorPlayer());
            } else if (BlackjackLogic.push) {
                System.out.println("hit: push\n" + true);
                winLoseOutcome.setText("Round got pushed...\nit's a tie");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.playerRegularWin) {
                System.out.println("hit: player win\n" + true);
                winLoseOutcome.setText("You have more than the dealer...\nyou win!");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.dealerRegularWin) {
                System.out.println("hit: dealer win\n" + true);
                winLoseOutcome.setText("The dealer has more...\nyou lose");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.totalValueCalculatorPlayer() == 21) {
                hit.setDisable(true);
                doubleDown.setDisable(true);
            }
            BlackjackLogic.payoutCalculator();
            long newBalance = CoinBalance.getGameBalance();
            coins.setText("MAAD Coins: " + newBalance);
        });

        stand.setOnAction(_ -> {
            set.setDisable(true);
            hit.setDisable(true);
            stand.setDisable(true);
            doubleDown.setDisable(true);
            BlackjackLogic.dealerHand.add(BlackjackLogic.dealersSecondCard);
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
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.dealerBust) {
                System.out.println("stand: dealer bust\n" + true);
                winLoseOutcome.setText("The dealer got a bust...\nyou win!");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.push) {
                System.out.println("stand: push\n" + true);
                winLoseOutcome.setText("Round got pushed...\nit's a tie");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.playerRegularWin) {
                System.out.println("stand: player win\n" + true);
                winLoseOutcome.setText("You have more than the dealer...\nyou win!");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else if (BlackjackLogic.dealerRegularWin) {
                System.out.println("stand: dealer win\n" + true);
                winLoseOutcome.setText("The dealer has more...\nyou lose");
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);
            } else {
                set.setDisable(false);
            }
            BlackjackLogic.payoutCalculator();
            long newBalance = CoinBalance.getGameBalance();
            coins.setText("MAAD Coins: " + newBalance);
        });

        HBox buttonRow = new HBox(10, hit, stand, doubleDown);

        VBox titleBar = new VBox(15, title, statusBar);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setPadding(new Insets(0));

        VBox bettingArea = new VBox(15, set, dealerLabel, dealerOutput, playerLabel, playerOutput, buttonRow, matchOutcome, winLoseOutcome, betField, back);
        bettingArea.setAlignment(Pos.CENTER);
        bettingArea.setPadding(new Insets(0));
        VBox.setMargin(bettingArea, Insets.EMPTY);

        setTop(titleBar);
        setCenter(bettingArea);
    }
}