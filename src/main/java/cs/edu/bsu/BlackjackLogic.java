package cs.edu.bsu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlackjackLogic {
    static Random random = new Random();

    static int currentBet = 0;
    static long coinsWon = 0;

    static int randomCard = 0;
    static int randomIndex = 0;
    static int dealersSecondCard = 0;

    static boolean dealerRegularWin = false;
    static boolean playerRegularWin = false;

    static boolean dealerBlackjack = false;
    static boolean playerBlackjack = false;

    static boolean playerBust = false;
    static boolean dealerBust = false;

    static boolean push = false;

    static List<Integer> values = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11);
    static List<String> faces = Arrays.asList("J", "Q", "K", "10");
    static List<String> suits = Arrays.asList("C", "D", "S", "H");

    static ArrayList<Integer> dealerHand = new ArrayList<>();
    static ArrayList<Integer> playerHand = new ArrayList<>();

    static ArrayList<String> playerHandFiles = new ArrayList<>();
    static ArrayList<String> dealerHandFiles = new ArrayList<>();

    public static void set() {
        if (currentBet <= CoinBalance.gameBalance) {
            CoinBalance.gameBalance -= currentBet;
            resetFlags();
            dealerHand.clear();
            playerHand.clear();
            setDealerCards();
            setPlayerCards();
            blackjackOutcome();
        }
    }

    public static void setBet(int bet) {
        currentBet = bet;
    }

    public static void setDealerCards() {
        dealerHand.clear();
        dealerHandFiles.clear();

        randomIndex = random.nextInt(values.size());
        randomCard = values.get(randomIndex);
        dealerHand.add(randomCard);
        dealerHandFiles.add(getCard(randomCard));

        randomIndex = random.nextInt(values.size());
        randomCard = values.get(randomIndex);
        dealerHand.add(randomCard);
        dealersSecondCard = randomCard;

        blackjackOutcome();
    }

    public static void revealDealerSecondCard() {
        if (dealerHandFiles.size() < 2) {
            dealerHandFiles.add(getCard(dealersSecondCard));
        }
    }

    public static void setPlayerCards() {
        playerHand.clear();
        playerHandFiles.clear();
        for (int i = 0; i < 2; i++) {
            randomIndex = random.nextInt(values.size());
            randomCard = values.get(randomIndex);
            playerHand.add(randomCard);
            playerHandFiles.add(getCard(randomCard));
        }
        blackjackOutcome();
    }

    public static void playerHit() {
        randomIndex = random.nextInt(values.size());
        randomCard = values.get(randomIndex);
        playerHand.add(randomCard);
        playerHandFiles.add(getCard(randomCard));
        totalValueCalculatorPlayer();
    }

    public static void dealerHit() {
        while (totalValueCalculatorDealer() < 17) {
            int randomIndex = random.nextInt(values.size());
            int randomCard = values.get(randomIndex);
            dealerHand.add(randomCard);
            dealerHandFiles.add(getCard(randomCard));
            totalValueCalculatorDealer();
        }
        pushOutcome();
        win();
    }

    public static int totalValueCalculatorPlayer() {
        int totalValue = 0;
        playerBust = false;

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
        dealerBust = false;

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
        if (dealerHand.size() >= 2 && playerHand.size() >= 2 &&
                totalValueCalculatorDealer() == totalValueCalculatorPlayer()) {
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
        if (dealerHand.size() >= 2 && playerHand.size() >= 2) {
            int dealerTotal = totalValueCalculatorDealer();
            int playerTotal = totalValueCalculatorPlayer();

            if (dealerTotal > playerTotal && dealerTotal <= 21) {
                dealerRegularWin = true;
            } else if (playerTotal > dealerTotal && playerTotal <= 21) {
                playerRegularWin = true;
            }
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

    public static long payoutCalculator() {
        if (playerBlackjack) {
            coinsWon = (long) (currentBet * 4.5);
        } else if (playerRegularWin || dealerBust) {
            coinsWon = currentBet * 3L;
        } else if (push) {
            coinsWon = currentBet;
        } else {
            coinsWon = 0;
        }

        CoinBalance.gameBalance += coinsWon;

        return coinsWon;
    }

    public static void doubleDownCalculator() {
        CoinBalance.gameBalance -= currentBet;
        currentBet *= 2;
        playerHit();
    }

    public static String getCard(int value) {
        String face;
        String suit;

        Random randomFace = new Random();
        Random randomSuit = new Random();

        int randomFaceIndex = randomFace.nextInt(faces.size());
        String randomFaceString = faces.get(randomFaceIndex);
        int randomSuitIndex = randomSuit.nextInt(suits.size());
        String randomSuitString = suits.get(randomSuitIndex);

        if (value == 11) {
            face = "A";
        } else if (value == 10) {
            face = randomFaceString;
        } else {
            face = String.valueOf(value);
        }
        suit = randomSuitString;

        return face + suit + ".png";
    }
}