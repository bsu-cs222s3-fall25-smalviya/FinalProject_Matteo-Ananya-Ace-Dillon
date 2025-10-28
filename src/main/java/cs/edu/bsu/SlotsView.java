package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SlotsView extends BorderPane {
    private final HBox symbolOutput = new HBox(10);
    private final TextArea scoreOutput = new TextArea();
    private final TextField userBet = new TextField();
    private Button activeButton = null;

    public SlotsView() {
        // title
        Label title = new Label("🎰 Slots 🎰");
        title.getStyleClass().add("title");

        // player info
        Label player = new Label("Player: Guest");
        player.getStyleClass().add("stat");

        Label dot = new Label("•");
        dot.getStyleClass().add("stat-dot");

        Label coins = new Label("MAAD Coins: " + SlotsLogic.payout());
        coins.getStyleClass().add("stat");

        HBox statusBar = new HBox(10, player, dot, coins);
        statusBar.setAlignment(Pos.CENTER);
        statusBar.setPadding(new Insets(0, 0, 0, 0));

        // label for bet amount
        userBet.getStyleClass().add("userBet");

        // output area for symbols
        symbolOutput.getStyleClass().add("symbolOutput");

        // output for coins won/lost, and match indicator
        scoreOutput.setEditable(false);
        scoreOutput.getStyleClass().add("scoreOutput");

        // spin button
        Button spin = new Button("Spin");
        spin.getStyleClass().add("black");
        spin.setDisable(true);
        spin.setOnAction(e -> {
            // SlotsLogic call
            boolean insufficient = SlotsLogic.spin();
            if (insufficient) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Message from the Devs:");
                alert.setHeaderText(null);
                alert.setContentText("You don’t have enough MAAD Coins to spin!");
                alert.showAndWait();
                return;
            }

            long newBalance = SlotsLogic.payout();
            CoinBalance.balance = newBalance;
            // test system balance
            //System.out.println(CoinBalance.balance);

            String symbols = SlotsLogic.spinResults(); // returns the three symbols
            String result = SlotsLogic.score(); // returns match indictor and coins won/lost

            // style depending on match result
            if (SlotsLogic.score().equals("No match...\n")) {
                scoreOutput.getStyleClass().removeAll("scoreOutput");
                scoreOutput.getStyleClass().add("scoreOutputLose");
            } else {
                scoreOutput.getStyleClass().removeAll("scoreOutputLose");
                scoreOutput.getStyleClass().add("scoreOutput");
            }

            // display logic
            symbolOutput.getChildren().clear();

            String[] parts = symbols.split(" ");

            for (String s : parts) {
                if (s.equals("⑦")) {
                    ImageView img = new ImageView(
                            new Image(getClass().getResource("/images/LuckySeven.png").toExternalForm())
                    );
                    img.setFitWidth(50);
                    img.setPreserveRatio(true);
                    symbolOutput.getChildren().add(img);
                } else {
                    Label lbl = new Label(s);
                    lbl.setStyle("-fx-font-size: 50px; -fx-text-fill: yellow;");
                    symbolOutput.getChildren().add(lbl);
                }
            }

            scoreOutput.setText(result);
            coins.setText("MAAD Coins: " + newBalance);

            SlotsLogic.symbol1 = null;
            SlotsLogic.symbol2 = null;
            SlotsLogic.symbol3 = null;
        });

        // bet buttons
        Button oneCoin = new Button("1");
        Button tenCoin = new Button("10");
        Button fiftyCoin = new Button("50");
        Button hundredCoin = new Button("100");

        oneCoin.getStyleClass().add("betButton");
        tenCoin.getStyleClass().add("betButton");
        fiftyCoin.getStyleClass().add("betButton");
        hundredCoin.getStyleClass().add("betButton");


        oneCoin.setOnAction(e -> {
            SlotsLogic.setBet(1);
            userBet.setText("Bet amount: 1 MAAD Coin");
            spin.setDisable(false);
            if (activeButton != null) { // if there is an active button
                activeButton.getStyleClass().remove("betButtonPressed");
            }
            oneCoin.getStyleClass().add("betButtonPressed");
            activeButton = oneCoin;
        });
        tenCoin.setOnAction(e -> {
            SlotsLogic.setBet(10);
            userBet.setText("Bet amount: 10 MAAD Coins");
            spin.setDisable(false);
            if (activeButton != null) {
                activeButton.getStyleClass().remove("betButtonPressed");
            }
            tenCoin.getStyleClass().add("betButtonPressed");
            activeButton = tenCoin;
        });
        fiftyCoin.setOnAction(e -> {
            SlotsLogic.setBet(50);
            userBet.setText("Bet amount: 50 MAAD Coins");
            spin.setDisable(false);
            if (activeButton != null) {
                activeButton.getStyleClass().remove("betButtonPressed");
            }
            fiftyCoin.getStyleClass().add("betButtonPressed");
            activeButton = fiftyCoin;
        });
        hundredCoin.setOnAction(e -> {
            SlotsLogic.setBet(100);
            userBet.setText("Bet amount: 100 MAAD Coins");
            spin.setDisable(false);
            if (activeButton != null) {
                activeButton.getStyleClass().remove("betButtonPressed");
            }
            hundredCoin.getStyleClass().add("betButtonPressed");
            activeButton = hundredCoin;
        });


        //Label betAmountLabel = new Label("Bet amount: " + betAmount + " coins");

        // back button
        Button back = new Button("Return to Menu");
        back.getStyleClass().add("red");
        back.setOnAction(e -> getScene().setRoot(new MenuView()));

        // layout areas
        HBox buttonRow = new HBox(10, oneCoin, tenCoin, fiftyCoin, hundredCoin, spin);
        buttonRow.setAlignment(Pos.CENTER);
        setTop(buttonRow);

        VBox center = new VBox(15, title, statusBar, symbolOutput, scoreOutput, userBet, buttonRow, back);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(0));

        VBox.setMargin(title, new Insets(10, 0, 0, 0));       // top, right, bottom, left
        VBox.setMargin(statusBar, new Insets(0, 0, 10, 0));
        VBox.setMargin(symbolOutput, new Insets(0, 0, 5, 0));
        VBox.setMargin(scoreOutput, new Insets(0, 0, 0, 0));
        VBox.setMargin(userBet, new Insets(0, 0, 0, 0));
        VBox.setMargin(buttonRow, new Insets(0, 0, 10, 0));
        VBox.setMargin(back, new Insets(0, 0, 0, 0));

        setCenter(center);
    }
}
