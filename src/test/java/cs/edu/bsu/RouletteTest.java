package cs.edu.bsu;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class RouletteTest {

    private void rigSpin(RouletteLogic logic, int fixedValue) {
        try {
            Field f = RouletteLogic.class.getDeclaredField("randomGenerator");
            f.setAccessible(true);
            Random rigged = new Random() {
                @Override
                public int nextInt(int bound) {
                    return Math.floorMod(fixedValue, bound);
                }
            };
            f.set(logic, rigged);
        } catch (Exception e) {
            throw new RuntimeException("Failed to rig spin: " + e.getMessage(), e);
        }
    }

    @Test
    void testGetColorBasics() {
        assertEquals("GREEN", RouletteLogic.getColor(0), "0 should be GREEN");
        assertEquals("RED", RouletteLogic.getColor(1), "1 is a standard RED number");
        assertEquals("BLACK", RouletteLogic.getColor(2), "2 is a standard BLACK number");
    }

    @Test
    void testNumberBetWinIncreasesBalanceAndSaysYouWon() {
        CoinBalance.gameBalance = 1000;
        RouletteLogic logic = new RouletteLogic();

        // Force the wheel to land on 17
        int chosenNumber = 17; // (BLACK number)
        rigSpin(logic, chosenNumber);

        int start = (int) CoinBalance.getGameBalance();
        int betAmount = 10;

        RouletteLogic.RoundResult result =
                logic.playRound(RouletteLogic.BetType.NUMBER, chosenNumber, betAmount);

        System.out.println("SUMMARY: " + result.getSummary());
        System.out.println("ROLLED: " + result.rolledNumber + " (" + result.rolledColor + ")");
        System.out.println("BALANCE: " + CoinBalance.getGameBalance());

        assertTrue(result.getSummary().contains("You won"), "Expected a win message.");
        assertEquals(chosenNumber, result.rolledNumber, "Rolled number should match rigged value.");
        assertTrue(CoinBalance.getGameBalance() > start, "Balance should increase on a correct NUMBER bet.");
    }

    @Test
    void testEvenBetWinWhenRolledIsEven() {
        CoinBalance.gameBalance = 500;
        RouletteLogic logic = new RouletteLogic();

        rigSpin(logic, 24);

        long before = CoinBalance.getGameBalance();
        int betAmount = 50;

        RouletteLogic.RoundResult result =
                logic.playRound(RouletteLogic.BetType.EVEN, -1, betAmount);

        System.out.println("SUMMARY: " + result.getSummary());
        System.out.println("BALANCE BEFORE: " + before + " AFTER: " + CoinBalance.getGameBalance());

        assertTrue(result.getSummary().contains("You won"), "Expected a win on EVEN with an even roll.");
        assertTrue(CoinBalance.getGameBalance() > before, "Balance should increase on a winning EVEN bet.");
    }

    @Test
    void testRedBetLosesOnBlackNumber() {
        CoinBalance.gameBalance = 300;
        RouletteLogic logic = new RouletteLogic();

        rigSpin(logic, 2);

        long before = CoinBalance.getGameBalance();
        int betAmount = 30;

        RouletteLogic.RoundResult result =
                logic.playRound(RouletteLogic.BetType.RED, -1, betAmount);

        System.out.println("SUMMARY: " + result.getSummary());
        System.out.println("BALANCE BEFORE: " + before + " AFTER: " + CoinBalance.getGameBalance());

        assertTrue(result.getSummary().contains("You lost"), "Expected a loss on RED when BLACK is rolled.");
        assertEquals(before - betAmount, CoinBalance.getGameBalance(),
                "On a loss, balance should decrease exactly by the bet amount.");
    }

    @Test
    void testOddBetLosesOnZero() {
        CoinBalance.gameBalance = 400;
        RouletteLogic logic = new RouletteLogic();

        rigSpin(logic, 0);

        long before = CoinBalance.getGameBalance();
        int betAmount = 25;

        RouletteLogic.RoundResult result =
                logic.playRound(RouletteLogic.BetType.ODD, -1, betAmount);

        System.out.println("SUMMARY: " + result.getSummary());
        System.out.println("BALANCE BEFORE: " + before + " AFTER: " + CoinBalance.getGameBalance());

        assertTrue(result.getSummary().contains("You lost"), "Expected a loss on ODD when 0 is rolled.");
        assertEquals(before - betAmount, CoinBalance.getGameBalance(),
                "On a loss, balance should decrease exactly by the bet amount.");
    }
}
