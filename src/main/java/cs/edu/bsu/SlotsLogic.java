package cs.edu.bsu;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SlotsLogic {

    static Random rand = new Random();
    static int currentBet = 0;

    static String[] symbols = {"🍒", "🍋", "🍊", "🔔", "💎", "⑦"};
    static String[] words = {"Cherry", "Lemon", "Orange", "Bell", "Diamond", "Lucky Seven"};

    private static final double[] getMultipler2 = {1.0, 1.5, 2.0, 2.5, 3.5, 5.0};

    private static final double[] getMultipler3 = {2.0, 2.5, 3.0, 4.5, 5.0, 100.0};

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

    static boolean sufficientBalance = true;

    public static boolean spin() {
        if (currentBet <= CoinBalance.gameBalance) {
            CoinBalance.gameBalance -= currentBet;

            item1 = rand.nextInt(symbols.length);
            item2 = rand.nextInt(symbols.length);
            item3 = rand.nextInt(symbols.length);

            symbol1 = symbols[item1];
            symbol2 = symbols[item2];
            symbol3 = symbols[item3];

            word1 = words[item1];
            word2 = words[item2];
            word3 = words[item3];


            String[] spin = {word1, word2, word3};
            Map<String, Integer> counts = new HashMap<>();
            for (String s : spin) {
                counts.put(s, counts.getOrDefault(s, 0) + 1);
            }

            int matchedCount = 0;
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                if (entry.getValue() > matchedCount) {
                    matchedSymbol = entry.getKey();
                    matchedCount = entry.getValue();
                }
            }
            return sufficientBalance = true;
        } else {
            return sufficientBalance = false;
        }
    }


    public static void setBet(int bet) {
        currentBet = bet;
    }

    public static long payout() {
        if (symbol1 == null || symbol2 == null || symbol3 == null) {
            return CoinBalance.gameBalance;
        }

        if (item1 == item2 && item1 == item3) { // three match
            coinsWon = (int) (currentBet * getMultipler3[item1]);
        } else if (item1 == item2 || item1 == item3) { // two match
            coinsWon = (int) (currentBet * getMultipler2[item1]);
        } else if (item2 == item3) { // two match
            coinsWon = (int) (currentBet * getMultipler2[item2]);
        } else {
            coinsWon = 0;
        }

        CoinBalance.gameBalance += coinsWon;
        return CoinBalance.gameBalance;
    }

    public static String spinResults() {
        return "| " + symbol1 + " | " + symbol2 + " | " + symbol3 + " |";
    }

    public static String score() {
        if (word1.equals(word2) && word1.equals(word3)) {
            return "Three " + matchedSymbol + "s!" + "\n" + "YOU WON: " + coinsWon + " MAAD Coins!";
        } else if (word1.equals(word2) || word1.equals(word3) || word2.equals(word3)) {
            return "Two " + matchedSymbol + "s!" + "\n" + "YOU WON: " + coinsWon + " MAAD Coins!";
        } else {
            return "No match...\n";
        }
    }
}