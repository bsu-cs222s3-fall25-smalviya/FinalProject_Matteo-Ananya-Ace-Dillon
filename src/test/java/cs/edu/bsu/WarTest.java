package cs.edu.bsu;

import org.junit.jupiter.api.Test;
import java.util.Random;

public class WarTest {
    Random random = new Random();
    //random cards
    int lowest = 2;
    int highest = 14;

    int playerShuffle = random.nextInt(highest - lowest + 1) + lowest;
    int dealerShuffle = random.nextInt(highest - lowest + 1) + lowest;

    @Test
    void testWarGame() {
        // tests length
        System.out.println("\n");
        System.out.println("Players card: " + playerShuffle);
        System.out.println("Dealers card: " + dealerShuffle);
        System.out.println("\n");

    }
}
