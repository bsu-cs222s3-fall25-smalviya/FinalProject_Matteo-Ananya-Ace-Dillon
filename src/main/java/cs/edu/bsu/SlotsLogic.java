package cs.edu.bsu;

/*
---------------
RULES OF SLOTS
--------------
The player bets an amount of money per spin. When the player spins, they have to match symbols to win.
Two matches gives the players money back. All three symbols gives out more money. There will be multiple
different symbols, with symbol being worth different amount of money, if the play gets all three to match.

🍒 Cherry – Lowest payout
🍋 Lemon – Second-lowest payout.
🍊 Orange - Medium-payout.
🔔 Bell – High-payout.
💎 Diamond – Very high payout.
⑦ Lucky Seven – Biggest payout.


50 coins max
25
10
5
1 coin min
*/

import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SlotsLogic {
    static Random rand = new Random();
    static long balance = CoinBalance.getBalance();
    static int currentBet = 0;

    // list of symbols/names for slot results
    static String[] symbols = {"🍒", "🍋", "🍊", "🔔", "💎", "⑦"};
    static String[] words = {"Cherry", "Lemon", "Orange", "Bell", "Diamond", "Lucky Seven"};

    // two-of-a-kind payout multipliers
    private static final double[] getMultipler2 = {1.0, 1.5, 2.0, 2.5, 3.5, 5.0};

    // three-of-a-kind payout multipliers
    private static final double[] getMultipler3 = {2.0, 2.5, 3.0, 4.5, 5.0, 100.0};

    // assigning
    static String symbol1;
    static String symbol2;
    static String symbol3;
    static String word1;
    static String word2;
    static String word3;
    static int item1;
    static int item2;
    static int item3;

    static int coinsWon = 0;
    static String matchedSymbol = null;

    static boolean insufficientBalance = false;

    // logic for spins
    public static boolean spin() {
        if (currentBet <= balance) {
            // these hold the length number that is assigned per Symbol, 0-5
            item1 = rand.nextInt(symbols.length);
            item2 = rand.nextInt(symbols.length);
            item3 = rand.nextInt(symbols.length);

            // takes the random Symbols chosen, and assigns to symbols. These hold the emojicons
            symbol1 = symbols[item1];
            symbol2 = symbols[item2];
            symbol3 = symbols[item3];

            // names of Symbols corresponds with Symbols chosen. These hold the Names
            word1 = words[item1];
            word2 = words[item2];
            word3 = words[item3];


            String[] spin = {word1, word2, word3};
            Map<String, Integer> counts = new HashMap<>();
            // keeps track of how many times each symbol appears on a spin
            for (String s : spin) {
                counts.put(s, counts.getOrDefault(s, 0) + 1);
            }

            // scans which symbol appears the most
            int matchedCount = 0;
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                if (entry.getValue() > matchedCount) {
                    matchedSymbol = entry.getKey();
                    matchedCount = entry.getValue();
                }
            }
            return insufficientBalance = false;
        } else {
            return insufficientBalance = true;
        }
    }

    // BET LOGIC

    public static void setBet(int bet) {
        currentBet = bet;
    }

    public static long payout() {
        // only pays out when the slot machine is has been spun
        if (symbol1 == null || symbol2 == null || symbol3 == null) {
            return balance;
        }
        balance -= currentBet;
        if (item1 == item2 && item1 == item3) { // three match
            coinsWon = (int) (currentBet * getMultipler3[item1]);
        } else if (item1 == item2 || item1 == item3) { // two match
            coinsWon = (int) (currentBet * getMultipler2[item1]);
        } else if (item2 == item3) { // two match
            coinsWon = (int) (currentBet * getMultipler2[item2]);
        } else {
            coinsWon = 0;
        }

        balance += coinsWon;
        return balance;
    }

    // outputs symbols on machine
    public static String spinResults() {
        return "| " + symbol1 + " | " + symbol2 + " | " + symbol3 + " |";
    }

    // output for win/loss display
    public static String score() {
        // logic
        if (word1.equals(word2) && word1.equals(word3)) {
            // all three match
            return "Three " + matchedSymbol + "s!" + "\n" + "YOU WON: " + coinsWon + " MAAD Coins!";
        } else if (word1.equals(word2) || word1.equals(word3) || word2.equals(word3)) {
            // two match
            return "Two " + matchedSymbol + "s!" + "\n" + "YOU WON: " + coinsWon + " MAAD Coins!";
        } else {
            // no match
            return "No match...\n";
        }
    }
}