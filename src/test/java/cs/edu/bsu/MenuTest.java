package cs.edu.bsu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
    @Test
    void testUserCoinBalance() {
        // should be the starting balance (1000) MAAD Coins
        System.out.println("Player's Balance: " + MenuView.balance);
        assertEquals(1000, MenuView.balance);
    }

    @Test
    void testCoinBalanceChange() {
        long newBalance = MenuView.balance * 5;
        assertEquals(5000, newBalance); // should be (5000 = 5 * (1000))
    }
}
