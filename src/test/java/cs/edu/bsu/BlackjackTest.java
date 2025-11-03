package cs.edu.bsu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackTest {
    @Test
    void testSetCards() {
        BlackjackLogic.play();
        System.out.println("Dealer set hand: " + BlackjackLogic.dealerHand);
        System.out.println("Player set hand: " + BlackjackLogic.playerHand);
    }

    @Test
    void testPlayerHand() {
        BlackjackLogic.play();
        for (int i = 0; i < 1; i++) {
            BlackjackLogic.playerHit();
        }
        System.out.println("PLAYER TOTAL HAND: " + BlackjackLogic.playerHand);
        System.out.println("Total value for player hand: " + BlackjackLogic.totalValueCalculator());
        System.out.println(BlackjackLogic.bust);
    }

}
