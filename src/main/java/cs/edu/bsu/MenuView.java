package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

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
        blackjack.setOnAction(_ -> getScene().setRoot(new BlackjackView()));

        Button war = makeButton("War", "red");
        war.setOnAction(_ -> getScene().setRoot(new WarView()));

        Button slots = makeButton("Slots", "green");
        slots.setOnAction(_ -> getScene().setRoot(new SlotsView()));

        Button horse = makeButton("Horse Racing", "red");
        horse.setOnAction(_ -> getScene().setRoot(new HorseRaceView()));

        Button roulette = makeButton("Roulette", "black");
        roulette.setOnAction(_ -> getScene().setRoot(new RouletteView()));

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
}
