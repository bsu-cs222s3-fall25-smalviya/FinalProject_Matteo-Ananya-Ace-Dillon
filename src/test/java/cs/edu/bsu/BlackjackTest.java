package cs.edu.bsu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BlackjackTest {
    @BeforeEach
    void resetBeforeEach() {
        // Reset static collections
        BlackjackLogic.dealerHand = new ArrayList<>();
        BlackjackLogic.playerHand = new ArrayList<>();

        // Reset all static booleans
        BlackjackLogic.playerBust = false;
        BlackjackLogic.dealerBust = false;
        BlackjackLogic.playerBlackjack = false;
        BlackjackLogic.dealerBlackjack = false;
        BlackjackLogic.push = false;
        BlackjackLogic.playerRegularWin = false;
        BlackjackLogic.dealerRegularWin = false;

        // Reset other static fields
        BlackjackLogic.coinsWon = 0;
        BlackjackLogic.currentBet = 0;
        CoinBalance.gameBalance = 0;
    }

    @Test
    void testSetCards() {
        BlackjackLogic.set();
        System.out.println("Dealer set hand: " + BlackjackLogic.dealerHand);
        System.out.println("Player set hand: " + BlackjackLogic.playerHand);
        assertNotNull(BlackjackLogic.dealerHand);
        assertNotNull(BlackjackLogic.playerHand);
    }

    @Test
    void testPlayerHit() {
        BlackjackLogic.set();
        int hits = 4;
        for (int i = 0; i < hits; i++) {
            BlackjackLogic.playerHit();
            System.out.println("Random card: " + BlackjackLogic.randomCard);
        }
        System.out.println("PLAYERS HAND: " + BlackjackLogic.playerHand);
        System.out.println("COLLECTIVE VALUE: " + BlackjackLogic.totalValueCalculatorPlayer());
    }

    @Test
    void testPlayerBust() {
        BlackjackLogic.playerHand.addAll(List.of(10, 10, 2));
        BlackjackLogic.totalValueCalculatorPlayer();
        assertTrue(BlackjackLogic.playerBust);
    }

    @Test
    void testDealerBust() {
        BlackjackLogic.dealerHand.addAll(List.of(10, 10, 2));
        BlackjackLogic.totalValueCalculatorDealer();
        assertTrue(BlackjackLogic.dealerBust);
    }

    @Test
    void testPlayerBlackjack() {
        BlackjackLogic.playerHand.addAll(List.of(11, 10));
        BlackjackLogic.blackjackOutcome();
        assertTrue(BlackjackLogic.playerBlackjack);
    }

    @Test
    void testDealerBlackjack() {
        BlackjackLogic.dealerHand.addAll(List.of(11, 10));
        BlackjackLogic.blackjackOutcome();
        assertTrue(BlackjackLogic.dealerBlackjack);
    }

    @Test
    void testPush() {
        BlackjackLogic.playerHand.addAll(List.of(2, 3));
        BlackjackLogic.dealerHand.addAll(List.of(2, 3));
        BlackjackLogic.pushOutcome(true);
        assertTrue(BlackjackLogic.push);
    }

    @Test
    void testRegularWinPlayer() {
        BlackjackLogic.playerHand.addAll(List.of(10, 10));
        BlackjackLogic.dealerHand.addAll(List.of(11, 8));
        BlackjackLogic.win(true);
        assertTrue(BlackjackLogic.playerRegularWin);
    }

    @Test
    void testRegularWinDealer() {
        BlackjackLogic.playerHand.addAll(List.of(11, 8));
        BlackjackLogic.dealerHand.addAll(List.of(10, 10));
        BlackjackLogic.win(true);
        assertTrue(BlackjackLogic.dealerRegularWin);
    }

    @Test
    void testBalanceMinusBet() {
        CoinBalance.gameBalance = 500;
        BlackjackLogic.currentBet = 50;
        BlackjackLogic.set();
        System.out.println("Balance: " + CoinBalance.gameBalance);
    }

    @Test
    void testPayoutBlackjack() {
        CoinBalance.gameBalance = 500 - 50;
        BlackjackLogic.currentBet = 50;
        BlackjackLogic.playerBlackjack = true;
        BlackjackLogic.payoutCalculator();
        System.out.println("Coins won (blackjack): " + BlackjackLogic.coinsWon);
        System.out.println("Game Balance: " + CoinBalance.gameBalance);
    }

    @Test
    void testPayoutRegularWin() {
        CoinBalance.gameBalance = 400 - 100;
        BlackjackLogic.currentBet = 100;
        BlackjackLogic.playerRegularWin = true;
        BlackjackLogic.payoutCalculator();
        System.out.println("Coins won (regular win): " + BlackjackLogic.coinsWon);
        System.out.println("Game Balance: " + CoinBalance.gameBalance);
    }

    @Test
    void testPayoutDealerBust() {
        CoinBalance.gameBalance = 400 - 100;
        BlackjackLogic.currentBet = 100;
        BlackjackLogic.dealerBust = true;
        BlackjackLogic.payoutCalculator();
        System.out.println("Coins won (dealer bust): " + BlackjackLogic.coinsWon);
        System.out.println("Game Balance: " + CoinBalance.gameBalance);
    }

    @Test
    void testPayoutPush() {
        CoinBalance.gameBalance = 400 - 100;
        BlackjackLogic.currentBet = 100;
        BlackjackLogic.push = true;
        BlackjackLogic.payoutCalculator();
        System.out.println("Coins won (push): " + BlackjackLogic.coinsWon);
        System.out.println("Game Balance: " + CoinBalance.gameBalance);
    }
}