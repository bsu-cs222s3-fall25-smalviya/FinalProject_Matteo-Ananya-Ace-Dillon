package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RouletteView extends BorderPane {

    private final RouletteLogic rouletteLogic = new RouletteLogic();

    private Label balanceLabel;
    private final TextField betAmountField = new TextField();
    private final ChoiceBox<RouletteLogic.BetType> betTypeChoiceBox = new ChoiceBox<>();
    private final TextField chosenNumberField = new TextField();
    private final Label resultLabel = new Label("Choose where to lose your money.");

    private static final String ROOT_BG_STYLE =
            "-fx-background-color: radial-gradient(center 50% 0%, radius 120%, #004000, #001000);";

    private static final String LABEL_STYLE_GOLD =
            "-fx-font-size: 14px; -fx-text-fill: #FFD700; -fx-font-weight: bold;";

    private static final String RESULT_STYLE =
            "-fx-font-size: 18px; -fx-text-fill: #FFFFFF; -fx-font-family: 'digital-7 (italic)';";

    private static final String TEXTFIELD_STYLE =
            "-fx-background-color: rgba(10,40,10,0.9);" +
                    "-fx-text-fill: #FFFFFF;" +
                    "-fx-prompt-text-fill: #B0B0B0;" +
                    "-fx-border-color: #FFD700;" +
                    "-fx-border-radius: 6;" +
                    "-fx-background-radius: 6;";

    private static final String SPIN_BUTTON_BASE =
            "-fx-background-color: #000000;" +
                    "-fx-text-fill: #FFD700;" +
                    "-fx-font-size: 18px;" +
                    "-fx-font-family: 'Bitcount Grid Single';" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 14;" +
                    "-fx-border-color: #FFD700;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-radius: 14;" +
                    "-fx-padding: 6 24;" +
                    "-fx-cursor: hand;";

    private static final String SPIN_BUTTON_HOVER =
            "-fx-background-color: #FFD700;" +
                    "-fx-text-fill: #000000;" +
                    "-fx-font-size: 18px;" +
                    "-fx-font-family: 'Bitcount Grid Single';" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 14;" +
                    "-fx-border-color: #FFFFFF;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-radius: 14;" +
                    "-fx-padding: 6 24;" +
                    "-fx-cursor: hand;";

    public RouletteView() {
        setupUI();
    }

    private void setupUI() {
        setStyle(ROOT_BG_STYLE);
        setPadding(new Insets(8));

        Label titleLabel = new Label("\uD83C\uDFA1 ROULETTE \uD83C\uDFA1");
        titleLabel.setFont(Font.font("Bitcount Grid Single", 48));
        titleLabel.setTextFill(Color.web("#FFD700"));

        Label playerLabel = new Label("Player: " + MenuView.currentUsername);
        playerLabel.setTextFill(Color.web("#FFFFFF"));
        playerLabel.setFont(Font.font("digital-7 (mono)", 18));

        Label dot = new Label("•");
        dot.setTextFill(Color.web("#FFD700"));
        dot.setFont(Font.font("digital-7 (mono)", 18));

        balanceLabel = new Label("MAAD Coins: " + CoinBalance.getGameBalance());
        balanceLabel.setTextFill(Color.web("#FFFFFF"));
        balanceLabel.setFont(Font.font("digital-7 (mono italic)", 20));

        HBox hud = new HBox(10, playerLabel, dot, balanceLabel);
        hud.setAlignment(Pos.CENTER);

        resultLabel.setStyle(RESULT_STYLE);

        VBox topBox = new VBox(5, titleLabel, hud, resultLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10, 10, 10, 10));
        topBox.setStyle(
                "-fx-background-color: linear-gradient(to right, #004000, #006b38);"
        );
        setTop(topBox);

        Image wheelImage = new Image("/images/roulette_table.jpg");
        ImageView wheelView = new ImageView(wheelImage);
        wheelView.setPreserveRatio(true);
        VBox.setMargin(wheelView, new Insets(16, 0, 8, 0));

        betTypeChoiceBox.getItems().addAll(
                RouletteLogic.BetType.NUMBER,
                RouletteLogic.BetType.RED,
                RouletteLogic.BetType.BLACK,
                RouletteLogic.BetType.EVEN,
                RouletteLogic.BetType.ODD
        );
        betTypeChoiceBox.getSelectionModel().select(RouletteLogic.BetType.RED);

        betAmountField.setPromptText("Bet amount");
        betAmountField.setPrefColumnCount(7);
        betAmountField.setStyle(TEXTFIELD_STYLE);

        chosenNumberField.setPromptText("0–36");
        chosenNumberField.setPrefColumnCount(4);
        chosenNumberField.setDisable(true);
        chosenNumberField.setStyle(TEXTFIELD_STYLE);

        betTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldType, newType) -> {
            boolean needsNumber = newType == RouletteLogic.BetType.NUMBER;
            chosenNumberField.setDisable(!needsNumber);
            if (!needsNumber) {
                chosenNumberField.clear();
            }
        });

        Label betTypeLabel = new Label("Bet Type:");
        betTypeLabel.setStyle(LABEL_STYLE_GOLD);

        Label betAmountLabel = new Label("Bet:");
        betAmountLabel.setStyle(LABEL_STYLE_GOLD);

        Label numberLabel = new Label("Number:");
        numberLabel.setStyle(LABEL_STYLE_GOLD);

        Button spinButton = new Button("SPIN");
        applySpinButtonStyle(spinButton);
        spinButton.setOnAction(e -> spinWheelAction());

        HBox inputRow = new HBox(10,
                betTypeLabel, betTypeChoiceBox,
                betAmountLabel, betAmountField,
                numberLabel, chosenNumberField,
                spinButton
        );
        inputRow.setAlignment(Pos.CENTER);
        inputRow.setPadding(new Insets(8));
        inputRow.setStyle(
                "-fx-background-color: rgba(0,0,0,0.75);" +
                        "-fx-background-radius: 18;" +
                        "-fx-border-color: #FF0000;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 18;"
        );

        VBox centerBox = new VBox(8, wheelView, inputRow);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(10, 10, 4, 10));
        setCenter(centerBox);

        Button instructionsButton = new Button("Instructions");
        instructionsButton.getStyleClass().add("purple");
        instructionsButton.setPrefWidth(110);

        instructionsButton.setOnAction(_ -> InstructionsPopup.show(
                "Roulette Instructions",
                """
                Roulette

                Roulette simulates a wheel and a ball spinning around it.

                Select your "Bet Type"

                  • Number - bet on a specific number (35:1)
                  • Red    - bet on red numbers (1:1)
                  • Black  - bet on black numbers (1:1)
                  • Even   - bet on even numbers (1:1)
                  • Odd    - bet on odd numbers (1:1)

                Select your Bet Amount
                Press the “Spin” button
                """
        ));

        Button backButton = new Button("Back to Menu");
        backButton.getStyleClass().add("black");
        backButton.setOnAction(e -> {
            CoinBalance.balance += CoinBalance.gameBalance;
            CoinBalance.gameBalance = 0;

            AccountManager.updateBalance(MenuView.currentUsername, CoinBalance.balance);
            AccountManager.saveAccounts();
            getScene().setRoot(new MenuView());
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottomBar = new HBox(10, instructionsButton, spacer, backButton);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(6, 12, 8, 12));
        setBottom(bottomBar);
    }

    private void applySpinButtonStyle(Button button) {
        button.setStyle(SPIN_BUTTON_BASE);
        button.setOnMouseEntered(e -> button.setStyle(SPIN_BUTTON_HOVER));
        button.setOnMouseExited(e -> button.setStyle(SPIN_BUTTON_BASE));
    }

    private void spinWheelAction() {
        int betAmount;
        try {
            betAmount = Integer.parseInt(betAmountField.getText().trim());
        } catch (Exception e) {
            resultLabel.setText("Please enter a valid number.");
            return;
        }

        if (betAmount <= 0) {
            resultLabel.setText("Bet must be greater than zero.");
            return;
        }

        int chosenNumber = -1;
        if (betTypeChoiceBox.getValue() == RouletteLogic.BetType.NUMBER) {
            try {
                chosenNumber = Integer.parseInt(chosenNumberField.getText().trim());
            } catch (Exception e) {
                resultLabel.setText("Please enter a number between 0 and 36.");
                return;
            }
            if (chosenNumber < 0 || chosenNumber > 36) {
                resultLabel.setText("Number must be between 0 and 36.");
                return;
            }
        }

        RouletteLogic.RoundResult roundResult = rouletteLogic.playRound(
                betTypeChoiceBox.getValue(),
                chosenNumber,
                betAmount
        );

        resultLabel.setText(roundResult.getSummary());
        balanceLabel.setText("MAAD Coins: " + CoinBalance.getGameBalance());
    }
}
