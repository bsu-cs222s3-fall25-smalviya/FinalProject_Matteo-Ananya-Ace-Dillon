package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class RouletteView extends BorderPane {

    private RouletteLogic rouletteLogic = new RouletteLogic();

    private Label balanceLabel = new Label("MAAD Coins: " + CoinBalance.getGameBalance());
    private TextField betAmountField = new TextField();
    private ChoiceBox<RouletteLogic.BetType> betTypeChoiceBox = new ChoiceBox<>();
    private TextField chosenNumberField = new TextField();
    private Label resultLabel = new Label("Choose where to lose your money.");

    public RouletteView() {
        Label titleLabel = new Label("Roulette");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox balanceBar = new HBox(10, balanceLabel);
        balanceBar.setAlignment(Pos.CENTER);

        VBox topBox = new VBox(10, titleLabel, balanceBar);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));
        setTop(topBox);

        Image wheelImage = new Image("/images/roulette_table.jpg");
        ImageView wheelView = new ImageView(wheelImage);
        wheelView.setPreserveRatio(true);
        wheelView.setFitWidth(600);
        VBox.setMargin(wheelView, new Insets(10, 0, 10, 0));

        betTypeChoiceBox.getItems().addAll(
                RouletteLogic.BetType.NUMBER,
                RouletteLogic.BetType.RED,
                RouletteLogic.BetType.BLACK,
                RouletteLogic.BetType.EVEN,
                RouletteLogic.BetType.ODD
        );
        betTypeChoiceBox.getSelectionModel().select(RouletteLogic.BetType.RED);

        betAmountField.setPromptText("Enter your bet amount");
        betAmountField.setPrefColumnCount(8);

        chosenNumberField.setPromptText("Pick a number 0–36");
        chosenNumberField.setPrefColumnCount(6);
        chosenNumberField.setDisable(true);

        betTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldType, newType) -> {
            boolean needsNumber = newType == RouletteLogic.BetType.NUMBER;
            chosenNumberField.setDisable(!needsNumber);
            if (!needsNumber) chosenNumberField.clear();
        });

        Button spinButton = new Button("SPIN");
        spinButton.setOnAction(event -> spinWheelAction());

        Button backButton = new Button("Return to Menu");
        backButton.setOnAction(event -> {
            CoinBalance.balance += CoinBalance.gameBalance;
            CoinBalance.gameBalance = 0;

            AccountManager.updateBalance(MenuView.currentUsername, CoinBalance.balance);
            AccountManager.saveAccounts();
                getScene().setRoot(new MenuView());
                });

        HBox inputRow = new HBox(10,
                new Label("Bet Type:"), betTypeChoiceBox,
                new Label("Bet Amount:"), betAmountField,
                new Label("Number:"), chosenNumberField,
                spinButton
        );
        inputRow.setAlignment(Pos.CENTER);
        inputRow.setPadding(new Insets(10));

        VBox centerBox = new VBox(12, wheelView, inputRow, resultLabel);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(10));

        HBox bottomBox = new HBox(backButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        setCenter(centerBox);
        setBottom(bottomBox);
        setPadding(new Insets(10));
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
