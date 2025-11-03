package cs.edu.bsu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlackjackLogic {
    static Random random = new Random();

    static boolean hit = false; // draw another card
    static boolean stand = false; // stop taking cards
    static boolean doubleDown = false; // double bet by 2x and take a card
    static boolean bust = false; // player/dealer losses
    static boolean push = false; // tie

    static List<Integer> values = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11);

    static ArrayList<Integer> dealerHand = new ArrayList<>();
    static ArrayList<Integer> playerHand = new ArrayList<>();

    static boolean newMatch = true;


    public static void play() {
        setDealerCards();
        setPlayerCards();
    }

    public static void setDealerCards() {
        dealerHand.clear();
        dealerHand.add(random.nextInt(values.size()));
        dealerHand.add(random.nextInt(values.size()));
    }

    public static void setPlayerCards() {
        playerHand.clear();
        playerHand.add(random.nextInt(values.size()));
        playerHand.add(random.nextInt(values.size()));
    }


    public static void playerHit() {
        int randomIndex = random.nextInt(values.size());
        int randomCard = values.get(randomIndex);
        playerHand.add(randomCard);
    }

    public static void dealersPlay() {
    }

    public static int totalValueCalculator() {
        int totalValue = 0;

        for (int i : playerHand) {
            totalValue += i;
        }
        if (totalValue > 21) {
            bust = true;
        }
        return totalValue;
    }
}
