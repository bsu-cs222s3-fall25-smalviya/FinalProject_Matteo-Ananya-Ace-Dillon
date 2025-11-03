package cs.edu.bsu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HorseRaceLogic {

    private static final int NUM_HORSES = 5;
    private static final double FINISH_DISTANCE = 600.0;

    private static final Random rand = new Random();

    static int currentBet = 0;
    static int chosenHorseIndex = -1;

    static double[] positions = new double[NUM_HORSES];
    static boolean[] finished = new boolean[NUM_HORSES];
    static List<Integer> finishedOrder = new ArrayList<>();

    static int coinsWon = 0;
    static int playerPlace = -1;

    public static int getNumHorses() {
        return NUM_HORSES;
    }

    public static double getFinishDistance() {
        return FINISH_DISTANCE;
    }

    public static void setBet(int bet) {
        currentBet = bet;
    }

    public static void setChosenHorse(int index) {
        chosenHorseIndex = index;
    }

    public static void resetRaceState() {
        Arrays.fill(positions, 0);
        Arrays.fill(finished, false);
        finishedOrder.clear();
        coinsWon = 0;
        playerPlace = -1;
    }

    public static boolean stepRace() {
        boolean raceJustFinished = false;

        for (int i = 0; i < NUM_HORSES; i++) {
            if (finished[i]) {
                continue;
            }

            double delta = 2 + rand.nextDouble() * 6;
            positions[i] += delta;

            if (positions[i] >= FINISH_DISTANCE) {
                positions[i] = FINISH_DISTANCE;
                finished[i] = true;
                finishedOrder.add(i);

                if (finishedOrder.size() == NUM_HORSES) {
                    raceJustFinished = true;
                    break;
                }
            }
        }

        if (raceJustFinished) {
            playerPlace = finishedOrder.indexOf(chosenHorseIndex) + 1;
            applyPayout();
        }

        return raceJustFinished;
    }

    private static void applyPayout() {
        if (currentBet <= 0) return;

        if (playerPlace >= 1 && playerPlace <= 3) {
            double multiplier;
            switch (playerPlace) {
                case 1 -> multiplier = 3.0;
                case 2 -> multiplier = 2.0;
                default -> multiplier = 1.5;
            }
            coinsWon = (int) Math.round(currentBet * multiplier);
            // bet already removed earlier, so we just add winnings
            CoinBalance.balance += coinsWon;
        } else {
            coinsWon = 0;
        }
    }

    public static long getUpdatedBalance() {
        return CoinBalance.getBalance();
    }

    public static double[] getPositions() {
        return Arrays.copyOf(positions, positions.length);
    }

    public static String finishOrderString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < finishedOrder.size(); i++) {
            int horseNum = finishedOrder.get(i) + 1;
            sb.append(i + 1)
                    .append(placeWithSuffix(i + 1))
                    .append(": Horse ")
                    .append(horseNum)
                    .append("\n");
        }
        return sb.toString();
    }

    public static String resultMessage() {
        if (playerPlace == -1) {
            return "Race finished.";
        }

        String placeText = placeWithSuffix(playerPlace);

        if (playerPlace >= 1 && playerPlace <= 3) {
            return "Your horse finished " + placeText +
                    "! You won " + coinsWon + " MAAD Coins!";
        } else {
            return "Your horse finished " + placeText +
                    ". You lost " + currentBet + " MAAD Coins.";
        }
    }

    private static String placeWithSuffix(int place) {
        return switch (place) {
            case 1 -> "1st";
            case 2 -> "2nd";
            case 3 -> "3rd";
            default -> place + "th";
        };
    }
}
