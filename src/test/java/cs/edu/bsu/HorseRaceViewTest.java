package cs.edu.bsu;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class HorseRaceViewTest {

    @BeforeAll
    static void initJavaFxToolkit() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
        }
    }

    @Test
    void testInitialBalanceLabelMatchesCoinBalance() {
        CoinBalance.gameBalance = 1234;
        HorseRaceView view = new HorseRaceView();
        VBox top = (VBox) view.getTop();
        Label balanceLabel = (Label) top.getChildren().get(1);
        assertEquals("Balance: " + CoinBalance.getGameBalance(), balanceLabel.getText());
    }

    @Test
    void testStatusLabelShowsInitialPrompt() {
        CoinBalance.balance = 1000;
        HorseRaceView view = new HorseRaceView();
        VBox top = (VBox) view.getTop();
        Label statusLabel = (Label) top.getChildren().get(2);
        assertTrue(statusLabel.getText().contains("Place your bet"));
    }

    @Test
    void testBottomBarHasBackButton() {
        HorseRaceView view = new HorseRaceView();
        HBox bottom = (HBox) view.getBottom();
        boolean hasBack = bottom.getChildren().stream()
                .anyMatch(n -> n instanceof Button btn && btn.getText().contains("Back"));
        assertTrue(hasBack);
    }

    @Test
    void testHorseChoiceComboBoxHasCorrectNumberOfItems() {
        HorseRaceView view = new HorseRaceView();
        HBox bottom = (HBox) view.getBottom();
        ComboBox<?> combo = bottom.getChildren().stream()
                .filter(n -> n instanceof ComboBox<?>)
                .map(n -> (ComboBox<?>) n)
                .findFirst()
                .orElse(null);
        assertNotNull(combo);
        assertEquals(HorseRaceLogic.getNumHorses(), combo.getItems().size());
    }

    @Test
    void testStartRaceValidationErrors() {
        CoinBalance.gameBalance = 1000;
        HorseRaceView view = new HorseRaceView();
        HBox bottom = (HBox) view.getBottom();
        TextField bet = null;
        ComboBox<Integer> horses = null;
        Button start = null;
        for (Node n : bottom.getChildren()) {
            if (n instanceof TextField tf) bet = tf;
            else if (n instanceof ComboBox<?> cb) horses = (ComboBox<Integer>) cb;
            else if (n instanceof Button b && b.getText().startsWith("Start")) start = b;
        }
        assertNotNull(bet);
        assertNotNull(horses);
        assertNotNull(start);

        VBox top = (VBox) view.getTop();
        Label status = (Label) top.getChildren().get(2);

        horses.getSelectionModel().select(1);
        bet.setText("");
        start.fire();
        assertTrue(status.getText().contains("Enter a bet"));

        bet.setText("abc");
        start.fire();
        assertTrue(status.getText().contains("number"));

        bet.setText("0");
        start.fire();
        assertTrue(status.getText().contains("positive"));

        bet.setText("99999");
        start.fire();
        assertTrue(status.getText().contains("enough balance"));

        bet.setText("50");
        horses.getSelectionModel().clearSelection();
        start.fire();
        assertTrue(status.getText().contains("Choose a horse"));
    }

    @Test
    void testValidStartRaceSubtractsBalanceAndDisablesInputs() {
        CoinBalance.gameBalance = 1000;
        HorseRaceView view = new HorseRaceView();

        HBox bottom = (HBox) view.getBottom();
        TextField bet = null;
        ComboBox<Integer> horses = null;
        Button start = null;

        for (Node n : bottom.getChildren()) {
            if (n instanceof TextField tf) bet = tf;
            else if (n instanceof ComboBox<?> cb) horses = (ComboBox<Integer>) cb;
            else if (n instanceof Button b && b.getText().startsWith("Start")) start = b;
        }

        assertNotNull(bet);
        assertNotNull(horses);
        assertNotNull(start);

        bet.setText("100");
        horses.getSelectionModel().select(1);
        start.fire();

        VBox top = (VBox) view.getTop();
        Label balanceLabel = (Label) top.getChildren().get(1);
        Label statusLabel = (Label) top.getChildren().get(2);

        assertEquals("Balance: 900", balanceLabel.getText());
        assertEquals("Race in progress...", statusLabel.getText());

        assertTrue(start.isDisabled());
        assertTrue(bet.isDisabled());
        assertTrue(horses.isDisabled());
    }

    @Test
    void testStepRaceMovesHorses() throws Exception {
        CoinBalance.balance = 1000;
        HorseRaceView view = new HorseRaceView();

        HBox bottom = (HBox) view.getBottom();
        TextField bet = null;
        ComboBox<Integer> horses = null;
        Button start = null;

        for (Node n : bottom.getChildren()) {
            if (n instanceof TextField tf) bet = tf;
            else if (n instanceof ComboBox<?> cb) horses = (ComboBox<Integer>) cb;
            else if (n instanceof Button b && b.getText().startsWith("Start")) start = b;
        }

        assertNotNull(bet);
        assertNotNull(horses);
        assertNotNull(start);

        bet.setText("50");
        horses.getSelectionModel().select(1);
        start.fire();

        VBox lanes = (VBox) view.getCenter();
        double[] before = new double[lanes.getChildren().size()];

        for (int i = 0; i < lanes.getChildren().size(); i++) {
            HBox lane = (HBox) lanes.getChildren().get(i);
            Rectangle rect = (Rectangle) lane.getChildren().get(1);
            before[i] = rect.getTranslateX();
        }

        var stepRace = HorseRaceView.class.getDeclaredMethod("stepRace");
        stepRace.setAccessible(true);
        stepRace.invoke(view);

        boolean moved = false;
        for (int i = 0; i < lanes.getChildren().size(); i++) {
            HBox lane = (HBox) lanes.getChildren().get(i);
            Rectangle rect = (Rectangle) lane.getChildren().get(1);
            if (rect.getTranslateX() > before[i]) {
                moved = true;
                break;
            }
        }
        assertTrue(moved, "At least one horse should move after stepRace() is called");
    }
}
