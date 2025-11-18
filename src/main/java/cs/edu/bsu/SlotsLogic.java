package cs.edu.bsu;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SlotsLogic {
    static Random rand = new Random();

    static int currentBet = 0;
    static int coinsWon = 0;

    static String[] words = {"Cherry", "Lemon", "Orange", "Bell", "Diamond", "Lucky Seven"};

    private static final double[] getMultipler2 = {1.0, 1.5, 2.0, 2.5, 3.0, 4.5};
    private static final double[] getMultipler3 = {5.5, 6.5, 7.0, 8.5, 10.0, 100.0};
    static String matchedSymbol = null;


    static String word1;
    static String word2;
    static String word3;
    static int item1;
    static int item2;
    static int item3;

    static boolean sufficientBalance = true;

    public static boolean spin() {
        if (currentBet <= CoinBalance.gameBalance) {
            CoinBalance.gameBalance -= currentBet;

            item1 = rand.nextInt(words.length);
            item2 = rand.nextInt(words.length);
            item3 = rand.nextInt(words.length);

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

    public static void payout() {
        if (item1 == item2 && item1 == item3) {
            coinsWon = (int) (currentBet * getMultipler3[item1]);
        } else if (item1 == item2 || item1 == item3) {
            coinsWon = (int) (currentBet * getMultipler2[item1]);
        } else if (item2 == item3) {
            coinsWon = (int) (currentBet * getMultipler2[item2]);
        } else {
            coinsWon = 0;
        }

        CoinBalance.gameBalance += coinsWon;
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