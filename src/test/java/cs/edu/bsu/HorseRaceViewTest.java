package cs.edu.bsu;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class HorseRaceViewTest {

    static {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
        }
    }

    private HorseRaceView view;

    @BeforeEach
    void setUp() throws Exception {
        MenuView.currentUsername = "TestPlayer";
        CoinBalance.gameBalance = 100;
        CoinBalance.balance = 1000;

        view = createViewOnFxThread();
        assertNotNull(view);
    }

    private HorseRaceView createViewOnFxThread() throws Exception {
        AtomicReference<HorseRaceView> ref = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            ref.set(new HorseRaceView());
            latch.countDown();
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "FX thread did not finish in time");
        return ref.get();
    }

    private void runOnFxAndWait(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS), "FX action did not finish in time");
    }

    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object target, String name, Class<T> type) {
        try {
            Field f = target.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return (T) f.get(target);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Could not access field '" + name + "': " + e.getMessage());
            return null;
        }
    }

    @Test
    void initialHorseChoiceHasCorrectNumberOfItems() {
        ComboBox<Integer> horseChoice = getPrivateField(view, "horseChoice", ComboBox.class);
        assertNotNull(horseChoice);

        int expected = HorseRaceLogic.getNumHorses();
        assertEquals(expected, horseChoice.getItems().size(),
                "ComboBox should have one entry per horse");
    }

    @Test
    void initialBalanceLabelUsesGameBalance() {
        Label balanceLabel = getPrivateField(view, "balanceLabel", Label.class);
        assertNotNull(balanceLabel);

        String expected = "MAAD Coins: " + CoinBalance.gameBalance;
        assertEquals(expected, balanceLabel.getText());
    }

    @Test
    void chipButtonSetsBetFieldTo25() throws Exception {
        TextField betField = getPrivateField(view, "betField", TextField.class);
        Button bet25Button = getPrivateField(view, "bet25Button", Button.class);

        assertNotNull(betField);
        assertNotNull(bet25Button);

        runOnFxAndWait(bet25Button::fire);

        assertEquals("25", betField.getText(), "Clicking 25-chip should set betField to 25");
    }

    @Test
    void startWithoutBetShowsEnterBetMessage() throws Exception {
        Button startButton = getPrivateField(view, "startButton", Button.class);
        Label statusLabel = getPrivateField(view, "statusLabel", Label.class);

        assertNotNull(startButton);
        assertNotNull(statusLabel);

        runOnFxAndWait(startButton::fire);

        assertEquals("Enter a bet amount or tap a chip.", statusLabel.getText());
    }

    @Test
    void nonNumericBetShowsWholeNumberMessage() throws Exception {
        TextField betField = getPrivateField(view, "betField", TextField.class);
        ComboBox<Integer> horseChoice = getPrivateField(view, "horseChoice", ComboBox.class);
        Button startButton = getPrivateField(view, "startButton", Button.class);
        Label statusLabel = getPrivateField(view, "statusLabel", Label.class);

        runOnFxAndWait(() -> {
            betField.setText("abc");
            horseChoice.setValue(1);
            startButton.fire();
        });

        assertEquals("Bet must be a whole number.", statusLabel.getText());
    }

    @Test
    void zeroOrNegativeBetShowsPositiveMessage() throws Exception {
        TextField betField = getPrivateField(view, "betField", TextField.class);
        ComboBox<Integer> horseChoice = getPrivateField(view, "horseChoice", ComboBox.class);
        Button startButton = getPrivateField(view, "startButton", Button.class);
        Label statusLabel = getPrivateField(view, "statusLabel", Label.class);

        runOnFxAndWait(() -> {
            betField.setText("0");
            horseChoice.setValue(1);
            startButton.fire();
        });

        assertEquals("Bet must be positive.", statusLabel.getText());

        runOnFxAndWait(() -> {
            betField.setText("-10");
            horseChoice.setValue(1);
            startButton.fire();
        });

        assertEquals("Bet must be positive.", statusLabel.getText());
    }

    @Test
    void betGreaterThanBalanceShowsNotEnoughCoinsMessage() throws Exception {
        CoinBalance.gameBalance = 50;

        TextField betField = getPrivateField(view, "betField", TextField.class);
        ComboBox<Integer> horseChoice = getPrivateField(view, "horseChoice", ComboBox.class);
        Button startButton = getPrivateField(view, "startButton", Button.class);
        Label statusLabel = getPrivateField(view, "statusLabel", Label.class);
        Label balanceLabel = getPrivateField(view, "balanceLabel", Label.class);

        runOnFxAndWait(() -> balanceLabel.setText("MAAD Coins: " + CoinBalance.gameBalance));

        runOnFxAndWait(() -> {
            betField.setText("100");
            horseChoice.setValue(1);
            startButton.fire();
        });

        assertEquals("You don't have enough MAAD Coins.", statusLabel.getText());
    }

    @Test
    void missingHorseSelectionShowsChooseHorseMessage() throws Exception {
        CoinBalance.gameBalance = 200;

        TextField betField = getPrivateField(view, "betField", TextField.class);
        ComboBox<Integer> horseChoice = getPrivateField(view, "horseChoice", ComboBox.class);
        Button startButton = getPrivateField(view, "startButton", Button.class);
        Label statusLabel = getPrivateField(view, "statusLabel", Label.class);

        runOnFxAndWait(() -> {
            betField.setText("50");
            horseChoice.setValue(null);
            startButton.fire();
        });

        assertEquals("Choose a horse to bet on.", statusLabel.getText());
    }

    @Test
    void validBetDecrementsGameBalanceAndDisablesInputs() throws Exception {
        CoinBalance.gameBalance = 100;

        TextField betField = getPrivateField(view, "betField", TextField.class);
        ComboBox<Integer> horseChoice = getPrivateField(view, "horseChoice", ComboBox.class);
        Button startButton = getPrivateField(view, "startButton", Button.class);

        Button bet10Button = getPrivateField(view, "bet10Button", Button.class);
        Button bet25Button = getPrivateField(view, "bet25Button", Button.class);
        Button bet50Button = getPrivateField(view, "bet50Button", Button.class);
        Button bet100Button = getPrivateField(view, "bet100Button", Button.class);

        runOnFxAndWait(() -> {
            betField.setText("20");
            horseChoice.setValue(1);
            startButton.fire();
        });

        assertEquals(80, CoinBalance.gameBalance, "Game balance should be debited by bet amount");
        assertTrue(startButton.isDisable(), "Start button should be disabled during race");
        assertTrue(betField.isDisable(), "Bet field should be disabled during race");
        assertTrue(horseChoice.isDisable(), "Horse choice should be disabled during race");
        assertTrue(bet10Button.isDisable());
        assertTrue(bet25Button.isDisable());
        assertTrue(bet50Button.isDisable());
        assertTrue(bet100Button.isDisable());
    }
}
