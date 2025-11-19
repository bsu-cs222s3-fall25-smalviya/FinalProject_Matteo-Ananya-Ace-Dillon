package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

import javafx.scene.layout.Region;

import javafx.stage.Popup;
import javafx.geometry.Bounds;


public class SlotsView extends BorderPane {
    private final TextArea scoreOutput = new TextArea();
    private final TextField userBet = new TextField();
    private Button activeButton = null;
    private static final Button spin = new Button("Spin");
    private final Map<String, Image> imageCache = new HashMap<>();

    private final ImageView slot1 = new ImageView();
    private final ImageView slot2 = new ImageView();
    private final ImageView slot3 = new ImageView();

    private final SlotsSpin slotsSpin;

    private static MediaPlayer spinAudio;

    public SlotsView() {
        imageCache.put("Lucky Seven", new Image(getClass().getResource("/images/LuckySeven.png").toExternalForm()));
        imageCache.put("Diamond", new Image(getClass().getResource("/images/Diamond.png").toExternalForm()));
        imageCache.put("Bell", new Image(getClass().getResource("/images/Bell.png").toExternalForm()));
        imageCache.put("Orange", new Image(getClass().getResource("/images/Orange.png").toExternalForm()));
        imageCache.put("Lemon", new Image(getClass().getResource("/images/Lemon.png").toExternalForm()));
        imageCache.put("Cherry", new Image(getClass().getResource("/images/Cherry.png").toExternalForm()));


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
        Label lineMid1 = new Label("|");
        Label lineMid2 = new Label("|");
        Label lineEnd = new Label("|");

        lineStart.getStyleClass().add("slotLines");
        lineMid1.getStyleClass().add("slotLines");
        lineMid2.getStyleClass().add("slotLines");
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
        userBet.setText("Select a coin amount:");

        scoreOutput.setEditable(false);
        scoreOutput.getStyleClass().add("scoreOutput");


        spin.getStyleClass().add("spinButton");
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
            betButtonAction(1, oneCoin);
        });
        tenCoin.setOnAction(_ -> {
            betButtonAction(10, tenCoin);
        });
        fiftyCoin.setOnAction(_ -> {
            betButtonAction(50, fiftyCoin);
        });
        hundredCoin.setOnAction(_ -> {
            betButtonAction(100, hundredCoin);
        });

        Button backButton = new Button("Back to Menu");
        Button payoutInfoButton = new Button("Payouts");

        payoutInfoButton.getStyleClass().add("purple");

        HBox bottomBar = new HBox();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        backButton.getStyleClass().add("red");
        backButton.setOnAction(_ -> {
            stopSpinSound();
            CoinBalance.balance += CoinBalance.gameBalance;
            CoinBalance.gameBalance = 0;
            getScene().setRoot(new MenuView());
        });



        Popup payoutPopup = new Popup();

        // Layout for popup content
        VBox popupContent = new VBox(15);
        popupContent.setPrefWidth(275);
        popupContent.setPrefHeight(340);
        popupContent.getStyleClass().add("payoutPopup");
        Label popupHeader = new Label ("Slot Payouts:");

        ImageView cherry = getImage("Cherry.png");
        ImageView lemon = getImage("Lemon.png");
        ImageView orange = getImage("Orange.png");
        ImageView bell = getImage("Bell.png");
        ImageView diamond = getImage("Diamond.png");
        ImageView luckySeven = getImage("LuckySeven.png");

        Stream.of(cherry, lemon, orange, bell, diamond, luckySeven)
                .forEach(img -> {
                    img.setFitWidth(20);
                    img.setFitHeight(20);
                    img.setPreserveRatio(true);
                });

        VBox popupText = new VBox(5);

        popupText.getChildren().addAll(
                getTwoMatchPayout(cherry, 1.0, "Cherries"),
                getThreeMatchPayout(cherry, 5.5, "Cherries"),

                getTwoMatchPayout(lemon, 1.5, "Lemons"),
                getThreeMatchPayout(lemon, 6.5, "Lemons"),

                getTwoMatchPayout(orange, 2.0, "Oranges"),
                getThreeMatchPayout(orange, 7.0, "Oranges"),

                getTwoMatchPayout(bell, 2.5, "Bells"),
                getThreeMatchPayout(bell, 8.5, "Bells"),

                getTwoMatchPayout(diamond, 3.0, "Diamonds"),
                getThreeMatchPayout(diamond, 10.0, "Diamonds"),

                getTwoMatchPayout(luckySeven, 4.5, "Lucky Sevens"),
                getThreeMatchPayout(luckySeven, 100.0, "Lucky Sevens")
        );

        popupHeader.getStyleClass().add("popupHeader");

        // close
        Button closePopup = new Button("Close");
        closePopup.getStyleClass().add("popupBackButton");
        closePopup.setOnAction(e -> payoutPopup.hide());

        // puts em together
        popupContent.getChildren().addAll(popupHeader, popupText, closePopup);

        // Adds content to popup. put whole box inside window
        payoutPopup.getContent().add(popupContent);

        // Show/hide near the button when clicked
        payoutInfoButton.setOnAction(e -> {
            if (payoutPopup.isShowing()) {
                payoutPopup.hide();
            } else {
                // Get screen position of the button. finds the payout button
                Bounds btnBounds = payoutInfoButton.localToScreen(payoutInfoButton.getBoundsInLocal());
                double x = btnBounds.getMinX();
                double y = btnBounds.getMinY() - 400;

                payoutPopup.show(payoutInfoButton, x, y);
            }
        });


        HBox buttonRow = new HBox(15, oneCoin, tenCoin, fiftyCoin, hundredCoin, spin);
        HBox.setMargin(spin, new Insets(0, 0, 0, 30));
        buttonRow.setAlignment(Pos.CENTER);

        VBox bettingArea = new VBox(0, userBet, buttonRow);
        bettingArea.getStyleClass().add("buttonLayout");

        bottomBar.getChildren().addAll(payoutInfoButton, spacer, backButton);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(10));

        setBottom(bottomBar);

        VBox center = new VBox(0, title, statusBar, symbolOutput, scoreOutput, bettingArea);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(0));

        VBox.setMargin(title, new Insets(10, 0, 0, 0));
        VBox.setMargin(statusBar, new Insets(0, 0, 25, 0));
        VBox.setMargin(symbolOutput, new Insets(0, 0, 10, 0));
        VBox.setMargin(scoreOutput, new Insets(0, 0, 40, 0));
        VBox.setMargin(userBet, new Insets(0, 0, 0, 0));
        VBox.setMargin(buttonRow, new Insets(25, 0, 10, 0));
        VBox.setMargin(bottomBar, new Insets(15, 0, 0, 0));

        setCenter(center);
    }

    public void betButtonAction(int setBet, Button activeButton) {
        SlotsLogic.setBet(setBet);
        if (setBet == 1) {
            userBet.setText("Bet amount: " + setBet + " MAAD Coin");
        } else {
            userBet.setText("Bet amount: " + setBet + " MAAD Coins");
        }
        spin.setDisable(false);
        updateActiveBetButton(activeButton);
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

    private ImageView getImage(String fileName) {
        Image img = new Image(getClass().getResource("/images/" + fileName).toExternalForm());
        return new ImageView(img);
    }

    private ImageView cloneImage(ImageView original) {
        ImageView clone = new ImageView(original.getImage());
        clone.setFitWidth(original.getFitWidth());
        clone.setFitHeight(original.getFitHeight());
        clone.setPreserveRatio(true);
        return clone;
    }

    private Node getTwoMatchPayout(ImageView img, double multiplier, String symbol) {
        HBox payoutRow = new HBox(5);
        payoutRow.setAlignment((Pos.CENTER_LEFT));

        Label payoutInfoLine = new Label("• 2 " + symbol + ": " + multiplier + "x ");

        ImageView i1 = cloneImage(img);
        ImageView i2 = cloneImage(img);
        payoutRow.getChildren().addAll(payoutInfoLine, i1, i2);

        Separator line = new Separator();
        line.getStyleClass().add("separator");

        VBox box = new VBox(4);
        box.getChildren().addAll(payoutRow, line);

        payoutInfoLine.getStyleClass().add("popupText");

        return box;
    }

    private Node getThreeMatchPayout(ImageView img, double multiplier, String symbol) {
        HBox payoutRow = new HBox(5);
        payoutRow.setAlignment((Pos.CENTER_LEFT));

        Label payoutInfoLine = new Label("• 2 " + symbol + ": " + multiplier + "x ");

        ImageView image1 = cloneImage(img);
        ImageView image2 = cloneImage(img);
        ImageView image3 = cloneImage(img);
        payoutRow.getChildren().addAll(payoutInfoLine, image1, image2, image3);

        Separator line = new Separator();
        line.getStyleClass().add("separator");

        VBox box = new VBox(4);
        box.getChildren().addAll(payoutRow, line);

        payoutInfoLine.getStyleClass().add("popupText");

        return box;
    }

}