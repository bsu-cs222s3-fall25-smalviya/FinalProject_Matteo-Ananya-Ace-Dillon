package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class BlackjackView extends BorderPane {
    static boolean wasClicked = false;

    public BlackjackView() {
        Label title = new Label("🂠 Blackjack 🂠");
        title.getStyleClass().add("title");


        Label userName = new Label("Player: " + MenuView.currentUsername);
        userName.getStyleClass().add("stat");

        Label dot = new Label("•");
        dot.getStyleClass().add("stat-dot");

        Label userBalance = new Label("MAAD Coins: " + CoinBalance.gameBalance);
        userBalance.getStyleClass().add("stat");

        HBox statusBar = new HBox(10, userName, dot, userBalance);
        statusBar.setAlignment(Pos.CENTER);
        statusBar.setPadding(new Insets(0, 0, 0, 0));

        VBox header = new VBox(15, title, statusBar);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0));

        Label dealerLabel = new Label("Dealer");
        dealerLabel.getStyleClass().add("statsLabel");

        HBox dealerCardRow = new HBox();
        dealerCardRow.setSpacing(5);
        dealerCardRow.setAlignment(Pos.CENTER);
        dealerCardRow.getStyleClass().add("cardRows");

        HBox playerCardRow = new HBox();
        playerCardRow.setSpacing(5);
        playerCardRow.setAlignment(Pos.CENTER);
        playerCardRow.getStyleClass().add("cardRows");

        dealerCardRow.setPrefSize(550, 150);
        playerCardRow.setPrefSize(550, 150);

        Button set = new Button("New Round");
        Button hit = new Button("Hit");
        Button stand = new Button("Stand");
        Button doubleDown = new Button ("Double Down");

        set.getStyleClass().add("blackjackButtonSet");
        hit.getStyleClass().add("blackjackButtonRow");
        stand.getStyleClass().add("blackjackButtonRow");
        doubleDown.getStyleClass().add("blackjackButtonRow");

        set.setDisable(true);
        hit.setDisable(true);
        stand.setDisable(true);
        doubleDown.setDisable(true);

        HBox buttonRow = new HBox(15, hit, stand, doubleDown);
        buttonRow.setAlignment(Pos.CENTER);

        VBox buttonArea = new VBox(10, buttonRow, set);
        buttonArea.setAlignment(Pos.CENTER);

        VBox leftPanel = new VBox(dealerLabel, dealerCardRow, playerCardRow, new Region(), buttonArea);
        leftPanel.getStyleClass().add("leftPanelBlackjack");
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setPadding(new Insets(30, 0, 10, 25));
        VBox.setMargin(dealerLabel, new Insets(0, 0, 10, 0));
        VBox.setMargin(dealerCardRow, new Insets(0, 0, 10, 0));
        VBox.setMargin(buttonArea, new Insets(10, 0, 0, 0));

        VBox.setVgrow(leftPanel.getChildren().get(3), Priority.ALWAYS);

        Label statsLabel = new Label("Round Stats");
        statsLabel.setAlignment(Pos.CENTER);
        statsLabel.getStyleClass().add("statsLabel");

        Text dealerTitle = new Text("Dealer's Hand\n");
        dealerTitle.getStyleClass().add("titleStats");

        Text dealersHand = new Text("");
        Text totalLabel1 = new Text("Total:");
        Text dealerTotalHand = new Text("");
        dealersHand.getStyleClass().add("bodyText1");
        dealerTotalHand.getStyleClass().add("bodyText2");
        totalLabel1.getStyleClass().add("totalLabel");

        VBox dealerBodyBox = new VBox(0, dealersHand, totalLabel1, dealerTotalHand);
        VBox.setMargin(dealersHand, new Insets(10, 0, 10, 5));
        VBox.setMargin(totalLabel1, new Insets(0, 0, 0, 5));
        VBox.setMargin(dealerTotalHand, new Insets(10, 0, 0, 5));

        TextFlow dealersInfoBox = new TextFlow(dealerTitle, dealerBodyBox);
        dealersInfoBox.getStyleClass().add("dealerStats");

        Text playerTitle = new Text("Your Hand\n");
        playerTitle.getStyleClass().add("titleStats");

        Text playersHand = new Text("");
        Text totalLabel2 = new Text("Total:");
        Text playerTotalHand = new Text("");
        playersHand.getStyleClass().add("bodyText1");
        playerTotalHand.getStyleClass().add("bodyText2");
        totalLabel2.getStyleClass().add("totalLabel");

        VBox playerBodyBox = new VBox(0, playersHand, totalLabel2, playerTotalHand);
        VBox.setMargin(playersHand, new Insets(10, 0, 10, 5));
        VBox.setMargin(totalLabel2, new Insets(0, 0, 0, 5));
        VBox.setMargin(playerTotalHand, new Insets(10, 0, 0, 5));

        TextFlow playersInfoBox = new TextFlow(playerTitle, playerBodyBox);
        playersInfoBox.getStyleClass().add("playerStats");

        TextArea matchOutcome = new TextArea("");
        matchOutcome.getStyleClass().add("matchOutcome");
        matchOutcome.setEditable(false);

        VBox rightPanel = new VBox(0, statsLabel, dealersInfoBox, playersInfoBox, matchOutcome);
        rightPanel.setPadding(new Insets(0, 25, 0, 0));
        rightPanel.setAlignment(Pos.TOP_LEFT);

        VBox.setMargin(statsLabel, new Insets(30, 0, 10, 0));
        VBox.setMargin(matchOutcome, new Insets(25, 0, 25, 0));

        Button backButton = new Button("Back to Menu");
        backButton.getStyleClass().add("red");

        Label betAmountLabel = new Label("Bet Amount:");
        betAmountLabel.getStyleClass().add("betAmountLabel");

        TextField userBetInput = new TextField();
        userBetInput.setPromptText("Place bet here...");
        userBetInput.getStyleClass().add("userBetInput");

        Button instructionsButton = new Button("Instructions");
        instructionsButton.getStyleClass().add("purple");
        instructionsButton.setOnAction(_ -> InstructionsPopup.show(
                "Blackjack Instructions",
                """
                Blackjack Instructions
        
                The goal of Blackjack is to get closer to 21 than the dealer.
        
                Place your bet in the "Place your bet" box.
                Select the "New Round" button.
                Make your decision to Hit, Stand, or Double Down.
        
                Hit        - take another card.
                Stand      - stay with the current total of cards you have now.
                Double Down - take 1 card and double your bet amount.
        
                Read the result on the screen to know your payout.
                You may change your bet size or click "New Round" to play another hand.
                """
        ));


        VBox betArea = new VBox(4, betAmountLabel, userBetInput);
        betArea.setAlignment(Pos.CENTER);

        StackPane footer = new StackPane();
        footer.getStyleClass().add("footerBlackjack");
        footer.getChildren().addAll(betArea, backButton, instructionsButton);

        StackPane.setAlignment(backButton, Pos.CENTER_RIGHT);
        StackPane.setAlignment(instructionsButton, Pos.CENTER_LEFT);

        StackPane.setMargin(betArea, new Insets(0, 0, 0, 0));
        StackPane.setMargin(backButton, new Insets(20, 15, 15, 0));
        StackPane.setMargin(instructionsButton, new Insets(20, 0, 15, 15));


        backButton.setOnAction(_ -> {
            CoinBalance.balance += CoinBalance.gameBalance;
            CoinBalance.gameBalance = 0;

            AccountManager.updateBalance(MenuView.currentUsername, CoinBalance.balance);
            AccountManager.saveAccounts();
            getScene().setRoot(new MenuView());
        });

        userBetInput.textProperty().addListener((obs) -> {
            set.setDisable(false);
        });

        set.setOnAction(_ -> {
            set.setDisable(true);
            hit.setDisable(false);
            stand.setDisable(false);
            doubleDown.setDisable(false);

            String betAmount = userBetInput.getText();

            BlackjackLogic.setBet(Integer.parseInt(betAmount));
            BlackjackLogic.set();

            dealerCardRow.getChildren().clear();
            playerCardRow.getChildren().clear();

            ImageView dealerCard1 = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/PlayingCards/" + BlackjackLogic.dealerHandFiles.get(0))));
            dealerCard1.setFitWidth(85);
            dealerCard1.setPreserveRatio(true);

            ImageView dealerHidden = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/PlayingCards/HiddenCard.png")));
            dealerHidden.setFitWidth(85);
            dealerHidden.setPreserveRatio(true);

            dealerCardRow.getChildren().addAll(dealerCard1, dealerHidden);

            for (int i = 0; i < BlackjackLogic.playerHandFiles.size(); i++) {
                ImageView playerCard = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/PlayingCards/" + BlackjackLogic.playerHandFiles.get(i))));
                playerCard.setFitWidth(85);
                playerCard.setPreserveRatio(true);
                playerCardRow.getChildren().add(playerCard);
            }

            wasClicked = false;
            matchOutcome.setText("");
            matchOutcome.getStyleClass().remove("matchOutcomeWon");
            matchOutcome.getStyleClass().remove("matchOutcomeLost");
            matchOutcome.getStyleClass().remove("matchOutcomeTied");

            dealersHand.setText(BlackjackLogic.dealerHand.get(0) + " ?");
            playersHand.setText(formatHand(BlackjackLogic.playerHand));
            dealerTotalHand.setText(" ?");
            playerTotalHand.setText("" + BlackjackLogic.totalValueCalculatorPlayer());

            if (BlackjackLogic.dealerBlackjack && BlackjackLogic.playerBlackjack) {
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);

                BlackjackLogic.revealDealerSecondCard();
                dealerCardRow.getChildren().clear();
                for (int i = 0; i < BlackjackLogic.dealerHandFiles.size(); i++) {
                    ImageView dealerCard = new ImageView(new javafx.scene.image.Image(
                            getClass().getResourceAsStream("/images/PlayingCards/" + BlackjackLogic.dealerHandFiles.get(i))
                    ));
                    dealerCard.setFitWidth(85);
                    dealerCard.setPreserveRatio(true);
                    dealerCardRow.getChildren().add(dealerCard);
                }

                dealersHand.setText(formatHand(BlackjackLogic.dealerHand));
                playersHand.setText(formatHand(BlackjackLogic.playerHand));
                matchOutcome.setText("Round got pushed\n-\nIt's a tie");
                matchOutcome.getStyleClass().add("matchOutcomeTied");
            } else if (BlackjackLogic.dealerBlackjack) {
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);

                BlackjackLogic.revealDealerSecondCard();
                dealerCardRow.getChildren().clear();
                for (int i = 0; i < BlackjackLogic.dealerHandFiles.size(); i++) {
                    ImageView dealerCard = new ImageView(new javafx.scene.image.Image(
                            getClass().getResourceAsStream("/images/PlayingCards/" + BlackjackLogic.dealerHandFiles.get(i))
                    ));
                    dealerCard.setFitWidth(85);
                    dealerCard.setPreserveRatio(true);
                    dealerCardRow.getChildren().add(dealerCard);
                }

                dealersHand.setText(formatHand(BlackjackLogic.dealerHand));
                playersHand.setText(formatHand(BlackjackLogic.playerHand));
                matchOutcome.setText("Dealer got a Blackjack\n-\nYou lose");
                matchOutcome.getStyleClass().add("matchOutcomeLost");
            } else if (BlackjackLogic.playerBlackjack) {
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);

                BlackjackLogic.revealDealerSecondCard();
                dealerCardRow.getChildren().clear();
                for (int i = 0; i < BlackjackLogic.dealerHandFiles.size(); i++) {
                    ImageView dealerCard = new ImageView(new javafx.scene.image.Image(
                            getClass().getResourceAsStream("/images/PlayingCards/" + BlackjackLogic.dealerHandFiles.get(i))
                    ));
                    dealerCard.setFitWidth(85);
                    dealerCard.setPreserveRatio(true);
                    dealerCardRow.getChildren().add(dealerCard);
                }

                dealersHand.setText(formatHand(BlackjackLogic.dealerHand));
                playersHand.setText(formatHand(BlackjackLogic.playerHand));
                matchOutcome.setText("You got a Blackjack\n-\nYou win " + BlackjackLogic.payoutCalculator() + " MAAD Coins!");
                matchOutcome.getStyleClass().add("matchOutcomeWon");
            }

            BlackjackLogic.payoutCalculator();
            userBalance.setText("MAAD Coins: " + CoinBalance.gameBalance);
        });

        doubleDown.setOnAction(_ -> {
            BlackjackLogic.doubleDownCalculator();

            playerCardRow.getChildren().clear();
            for (String fileName : BlackjackLogic.playerHandFiles) {
                ImageView playerCard = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/PlayingCards/" + fileName)));
                playerCard.setFitWidth(85);
                playerCard.setPreserveRatio(true);
                playerCardRow.getChildren().add(playerCard);
            }

            userBalance.setText("MAAD Coins: " + CoinBalance.gameBalance);

            playersHand.setText(formatHand(BlackjackLogic.playerHand));
            dealerTotalHand.setText(" ?");
            playerTotalHand.setText("" + BlackjackLogic.totalValueCalculatorPlayer());

            if (BlackjackLogic.playerBust) {
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);

                BlackjackLogic.revealDealerSecondCard();
                dealerCardRow.getChildren().clear();
                for (int i = 0; i < BlackjackLogic.dealerHandFiles.size(); i++) {
                    ImageView dealerCard = new ImageView(new javafx.scene.image.Image(
                            getClass().getResourceAsStream("/images/PlayingCards/" + BlackjackLogic.dealerHandFiles.get(i))
                    ));
                    dealerCard.setFitWidth(85);
                    dealerCard.setPreserveRatio(true);
                    dealerCardRow.getChildren().add(dealerCard);
                }

                matchOutcome.setText("You got a bust\n-\nYou lose");
                matchOutcome.getStyleClass().add("matchOutcomeLost");

                dealersHand.setText("" + BlackjackLogic.dealerHand);
                dealerTotalHand.setText("" + BlackjackLogic.totalValueCalculatorDealer());
                playerTotalHand.setText("" + BlackjackLogic.totalValueCalculatorPlayer());
            } else if (BlackjackLogic.totalValueCalculatorPlayer() == 21) {
                hit.setDisable(true);
                doubleDown.setDisable(true);

                BlackjackLogic.revealDealerSecondCard();
            }

            BlackjackLogic.payoutCalculator();
            userBalance.setText("MAAD Coins: " + CoinBalance.getGameBalance());
        });

        hit.setOnAction(_ -> {
            BlackjackLogic.playerHit();

            playerCardRow.getChildren().clear();
            for (String fileName : BlackjackLogic.playerHandFiles) {
                ImageView playerCard = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("/images/PlayingCards/" + fileName)));
                playerCard.setFitWidth(85);
                playerCard.setPreserveRatio(true);
                playerCardRow.getChildren().add(playerCard);
            }

            playersHand.setText(formatHand(BlackjackLogic.playerHand));
            dealerTotalHand.setText(" ?");
            playerTotalHand.setText("" + BlackjackLogic.totalValueCalculatorPlayer());

            if (BlackjackLogic.playerBust) {
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);

                BlackjackLogic.revealDealerSecondCard();
                dealerCardRow.getChildren().clear();
                for (int i = 0; i < BlackjackLogic.dealerHandFiles.size(); i++) {
                    ImageView dealerCard = new ImageView(new javafx.scene.image.Image(
                            getClass().getResourceAsStream("/images/PlayingCards/" + BlackjackLogic.dealerHandFiles.get(i))
                    ));
                    dealerCard.setFitWidth(85);
                    dealerCard.setPreserveRatio(true);
                    dealerCardRow.getChildren().add(dealerCard);
                }

                matchOutcome.setText("You got a bust\n-\nYou lose");
                matchOutcome.getStyleClass().add("matchOutcomeLost");

                dealersHand.setText(formatHand(BlackjackLogic.dealerHand));
                dealerTotalHand.setText("" + BlackjackLogic.totalValueCalculatorDealer());
                playerTotalHand.setText("" + BlackjackLogic.totalValueCalculatorPlayer());
            } else if (BlackjackLogic.totalValueCalculatorPlayer() == 21) {
                hit.setDisable(true);
                doubleDown.setDisable(true);

                BlackjackLogic.revealDealerSecondCard();
            }

            BlackjackLogic.payoutCalculator();
            userBalance.setText("MAAD Coins: " + CoinBalance.getGameBalance());
        });

        stand.setOnAction(_ -> {
            set.setDisable(true);
            hit.setDisable(true);
            stand.setDisable(true);
            doubleDown.setDisable(true);

            BlackjackLogic.revealDealerSecondCard();
            BlackjackLogic.dealerHit();
            dealerCardRow.getChildren().clear();
            for (int i = 0; i < BlackjackLogic.dealerHandFiles.size(); i++) {
                ImageView dealerCard = new ImageView(new javafx.scene.image.Image(
                        getClass().getResourceAsStream("/images/PlayingCards/" + BlackjackLogic.dealerHandFiles.get(i))
                ));
                dealerCard.setFitWidth(85);
                dealerCard.setPreserveRatio(true);
                dealerCardRow.getChildren().add(dealerCard);
            }

            dealersHand.setText(formatHand(BlackjackLogic.dealerHand));
            playersHand.setText(formatHand(BlackjackLogic.playerHand));
            dealerTotalHand.setText("" + BlackjackLogic.totalValueCalculatorDealer());
            playerTotalHand.setText("" + BlackjackLogic.totalValueCalculatorPlayer());

            if (BlackjackLogic.playerBust) {
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);

                matchOutcome.setText("You got a bust\n-\nYou lose");
                matchOutcome.getStyleClass().add("matchOutcomeLost");
            } else if (BlackjackLogic.dealerBust) {
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);

                matchOutcome.setText("The dealer got a bust\n-\nYou win " + BlackjackLogic.payoutCalculator() + " MAAD Coins!");
                matchOutcome.getStyleClass().add("matchOutcomeWon");
            } else if (BlackjackLogic.push) {
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);

                matchOutcome.setText("Round got pushed\n-\nIt's a tie");
                matchOutcome.getStyleClass().add("matchOutcomeTied");
            } else if (BlackjackLogic.playerRegularWin) {
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);

                matchOutcome.setText("You outscored the dealer\n-\nYou win " + BlackjackLogic.payoutCalculator() + " MAAD Coins!");
                matchOutcome.getStyleClass().add("matchOutcomeWon");
            } else if (BlackjackLogic.dealerRegularWin) {
                set.setDisable(false);
                hit.setDisable(true);
                stand.setDisable(true);
                doubleDown.setDisable(true);

                matchOutcome.setText("The dealer has more\n-\nYou lose");
                matchOutcome.getStyleClass().add("matchOutcomeLost");
            } else {
                set.setDisable(false);
            }

            BlackjackLogic.payoutCalculator();
            userBalance.setText("MAAD Coins: " + CoinBalance.getGameBalance());
        });

        setTop(header);
        setLeft(leftPanel);
        setRight(rightPanel);
        setBottom(footer);
    }

    private String formatHand(ArrayList<Integer> hand) {
        StringBuilder sb = new StringBuilder();
        for (int card : hand) {
            sb.append(card).append("  ");
        }

        return sb.toString().trim();
    }
}