package cs.edu.bsu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackTest {
    @Test
    void testSuitPicker() {
        BlackjackLogic.play();
        for (int i = 0; i < BlackjackLogic.fullDeck.toArray().length; i++){
            System.out.println(BlackjackLogic.fullDeck.get(i));
        }
        assertNotNull(BlackjackLogic.fullDeck);
    }

    @Test
    void testRandomCard() {
        BlackjackLogic.play();
        assertNotNull(BlackjackLogic.randomCard);
        System.out.println("This is the random card: " + BlackjackLogic.randomCard);
    }

    @Test
    void testSetCards() {
        BlackjackLogic.play();
        System.out.println("DEALER HAND: " + BlackjackLogic.dealerHand);
        System.out.println("PLAYER HAND: " + BlackjackLogic.playerHand);
    }
}
