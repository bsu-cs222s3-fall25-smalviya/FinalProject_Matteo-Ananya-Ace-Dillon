package cs.edu.bsu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlackjackLogic {
    static Random random = new Random();
    static int randomCard = 0;
    static int randomIndex = 0;

    static boolean hit = false; // draw another card
    static boolean stand = false; // stop taking cards
    static boolean doubleDown = false; // double bet by 2x and take a card

    static boolean dealerRegularWin = false; // win from beating the player
    static boolean playerRegularWin = false; // win from beating the dealer (1:1 payout)

    static boolean dealerBlackjack = false; // win from first two cards while beating the player
    static boolean playerBlackjack = false; // win from first two cards while beating the dealer (3:2 payout)

    static boolean playerBust = false; // player losses (lose the money you bet)
    static boolean dealerBust = false; // dealer losses

    static boolean push = false; // tie (get money back)

    static List<Integer> values = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11);

    static ArrayList<Integer> dealerHand = new ArrayList<>();
    static ArrayList<Integer> playerHand = new ArrayList<>();

    static boolean newMatch = true;


    public static void set() {
        resetFlags();
        dealerHand.clear();
        playerHand.clear();
        setDealerCards();
        setPlayerCards();
        blackjackOutcome();
    }

    public static void setDealerCards() {
        for (int i = 0; i < 2; i++) {
            randomIndex = random.nextInt(values.size());
            randomCard = values.get(randomIndex);
            dealerHand.add(randomCard);
        }
        blackjackOutcome();
    }

    public static void setPlayerCards() {
        for (int i = 0; i < 2; i++) {
            randomIndex = random.nextInt(values.size());
            randomCard = values.get(randomIndex);
            playerHand.add(randomCard);
        }
        blackjackOutcome();
    }


    public static void playerHit() {
        randomIndex = random.nextInt(values.size());
        randomCard = values.get(randomIndex);
        playerHand.add(randomCard);
        totalValueCalculatorPlayer();
        pushOutcome();
        win();
    }

    public static void dealerHit() {
        while (totalValueCalculatorDealer() < 17) {
            int randomIndex = random.nextInt(values.size());
            int randomCard = values.get(randomIndex);
            dealerHand.add(randomCard);
            totalValueCalculatorDealer();
            pushOutcome();
            win();
        }
    }

    public static int totalValueCalculatorPlayer() {
        int totalValue = 0;

        for (int i : playerHand) {
            totalValue += i;
            if (totalValue > 21 && playerHand.contains(11)) {
                int index = playerHand.lastIndexOf(11);
                playerHand.set(index, 1);
                totalValue -= 10;
            }
            if (totalValue > 21) {
                playerBust = true;
            }
        }

        return totalValue;
    }

    public static int totalValueCalculatorDealer() {
        int totalValue = 0;

        for (int i : dealerHand) {
            totalValue += i;
            if (totalValue > 21 && dealerHand.contains(11)) {
                int index = dealerHand.lastIndexOf(11);
                dealerHand.set(index, 1);
                totalValue -= 10;
            }
            if (totalValue > 21) {
                dealerBust = true;
            }
        }

        return totalValue;
    }

    public static void pushOutcome() {
        if ((totalValueCalculatorDealer() == totalValueCalculatorPlayer()) && BlackjackView.wasClicked) {
            push = true;
        }
    }

    public static void blackjackOutcome() {
        if (totalValueCalculatorDealer() == 21) {
            dealerBlackjack = true;
        } else if (totalValueCalculatorPlayer() == 21) {
            playerBlackjack = true;
        }
    }

    public static void win() {
        if ((totalValueCalculatorDealer() > totalValueCalculatorPlayer()) && BlackjackView.wasClicked) {
            dealerRegularWin = true;
        } else if ((totalValueCalculatorDealer() < totalValueCalculatorPlayer()) && BlackjackView.wasClicked) {
            playerRegularWin = true;
        }
    }

    public static void resetFlags() {
        playerBust = false;
        dealerBust = false;

        playerBlackjack = false;
        dealerBlackjack = false;

        playerRegularWin = false;
        dealerRegularWin = false;

        push = false;
    }
}