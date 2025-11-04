package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;

import java.util.Optional;

public class MenuView extends BorderPane {
    static long balance = CoinBalance.balance = 1000;

    public MenuView() {
        balance = CoinBalance.balance;

        Label title = new Label("MAAD Casino");
        VBox.setMargin(title, new Insets(23, 0, 10, 0));
        title.getStyleClass().add("title");
        BorderPane.setAlignment(title, Pos.CENTER);

        Label player = new Label("Player: Guest");
        player.getStyleClass().add("stat");

        Label dot = new Label("•");
        dot.getStyleClass().add("stat-dot");

        Label coins = new Label("MAAD Coins: " + balance);
        coins.getStyleClass().add("stat");

        HBox statusBar = new HBox(10, player, dot, coins);
        statusBar.setAlignment(Pos.CENTER);
        statusBar.setPadding(new Insets(5, 0, 20, 0));

        VBox topBox = new VBox(title, statusBar);
        topBox.setAlignment(Pos.CENTER);
        setTop(topBox);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(24);
        grid.setVgap(24);
        grid.setPadding(new Insets(20));

        RowConstraints r = new RowConstraints();
        r.setPercentHeight(33.333);
        grid.getRowConstraints().addAll(r, r, r);

        Button blackjack = makeButton("Blackjack", "black");
        blackjack.setOnAction(_ ->
                promptCoinAmountAndStartGame("Blackjack", () -> getScene().setRoot(new BlackjackView()))
        );

        Button war = makeButton("War", "red");
        war.setOnAction(_ ->
                promptCoinAmountAndStartGame("War", () -> getScene().setRoot(new WarView()))
        );

        Button slots = makeButton("Slots", "green");
        slots.setOnAction(_ ->
                promptCoinAmountAndStartGame("Slots", () -> getScene().setRoot(new SlotsView()))
        );

        Button horse = makeButton("Horse Racing", "red");
        horse.setOnAction(_ ->
                promptCoinAmountAndStartGame("Horse Racing", () -> getScene().setRoot(new HorseRaceView()))
        );

        Button roulette = makeButton("Roulette", "black");
        roulette.setOnAction(_ ->promptCoinAmountAndStartGame("Roulette",() -> getScene().setRoot(new RouletteView())));

        grid.add(blackjack, 0, 0);
        grid.add(war, 2, 0);
        grid.add(slots, 1, 1);
        grid.add(horse, 0, 2);
        grid.add(roulette, 2, 2);
        setCenter(grid);
    }

    private Button makeButton(String text, String cssClass) {
        Button btn = new Button(text);
        btn.getStyleClass().add(cssClass);
        btn.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        return btn;
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message from the Devs:");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    private void promptCoinAmountAndStartGame(String gameName, Runnable startGameAction) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Bring Coins");
        dialog.setHeaderText("Enter the number of MAAD Coins you want to bring into " + gameName);
        dialog.setContentText("Coins:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return; // user canceled
        }

        try {
            int amount = Integer.parseInt(result.get().trim());
            if (amount <= 0) {
                showError("Please enter a positive number of coins.");
                return;
            }
            if (amount > CoinBalance.balance) {
                showError("You don't have enough MAAD Coins!");
                return;
            }

            // Deduct coins from global balance
            CoinBalance.balance -= amount;

            // (Optional) You can store the amount in the game logic class if needed
            // Example: WarLogic.setStartingCoins(amount);

            startGameAction.run();

        } catch (NumberFormatException e) {
            showError("Please enter a valid number.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
