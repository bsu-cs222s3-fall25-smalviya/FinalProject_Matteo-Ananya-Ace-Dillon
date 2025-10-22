package cs.edu.bsu;
/*
----------------
RULES OF WAR
------------
There are 52 cards in total, which are split between the player and the dealer (computer).
Both the player and the dealer will have 26 cards. Each round, both the player and dealer
flip a card over, whichever cards value is highest will win that round.
----------------
CARD VALUES
-----------
2, 3, 4, 5, 6, 7, 8, 9, 10
Jack - 11
Queen - 12
King - 13
Ace - 14
----------------
*/
import java.util.Random;

public class WarLogic {
    // list of suit symbols for card deck
    String[] suit = {
            "♠", // Spade
            "♥", // Heart
            "♦", // Diamond
            "♣"  // Club
    };
    // list of court cards
    String[] court = {
            "Jack", // 11 value
            "Queen", // 12 value
            "King", // 13 value
            "Ace" // 14 value
    };

/*
    Random random = new Random();

    //random cards
    int lowest = 2;
    int highest = 14;

    int playerShuffle = random.nextInt(highest - lowest + 1) + lowest;
    int dealerShuffle = random.nextInt(highest - lowest + 1) + lowest;
*/
}
