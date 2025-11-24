package cs.edu.bsu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.control.ListCell;

import java.util.ArrayList;
import java.util.List;

public class HorseRaceView extends BorderPane {

    private static final int NUM_HORSES = HorseRaceLogic.getNumHorses();
    private static final double TICK_MS = 50;

    private final List<Label> horseSprites = new ArrayList<>();
    private Timeline timeline;
    private boolean raceFinished = false;

    private ComboBox<Integer> horseChoice;
    private TextField betField;
    private Label balanceLabel;
    private Label statusLabel;
    private Button startButton;
    private Button backButton;

    private Button bet10Button;
    private Button bet25Button;
    private Button bet50Button;
    private Button bet100Button;
    private Button activeBetButton;

    public HorseRaceView() {
        setupUI();
    }

    private void setupUI() {
        this.setStyle(
                "-fx-background-color: radial-gradient(center 50% 0%, radius 120%, #004000, #001000);"
        );

        Label title = new Label("🐎 HORSE RACE ARENA 🐎");
        title.setFont(Font.font("Bitcount Grid Single", 40));
        title.setTextFill(Color.web("#FFD700"));

        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#FFD700"));
        glow.setRadius(20);
        title.setEffect(glow);

        Label playerLabel = new Label("Player: " + MenuView.currentUsername);
        playerLabel.setTextFill(Color.web("#FFFFFF"));
        playerLabel.setFont(Font.font("digital-7 (mono)", 18));

        Label dot = new Label("•");
        dot.setTextFill(Color.web("#FFD700"));
        dot.setFont(Font.font("digital-7 (mono)", 18));

        balanceLabel = new Label("MAAD Coins: " + CoinBalance.gameBalance);
        balanceLabel.setTextFill(Color.web("#FFFFFF"));
        balanceLabel.setFont(Font.font("digital-7 (mono italic)", 20));

        HBox statusBar = new HBox(10, playerLabel, dot, balanceLabel);
        statusBar.setAlignment(Pos.CENTER);

        statusLabel = new Label("Pick your champion, place your bet, and start the race!");
        statusLabel.setTextFill(Color.web("#FFEFD5"));
        statusLabel.setFont(Font.font("digital-7 (italic)", 18));

        VBox topBox = new VBox(8, title, statusBar, statusLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(15, 10, 15, 10));
        topBox.setStyle(
                "-fx-background-color: linear-gradient(to right, #004000, #006b38);"
        );

        setTop(topBox);

        VBox lanesBox = new VBox(15);
        lanesBox.setPadding(new Insets(20));
        lanesBox.setAlignment(Pos.CENTER_LEFT);
        lanesBox.setStyle(
                "-fx-background-color: linear-gradient(to right, rgba(0,30,0,0.9), rgba(0,90,40,0.9));"
        );

        Color[] horseColors = new Color[] {
                Color.web("#FF7F50"),
                Color.web("#1E90FF"),
                Color.web("#ADFF2F"),
                Color.web("#FF1493"),
                Color.web("#FFD700"),
                Color.web("#7B68EE")
        };

        for (int i = 0; i < NUM_HORSES; i++) {
            HBox lane = new HBox(10);
            lane.setAlignment(Pos.CENTER_LEFT);
            lane.setPadding(new Insets(5, 10, 5, 10));
            lane.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.25);" +
                            "-fx-background-radius: 15;"
            );

            Label horseLabel = new Label("Horse " + (i + 1));
            horseLabel.setMinWidth(80);
            horseLabel.setTextFill(Color.web("#FFD700"));
            horseLabel.setFont(Font.font("Bitcount Grid Single", 16));

            Region finishLine = new Region();
            finishLine.setPrefWidth(HorseRaceLogic.getFinishDistance());
            finishLine.setStyle(
                    "-fx-border-color: white;" +
                            "-fx-border-width: 0 0 0 2;" +
                            "-fx-border-style: segments(5, 5) line-cap round;"
            );

            Label horseSprite = new Label("🐎");
            horseSprite.setFont(Font.font(28));
            horseSprite.setTextFill(horseColors[i % horseColors.length]);
            horseSprite.setTranslateX(0);
            horseSprites.add(horseSprite);

            lane.getChildren().addAll(horseLabel, finishLine, horseSprite);
            lanesBox.getChildren().add(lane);
        }

        setCenter(lanesBox);

        HBox controls = new HBox(18);
        controls.setPadding(new Insets(12, 20, 15, 20));
        controls.setAlignment(Pos.CENTER);
        controls.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.85);");

        Label betLabel = new Label("Bet:");
        betLabel.setTextFill(Color.web("#FFD700"));
        betLabel.setStyle("-fx-font-weight: bold;");

        betField = new TextField();
        betField.setPromptText("Custom bet");
        betField.setPrefWidth(90);
        betField.setStyle(
                "-fx-background-color: rgba(10,40,10,0.9);" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-prompt-text-fill: #B0B0B0;" +
                        "-fx-border-color: #FFD700;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;");

        bet10Button = createBetChipButton(10);
        bet25Button = createBetChipButton(25);
        bet50Button = createBetChipButton(50);
        bet100Button = createBetChipButton(100);

        HBox chipsBox = new HBox(6, bet10Button, bet25Button, bet50Button, bet100Button);
        chipsBox.setAlignment(Pos.CENTER_LEFT);

        Label horseLabelControl = new Label("Horse:");
        horseLabelControl.setTextFill(Color.web("#FFD700"));
        horseLabelControl.setStyle("-fx-font-weight: bold;");

        horseChoice = new ComboBox<>();
        for (int i = 1; i <= NUM_HORSES; i++) {
            horseChoice.getItems().add(i);
        }
        horseChoice.setPromptText("#");
        horseChoice.setPrefWidth(70);
        horseChoice.setStyle(
                "-fx-background-color: rgba(10,40,10,0.9);" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-prompt-text-fill: #B0B0B0;" +
                        "-fx-border-color: #FFD700;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;");

        horseChoice.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(item));
                }
                setTextFill(Color.WHITE);
                setStyle("-fx-background-color: rgba(10,40,10,0.95);");
            }
        });

        horseChoice.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("#");
                } else {
                    setText(String.valueOf(item));
                }
                setTextFill(Color.WHITE);
            }
        });

        startButton = new Button("Start Race ▶");
        startButton.setFont(Font.font("Bitcount Grid Single", 18));
        startButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #00ff99, #00cc66);" +
                        "-fx-text-fill: #001a00;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 25 8 25;" +
                        "-fx-border-color: #ffffff;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 20;" +
                        "-fx-cursor: hand;"
        );
        startButton.setOnAction(e -> startRace());

        backButton = new Button("Back to Menu");
        backButton.setFont(Font.font("Bitcount Grid Single", 16));
        backButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff8800);" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 6 18 6 18;" +
                        "-fx-border-color: transparent;" +
                        "-fx-cursor: hand;"
        );
        backButton.setOnAction(e -> {
            if (timeline != null) {
                timeline.stop();
            }
            CoinBalance.balance += CoinBalance.gameBalance;
            CoinBalance.gameBalance = 0;

            AccountManager.updateBalance(MenuView.currentUsername, CoinBalance.balance);
            AccountManager.saveAccounts();
            MenuView menuView = new MenuView();
            this.getScene().setRoot(menuView);
        });

        VBox betControls = new VBox(4,
                new HBox(5, betLabel, betField),
                chipsBox
        );
        betControls.setAlignment(Pos.CENTER_LEFT);

        controls.getChildren().addAll(
                betControls,
                horseLabelControl, horseChoice,
                startButton,
                backButton
        );

        setBottom(controls);
        setupChipActions();
    }

    private Button createBetChipButton(int amount) {
        Button chip = new Button(String.valueOf(amount));
        chip.setMinWidth(48);
        chip.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2e8b57, #145c33);" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 999;" +
                        "-fx-border-radius: 999;" +
                        "-fx-border-color: #FFD700;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-cursor: hand;");
        return chip;
    }

    private void setupChipActions() {
        bet10Button.setOnAction(e -> selectChipBet(10, bet10Button));
        bet25Button.setOnAction(e -> selectChipBet(25, bet25Button));
        bet50Button.setOnAction(e -> selectChipBet(50, bet50Button));
        bet100Button.setOnAction(e -> selectChipBet(100, bet100Button));
    }

    private void selectChipBet(int amount, Button chip) {
        betField.setText(String.valueOf(amount));

        if (activeBetButton != null) {
            activeBetButton.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #2e8b57, #145c33);" +
                            "-fx-text-fill: #ffffff;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 999;" +
                            "-fx-border-radius: 999;" +
                            "-fx-border-color: #FFD700;" +
                            "-fx-border-width: 1.5;" +
                            "-fx-cursor: hand;");
        }

        chip.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #FFD700, #FFB000);" +
                        "-fx-text-fill: #202020;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 999;" +
                        "-fx-border-radius: 999;" +
                        "-fx-border-color: #ffffff;" +
                        "-fx-border-width: 2;" +
                        "-fx-cursor: hand;");
        activeBetButton = chip;
    }

    private void startRace() {
        String betText = betField.getText().trim();
        if (betText.isEmpty()) {
            statusLabel.setText("Enter a bet amount or tap a chip.");
            return;
        }

        int betAmount;
        try {
            betAmount = Integer.parseInt(betText);
        } catch (NumberFormatException ex) {
            statusLabel.setText("Bet must be a whole number.");
            return;
        }

        if (betAmount <= 0) {
            statusLabel.setText("Bet must be positive.");
            return;
        }

        long currentBalance = CoinBalance.gameBalance;
        if (betAmount > currentBalance) {
            statusLabel.setText("You don't have enough MAAD Coins.");
            return;
        }

        Integer choice = horseChoice.getValue();
        if (choice == null) {
            statusLabel.setText("Choose a horse to bet on.");
            return;
        }

        int chosenIndex = choice - 1;

        CoinBalance.gameBalance -= betAmount;
        balanceLabel.setText("MAAD Coins: " + CoinBalance.gameBalance);

        HorseRaceLogic.setBet(betAmount);
        HorseRaceLogic.setChosenHorse(chosenIndex);
        HorseRaceLogic.resetRaceState();
        raceFinished = false;

        for (Label sprite : horseSprites) {
            sprite.setTranslateX(0);
        }

        startButton.setDisable(true);
        betField.setDisable(true);
        horseChoice.setDisable(true);
        disableChipButtons(true);

        statusLabel.setText("Race in progress... 🐎💨");

        timeline = new Timeline(new KeyFrame(Duration.millis(TICK_MS), e -> stepRace()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stepRace() {
        boolean finished = HorseRaceLogic.stepRace();
        double[] positions = HorseRaceLogic.getPositions();

        for (int i = 0; i < horseSprites.size(); i++) {
            horseSprites.get(i).setTranslateX(-positions[i]);
        }

        if (finished && !raceFinished) {
            raceFinished = true;
            finishRace();
        }
    }

    private void finishRace() {
        if (timeline != null) {
            timeline.stop();
        }

        startButton.setDisable(false);
        betField.setDisable(false);
        horseChoice.setDisable(false);
        disableChipButtons(false);

        long newBalance = HorseRaceLogic.getUpdatedBalance();
        CoinBalance.gameBalance = newBalance;

        balanceLabel.setText("MAAD Coins: " + newBalance);
        statusLabel.setText(HorseRaceLogic.resultMessage());
    }

    private void disableChipButtons(boolean disable) {
        bet10Button.setDisable(disable);
        bet25Button.setDisable(disable);
        bet50Button.setDisable(disable);
        bet100Button.setDisable(disable);
    }
}
