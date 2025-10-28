package cs.edu.bsu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WarTest {
    @Test
    void testCardToStringForNumberCards() {
        assertEquals("2", WarLogic.cardToString(2));
        assertEquals("10", WarLogic.cardToString(10));
    }

    @Test
    void testCardToStringForFaceCards() {
        assertEquals("Jack", WarLogic.cardToString(11));
        assertEquals("Queen", WarLogic.cardToString(12));
        assertEquals("King", WarLogic.cardToString(13));
        assertEquals("Ace", WarLogic.cardToString(14));
    }

    @Test
    void testSummaryForPlayerWin() {
        WarLogic.RoundResult result =
                new WarLogic.RoundResult(10, 5, WarLogic.Outcome.PLAYER_WIN);
        assertEquals("You win 50 MAAD coins!", result.summary(50));
    }

    @Test
    void testSummaryForDealerWIn() {
        WarLogic.RoundResult result = new WarLogic.RoundResult(3, 12, WarLogic.Outcome.DEALER_WIN);
        assertEquals("Dealer wins. You lose 25.", result.summary(25));
    }

    @Test
    void testSummaryForPush() {
        WarLogic.RoundResult result = new WarLogic.RoundResult(8, 8, WarLogic.Outcome.PUSH);
        assertEquals("Push. No one wins.", result.summary(25));
    }
}
