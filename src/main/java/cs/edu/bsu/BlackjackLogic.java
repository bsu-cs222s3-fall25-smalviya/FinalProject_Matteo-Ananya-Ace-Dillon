package cs.edu.bsu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlackjackLogic {
    static Random random = new Random();
    static String randomCard = " ";

    static boolean hit = false; // draw another card
    static boolean stand = false; // stop taking cards
    static boolean doubleDown = false; // double bet by 2x and take a card
    static boolean bust = false; // player/dealer losses
    static boolean push = false; // tie

    static List<String> hearts = new ArrayList<>(Arrays.asList("2Hearts", "3Hearts", "4Hearts", "5Hearts",
            "6Hearts", "7Hearts" ,"8Hearts" ,"9Hearts" ,"10Hearts", "jackHearts",
            "queenHearts", "kingHearts", "aceHearts"));

    static List<String> clubs = new ArrayList<>(Arrays.asList("2Clubs", "3Clubs", "4Clubs", "5Clubs",
            "6Clubs", "7Clubs" ,"8Clubs" ,"9Clubs" ,"10Clubs", "jackClubs",
            "queenClubs", "kingClubs", "aceClubs"));

    static List<String> diamonds = new ArrayList<>(Arrays.asList("2Diamonds", "3Diamonds", "4Diamonds", "5Diamonds",
            "6Diamonds", "7Diamonds" ,"8Diamonds" ,"9Diamonds" ,"10Diamonds", "jackDiamonds",
            "queenDiamonds", "kingDiamonds", "aceDiamonds"));

    static List<String> spades = new ArrayList<>(Arrays.asList("2Spades", "3Spades", "4Spades", "5Spades",
            "6Spades", "7Spades" ,"8Spades" ,"9Spades" ,"10Spades", "jackSpades",
            "queenSpades", "kingSpades", "aceSpades"));

    int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1};

    static ArrayList<String> fullDeck = new ArrayList<>();
    static ArrayList<String> dealerHand = new ArrayList<>();
    static ArrayList<String> playerHand = new ArrayList<>();

    static boolean newMatch = true;


    public static void play() {
        fullDeck.addAll(hearts);
        fullDeck.addAll(clubs);
        fullDeck.addAll(diamonds);
        fullDeck.addAll(spades);

        setDealerCards();
        setPlayerCards();

    }

    public static void setDealerCards() {
        dealerHand.clear();
        dealerHand.add(fullDeck.get(random.nextInt(fullDeck.size())));
        dealerHand.add(fullDeck.get(random.nextInt(fullDeck.size())));
    }

    public static void setPlayerCards() {
        playerHand.clear();
        playerHand.add(fullDeck.get(random.nextInt(fullDeck.size())));
        playerHand.add(fullDeck.get(random.nextInt(fullDeck.size())));
    }


    public static String playerHit() {
        String newCard;
        newCard = fullDeck.get(random.nextInt(fullDeck.size()));
        return newCard;
    }

    public static void dealersPlay() {

    }

    public static void totalValueCalculator() {
        //playerHand
        //if (totalValue > 21) {
            bust = true;
        //}
    }
}
