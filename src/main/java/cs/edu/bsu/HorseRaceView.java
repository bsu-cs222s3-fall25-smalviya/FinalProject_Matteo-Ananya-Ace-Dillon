package cs.edu.bsu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class HorseRaceView extends BorderPane {

    private static final int NUM_HORSES = HorseRaceLogic.getNumHorses();
    private static final double TICK_MS = 50;

    private final List<Rectangle> horseRects = new ArrayList<>();
    private Timeline timeline;

    private ComboBox<Integer> horseChoice;
    private TextField betField;
    private Label balanceLabel;
    private Label statusLabel;
    private Button startButton;
    private Button backButton;

    public HorseRaceView() {
        setupUI();
    }

    private void setupUI() {
        Label title = new Label("🐎  MAAD Horse Racing  🐎");
        title.setFont(Font.font("Bitcount Grid Single", 32));
        title.setTextFill(Color.web("#FFD700"));

        balanceLabel = new Label("Balance: " + CoinBalance.getBalance());
        balanceLabel.setTextFill(Color.WHITE);
        balanceLabel.setFont(Font.font("digital-7 (mono italic)", 24));

        statusLabel = new Label("Place your bet and start the race!");
        statusLabel.setTextFill(Color.WHITE);
        statusLabel.setFont(Font.font("digital-7 (italic)", 18));

        VBox topBox = new VBox(5, title, balanceLabel, statusLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(15, 0, 10, 0));
        topBox.setStyle("-fx-background-color: linear-gradient(to right, #004000, #006b38);");

        setTop(topBox);

        VBox lanesBox = new VBox(15);
        lanesBox.setPadding(new Insets(20));
        lanesBox.setAlignment(Pos.CENTER_LEFT);
        lanesBox.setStyle("-fx-background-color: linear-gradient(to right, #003300, #006b38);");

        for (int i = 0; i < NUM_HORSES; i++) {
            HBox lane = new HBox(10);
            lane.setAlignment(Pos.CENTER_LEFT);

            Label horseLabel = new Label("Horse " + (i + 1));
            horseLabel.setMinWidth(70);
            horseLabel.setTextFill(Color.WHITE);

            Rectangle horseRect = new Rectangle(40, 20, Color.DARKBLUE);
            horseRect.setTranslateX(0);
            horseRects.add(horseRect);

            Region finishLine = new Region();
            finishLine.setPrefWidth(HorseRaceLogic.getFinishDistance());
            finishLine.setStyle("-fx-border-color: white; -fx-border-width: 0 2 0 0;");

            lane.getChildren().addAll(horseLabel, horseRect, finishLine);
            lanesBox.getChildren().add(lane);
        }

        setCenter(lanesBox);

        HBox controls = new HBox(12);
        controls.setPadding(new Insets(10, 20, 15, 20));
        controls.setAlignment(Pos.CENTER);
        controls.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85);");

        betField = new TextField();
        betField.setPromptText("Bet amount");
        betField.setPrefWidth(90);

        horseChoice = new ComboBox<>();
        for (int i = 1; i <= NUM_HORSES; i++) {
            horseChoice.getItems().add(i);
        }
        horseChoice.setPromptText("Horse #");
        horseChoice.setPrefWidth(80);

        startButton = new Button("Start ▶");
        startButton.setFont(Font.font("Bitcount Grid Single", 16));
        startButton.setOnAction(e -> startRace());

        backButton = new Button("⮌ Back to Menu");
        backButton.setFont(Font.font("Bitcount Grid Single", 18));
        backButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff8800);" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 25 8 25;" +
                        "-fx-border-color: #ffffff;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 20;"
        );
        backButton.setOnAction(e -> {
            if (timeline != null) {
                timeline.stop();
            }
            MenuView menuView = new MenuView();
            this.getScene().setRoot(menuView);
        });

        controls.getChildren().addAll(
                new Label("Bet:"), betField,
                new Label("Horse:"), horseChoice,
                startButton,
                backButton
        );

        setBottom(controls);
    }

    private void startRace() {
        String betText = betField.getText().trim();
        if (betText.isEmpty()) {
            statusLabel.setText("Enter a bet amount.");
            return;
        }

        int betAmount;
        try {
            betAmount = Integer.parseInt(betText);
        } catch (NumberFormatException ex) {
            statusLabel.setText("Bet must be a number.");
            return;
        }

        if (betAmount <= 0) {
            statusLabel.setText("Bet must be positive.");
            return;
        }

        long currentBalance = CoinBalance.getBalance();
        if (betAmount > currentBalance) {
            statusLabel.setText("You don't have enough balance.");
            return;
        }

        Integer choice = horseChoice.getValue();
        if (choice == null) {
            statusLabel.setText("Choose a horse to bet on.");
            return;
        }

        int chosenIndex = choice - 1;

        CoinBalance.balance -= betAmount;
        balanceLabel.setText("Balance: " + CoinBalance.getBalance());

        HorseRaceLogic.setBet(betAmount);
        HorseRaceLogic.setChosenHorse(chosenIndex);
        HorseRaceLogic.resetRaceState();

        for (Rectangle rect : horseRects) {
            rect.setTranslateX(0);
        }

        startButton.setDisable(true);
        betField.setDisable(true);
        horseChoice.setDisable(true);
        statusLabel.setText("Race in progress...");

        timeline = new Timeline(new KeyFrame(Duration.millis(TICK_MS), e -> stepRace()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stepRace() {
        boolean finished = HorseRaceLogic.stepRace();
        double[] positions = HorseRaceLogic.getPositions();

        for (int i = 0; i < horseRects.size(); i++) {
            horseRects.get(i).setTranslateX(positions[i]);
        }

        if (finished) {
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

        long newBalance = HorseRaceLogic.getUpdatedBalance();

        balanceLabel.setText("Balance: " + newBalance);
        statusLabel.setText(HorseRaceLogic.resultMessage());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Race Results");
        alert.setHeaderText("Race Finished!");
        alert.setContentText(HorseRaceLogic.finishOrderString());
        alert.showAndWait();
    }
}
