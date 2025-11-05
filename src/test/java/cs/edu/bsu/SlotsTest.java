package cs.edu.bsu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SlotsTest {
    @Test
    void testSpinReturnsTrueWhenBalanceSufficient() {
        SlotsLogic.setBet(5);
        CoinBalance.gameBalance = 500;
        System.out.println("DEBUG currentBet: " + SlotsLogic.currentBet);
        System.out.println("DEBUG balance: " + CoinBalance.gameBalance);
        boolean result = SlotsLogic.spin();
        System.out.println("DEBUG spin returned" + result);
        assertTrue(result);
    }

    @Test
    void testSpinReturnsFalseWhenBalanceInsufficient() {
        SlotsLogic.setBet(99999);
        CoinBalance.gameBalance = 500;
        boolean result = SlotsLogic.spin();
        assertFalse(result);
    }

    @Test
    void testSpinSetsSymbols() {
        SlotsLogic.setBet(10);
        CoinBalance.gameBalance = 500;
        SlotsLogic.spin();
        assertNotNull(SlotsLogic.symbol1);
        assertNotNull(SlotsLogic.symbol2);
        assertNotNull(SlotsLogic.symbol3);
    }

    @Test
    void testScoreForThreeMatch() {
        SlotsLogic.word1 = "Cherry";
        SlotsLogic.word2 = "Cherry";
        SlotsLogic.word3 = "Cherry";
        SlotsLogic.coinsWon = 50;
        SlotsLogic.matchedSymbol = "Cherry";
        String result = SlotsLogic.score();
        assertTrue(result.contains("Three Cherry"));
    }

    @Test
    void testScoreForTwoMatch() {
        SlotsLogic.word1 = "Cherry";
        SlotsLogic.word2 = "Cherry";
        SlotsLogic.word3 = "Lemon";
        SlotsLogic.coinsWon = 20;
        SlotsLogic.matchedSymbol = "Cherry";
        String result = SlotsLogic.score();
        assertTrue(result.contains("Two Cherry"));
    }

    @Test
    void testScoreForNoMatch() {
        SlotsLogic.word1 = "Cherry";
        SlotsLogic.word2 = "Lemon";
        SlotsLogic.word3 = "Bell";
        String result = SlotsLogic.score();
        assertEquals("No match...\n", result);
    }
}
