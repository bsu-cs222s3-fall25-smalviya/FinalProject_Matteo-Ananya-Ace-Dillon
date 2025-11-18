package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;


public class SlotsView extends BorderPane {
    private final TextArea scoreOutput = new TextArea();
    private final TextField userBet = new TextField();
    private Button activeButton = null;
    private final Map<String, Image> imageCache = new HashMap<>();

    private final ImageView slot1 = new ImageView();
    private final ImageView slot2 = new ImageView();
    private final ImageView slot3 = new ImageView();

    private final SlotsSpin slotsSpin;

    private static MediaPlayer spinAudio;

    public SlotsView() {
        imageCache.put("Lucky Seven", new Image(getClass().getResource("/images/LuckySeven.png").toExternalForm()));
        imageCache.put("Diamond",     new Image(getClass().getResource("/images/Diamond.png").toExternalForm()));
        imageCache.put("Bell",        new Image(getClass().getResource("/images/Bell.png").toExternalForm()));
        imageCache.put("Orange",      new Image(getClass().getResource("/images/Orange.png").toExternalForm()));
        imageCache.put("Lemon",       new Image(getClass().getResource("/images/Lemon.png").toExternalForm()));
        imageCache.put("Cherry",      new Image(getClass().getResource("/images/Cherry.png").toExternalForm()));

        setupSlotImageView(slot1);
        setupSlotImageView(slot2);
        setupSlotImageView(slot3);

        slot1.setImage(null);
        slot2.setImage(null);
        slot3.setImage(null);

        HBox symbolOutput = new HBox(10);
        symbolOutput.setAlignment(Pos.CENTER);
        symbolOutput.getStyleClass().add("symbolOutput");
        symbolOutput.getChildren().clear();

        Label lineStart = new Label("|");
        lineStart.getStyleClass().add("slotLines");
        Label lineMid1 = new Label("|");
        lineMid1.getStyleClass().add("slotLines");
        Label lineMid2 = new Label("|");
        lineMid2.getStyleClass().add("slotLines");
        Label lineEnd = new Label("|");
        lineEnd.getStyleClass().add("slotLines");

        symbolOutput.getChildren().addAll(lineStart, slot1, lineMid1, slot2, lineMid2, slot3, lineEnd);

        slotsSpin = new SlotsSpin(imageCache, slot1, slot2, slot3);


        Label title = new Label("🎰 Slots 🎰");
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

        userBet.getStyleClass().add("userBet");

        scoreOutput.setEditable(false);
        scoreOutput.getStyleClass().add("scoreOutput");

        Button spin = new Button("Spin");
        spin.getStyleClass().add("black");
        spin.setDisable(true);

        spin.setOnAction(_ -> {
            scoreOutput.getStyleClass().removeAll("scoreOutputLose");
            scoreOutput.getStyleClass().removeAll("scoreOutputWin");
            scoreOutput.getStyleClass().add("scoreOutput");
            boolean sufficient = SlotsLogic.spin();
            if (!sufficient) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Message from the Devs:");
                alert.setHeaderText(null);
                alert.setContentText("You don’t have enough MAAD Coins to spin...");
                alert.showAndWait();
                return;
            }
            playSpinSound();

            spin.setDisable(true);
            coins.setText("MAAD Coins: " + CoinBalance.gameBalance);

            scoreOutput.setText("Spinning...");

            slotsSpin.spin(Duration.seconds(1.3), () -> {
                SlotsLogic.payout();
                long newBalance = CoinBalance.gameBalance;

                String result = SlotsLogic.score();

                if ("No match...\n".equals(result)) {
                    scoreOutput.getStyleClass().removeAll("scoreOutput");
                    scoreOutput.getStyleClass().removeAll("scoreOutputWin");
                    scoreOutput.getStyleClass().add("scoreOutputLose");
                } else {
                    scoreOutput.getStyleClass().removeAll("scoreOutputLose");
                    scoreOutput.getStyleClass().removeAll("scoreOutput");
                    scoreOutput.getStyleClass().add("scoreOutputWin");
                }

                String[] parts = { SlotsLogic.word1, SlotsLogic.word2, SlotsLogic.word3 };
                ImageView[] slots = { slot1, slot2, slot3 };

                for (int i = 0; i < 3; i++) {
                    String symbolName = parts[i];
                    Image img = imageCache.get(symbolName);
                    if (img != null) {
                        slots[i].setImage(img);
                    }
                }
                scoreOutput.setText(result);
                coins.setText("MAAD Coins: " + newBalance);

                SlotsLogic.word1 = null;
                SlotsLogic.word2 = null;
                SlotsLogic.word3 = null;

                spin.setDisable(false);
            });
        });

        Button oneCoin = new Button("1");
        Button tenCoin = new Button("10");
        Button fiftyCoin = new Button("50");
        Button hundredCoin = new Button("100");

        oneCoin.getStyleClass().add("betButton");
        tenCoin.getStyleClass().add("betButton");
        fiftyCoin.getStyleClass().add("betButton");
        hundredCoin.getStyleClass().add("betButton");

        oneCoin.setOnAction(_ -> {
            SlotsLogic.setBet(1);
            userBet.setText("Bet amount: 1 MAAD Coin");
            spin.setDisable(false);
            updateActiveBetButton(oneCoin);
        });
        tenCoin.setOnAction(_ -> {
            SlotsLogic.setBet(10);
            userBet.setText("Bet amount: 10 MAAD Coins");
            spin.setDisable(false);
            updateActiveBetButton(tenCoin);
        });
        fiftyCoin.setOnAction(_ -> {
            SlotsLogic.setBet(50);
            userBet.setText("Bet amount: 50 MAAD Coins");
            spin.setDisable(false);
            updateActiveBetButton(fiftyCoin);
        });
        hundredCoin.setOnAction(_ -> {
            SlotsLogic.setBet(100);
            userBet.setText("Bet amount: 100 MAAD Coins");
            spin.setDisable(false);
            updateActiveBetButton(hundredCoin);
        });

        Button back = new Button("Back to Menu");
        back.getStyleClass().add("red");
        back.setOnAction(_ -> {
            stopSpinSound();
            CoinBalance.balance += CoinBalance.gameBalance;
            CoinBalance.gameBalance = 0;
            getScene().setRoot(new MenuView());
        });

        HBox buttonRow = new HBox(10, oneCoin, tenCoin, fiftyCoin, hundredCoin, spin);
        buttonRow.setAlignment(Pos.CENTER);
        setTop(buttonRow);

        VBox center = new VBox(15, title, statusBar, symbolOutput, scoreOutput, userBet, buttonRow, back);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(0));

        VBox.setMargin(title, new Insets(10, 0, 0, 0));
        VBox.setMargin(statusBar, new Insets(0, 0, 10, 0));
        VBox.setMargin(symbolOutput, new Insets(0, 0, 5, 0));
        VBox.setMargin(scoreOutput, new Insets(0, 0, 0, 0));
        VBox.setMargin(userBet, new Insets(0, 0, 0, 0));
        VBox.setMargin(buttonRow, new Insets(0, 0, 10, 0));
        VBox.setMargin(back, new Insets(0, 0, 0, 0));

        setCenter(center);
    }

    private void setupSlotImageView(ImageView iv) {
        iv.setFitWidth(60);
        iv.setFitHeight(60);
        iv.setPreserveRatio(false);
        iv.setSmooth(true);
    }

    private void updateActiveBetButton(Button newActive) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("betButtonPressed");
        }
        newActive.getStyleClass().add("betButtonPressed");
        activeButton = newActive;
    }

    private void playSpinSound() {
        Media media = new Media(getClass().getResource("/audio/SpinSound.wav").toExternalForm());
        spinAudio = new MediaPlayer(media);
        spinAudio.play();
    }

    private void stopSpinSound() {
        if (spinAudio != null) {
            spinAudio.stop();
            spinAudio.dispose();
            spinAudio = null;
        }
    }

}