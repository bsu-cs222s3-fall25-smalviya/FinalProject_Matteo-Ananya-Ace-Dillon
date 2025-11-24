package cs.edu.bsu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WarView extends BorderPane {

    private final WarLogic war = new WarLogic();

    private final TextField betField = new TextField();
    private final Label playerCardLabel = new Label("—");
    private final Label dealerCardLabel = new Label("—");
    private final Label outcomeLabel = new Label("Place a bet and press PLAY");
    private final Label coinsLabel = new Label("MAAD Coins: " + CoinBalance.gameBalance);

    private final Label bannerLabel = new Label("Ready for battle...");
    private final Label roundLabel = new Label("Round: 1");
    private int roundCount = 1;

    private Button bet10Button;
    private Button bet25Button;
    private Button bet50Button;
    private Button bet100Button;

    public WarView() {
        configureRoot();
        buildTopBar();
        buildCenterPanel();
        buildBottomBar();
        updateCoins();
    }

    private void configureRoot() {
        setPadding(new Insets(20));
        setPrefSize(900, 600);
    }

    private void buildTopBar() {
        Label title = new Label("🛡️ WAR 🛡️");
        title.getStyleClass().add("title");

        Label player = new Label("Player: " + MenuView.currentUsername);
        player.getStyleClass().add("stat");

        Label dot = new Label("•");
        dot.getStyleClass().add("stat-dot");

        coinsLabel.getStyleClass().add("stat");

        HBox statusBar = new HBox(10, player, dot, coinsLabel);
        statusBar.setAlignment(Pos.CENTER);

        VBox topBox = new VBox(6, title, statusBar);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(0, 0, 20, 0));

        setTop(topBox);
    }

    private void buildCenterPanel() {
        // Banner + round
        bannerLabel.getStyleClass().addAll("banner-text", "stat");
        roundLabel.getStyleClass().add("stat");

        HBox bannerRow = new HBox(20, bannerLabel, roundLabel);
        bannerRow.setAlignment(Pos.CENTER);

        // Battlefield: YOU vs DEALER
        Label youTitle = new Label("YOU");
        youTitle.getStyleClass().add("stat");

        Label dealerTitle = new Label("DEALER");
        dealerTitle.getStyleClass().add("stat");

        playerCardLabel.getStyleClass().addAll("card-text", "stat");
        dealerCardLabel.getStyleClass().addAll("card-text", "stat");

        VBox youBox = new VBox(6, youTitle, playerCardLabel);
        youBox.setAlignment(Pos.CENTER);

        VBox dealerBox = new VBox(6, dealerTitle, dealerCardLabel);
        dealerBox.setAlignment(Pos.CENTER);

        Label vsLabel = new Label("VS");
        vsLabel.getStyleClass().addAll("vs-text", "stat");

        HBox battleRow = new HBox(40, youBox, vsLabel, dealerBox);
        battleRow.setAlignment(Pos.CENTER);

        Separator topSep = new Separator();
        Separator bottomSep = new Separator();

        outcomeLabel.getStyleClass().addAll("outcome-text", "big-outcome", "stat");

        Label betLabel = new Label("Bet Amount:");
        betLabel.getStyleClass().add("stat");

        betField.setPromptText("Enter whole number");
        betField.setPrefColumnCount(8);

        Button playBtn = new Button("PLAY WAR");
        playBtn.getStyleClass().add("green");
        playBtn.setOnAction(_ -> onPlay());
        betField.setOnAction(_ -> onPlay());

        HBox betRow = new HBox(12, betLabel, betField, playBtn);
        betRow.setAlignment(Pos.CENTER);

        bet10Button = createQuickBetButton(10);
        bet25Button = createQuickBetButton(25);
        bet50Button = createQuickBetButton(50);
        bet100Button = createQuickBetButton(100);

        HBox chipsRow = new HBox(10, bet10Button, bet25Button, bet50Button, bet100Button);
        chipsRow.setAlignment(Pos.CENTER);

        VBox gamePanel = new VBox(
                14,
                bannerRow,
                topSep,
                battleRow,
                bottomSep,
                outcomeLabel,
                betRow,
                chipsRow
        );
        gamePanel.setAlignment(Pos.TOP_CENTER);
        gamePanel.setPadding(new Insets(20, 40, 25, 40));

        gamePanel.getStyleClass().add("game-panel");

        VBox centerWrapper = new VBox(gamePanel);
        centerWrapper.setAlignment(Pos.CENTER);

        setCenter(centerWrapper);
    }

    private void buildBottomBar() {
        Button backBtn = new Button("Back to Menu");
        backBtn.getStyleClass().add("red");
        backBtn.setOnAction(_ -> {
            CoinBalance.balance += CoinBalance.gameBalance;
            CoinBalance.gameBalance = 0;

            AccountManager.updateBalance(MenuView.currentUsername, CoinBalance.balance);
            AccountManager.saveAccounts();
            if (getScene() != null) {
                getScene().setRoot(new MenuView());
            }
        });

        HBox bottomBar = new HBox(backBtn);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(25, 0, 0, 0));

        setBottom(bottomBar);
    }

    private Button createQuickBetButton(int amount) {
        Button button = new Button(String.valueOf(amount));
        button.getStyleClass().add("chip-button");
        button.setMinWidth(60);
        button.setOnAction(_ -> {
            betField.setText(String.valueOf(amount));
            bannerLabel.setText("Bet set to " + amount + " MAAD Coins");
        });
        return button;
    }

    private void onPlay() {
        String raw = betField.getText();
        int bet;

        if (raw == null || raw.trim().isEmpty()) {
            outcomeLabel.setText("Please enter a bet amount.");
            return;
        }

        try {
            bet = Integer.parseInt(raw.trim());
        } catch (NumberFormatException exception) {
            outcomeLabel.setText("Enter a whole number bet.");
            return;
        }

        if (bet <= 0) {
            outcomeLabel.setText("Bet must be greater than 0.");
            return;
        }

        if (bet > CoinBalance.gameBalance) {
            outcomeLabel.setText("Not enough MAAD Coins!");
            return;
        }

        WarLogic.RoundResult result = war.playRound(bet);

        roundCount++;
        roundLabel.setText("Round: " + roundCount);

        playerCardLabel.setText(WarLogic.cardToString(result.playerCard));
        dealerCardLabel.setText(WarLogic.cardToString(result.dealerCard));
        outcomeLabel.setText(result.summary(bet));

        switch (result.outcome) {
            case PLAYER_WIN -> bannerLabel.setText("You win the battle!");
            case DEALER_WIN -> bannerLabel.setText("Dealer takes this one...");
            case PUSH -> bannerLabel.setText("It's a tie – WAR continues!");
            case INVALID -> bannerLabel.setText("Invalid bet – check your coins.");
        }

        updateCoins();
    }

    private void updateCoins() {
        coinsLabel.setText("MAAD Coins: " + CoinBalance.gameBalance);
    }
}
