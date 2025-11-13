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

    public static void set() {
        if (currentBet <= CoinBalance.gameBalance) {
            CoinBalance.gameBalance -= currentBet;
            System.out.println("set " + currentBet);
            System.out.println("game balance " + CoinBalance.gameBalance);
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
        System.out.println("setBet " + currentBet);
    }

    public static void setDealerCards() {
        randomIndex = random.nextInt(values.size());
        randomCard = values.get(randomIndex);
        dealerHand.add(randomCard);

        randomIndex = random.nextInt(values.size());
        randomCard = values.get(randomIndex);
        dealersSecondCard = randomCard;

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
    }

    public static void dealerHit() {
        while (totalValueCalculatorDealer() < 17) {
            int randomIndex = random.nextInt(values.size());
            int randomCard = values.get(randomIndex);
            dealerHand.add(randomCard);
            totalValueCalculatorDealer();
        }

        pushOutcome();
        win();
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

    public static void payoutCalculator() {
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
    }

    public static void doubleDownCalculator() {
        currentBet *= 2;
        CoinBalance.gameBalance -= currentBet;
        playerHit();
    }
}