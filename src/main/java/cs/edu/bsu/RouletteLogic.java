package cs.edu.bsu;

import java.util.Random;
import java.util.Set;

public class RouletteLogic {

    private Random randomGenerator;

    public RouletteLogic() {
        randomGenerator = new Random();
    }

    public enum BetType {
        NUMBER, RED, BLACK, EVEN, ODD
    }

    public static final Set<Integer> RED_NUMBERS = Set.of(
            2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36
    );

    public static final Set<Integer> BLACK_NUMBERS = Set.of(
            1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35
    );


    public static class RoundResult {
        public int rolledNumber;
        public String rolledColor;
        public BetType betType;
        public int chosenNumber;
        public int betAmount;
        public int netGain;

        public RoundResult(int rolledNumber, String rolledColor, BetType betType,
                           int chosenNumber, int betAmount, int netGain) {
            this.rolledNumber = rolledNumber;
            this.rolledColor = rolledColor;
            this.betType = betType;
            this.chosenNumber = chosenNumber;
            this.betAmount = betAmount;
            this.netGain = netGain;
        }

        public String getSummary() {
            if (rolledNumber == -1) {
                return "Invalid bet or not enough MAAD Coins.";
            }

            String resultMessage = "Ball landed on " + rolledNumber + " (" + rolledColor + "). ";
            if (netGain > 0) {
                resultMessage += "You won " + netGain + " MAAD Coins!";
            } else {
                resultMessage += "You lost " + betAmount + " MAAD Coins.";
            }
            return resultMessage;
        }
    }

    public RoundResult playRound(BetType betType, int chosenNumber, int betAmount) {
        if (betAmount <= 0 || betAmount > CoinBalance.getGameBalance()) {
            return new RoundResult(-1, "Invalid", betType, chosenNumber, betAmount, 0);
        }

        CoinBalance.gameBalance -= betAmount;
        int rolledNumber = spinWheel();
        String rolledColor = getColor(rolledNumber);
        boolean playerWon = checkIfPlayerWon(betType, chosenNumber, rolledNumber);
        int payoutMultiplier = getMultiplier(betType);

        int netGain = 0;
        if (playerWon) {
            int totalPayout = betAmount * (payoutMultiplier + 1);
            CoinBalance.gameBalance += totalPayout;
            netGain = totalPayout - betAmount;
        }

        return new RoundResult(rolledNumber, rolledColor, betType, chosenNumber, betAmount, netGain);
    }

    private int spinWheel() {
        return randomGenerator.nextInt(37);
    }

    private boolean checkIfPlayerWon(BetType betType, int chosenNumber, int rolledNumber) {
        return switch (betType) {
            case NUMBER -> chosenNumber == rolledNumber;
            case RED -> RED_NUMBERS.contains(rolledNumber);
            case BLACK -> BLACK_NUMBERS.contains(rolledNumber);
            case EVEN -> rolledNumber != 0 && rolledNumber % 2 == 0;
            case ODD -> rolledNumber % 2 == 1;
        };
    }

    private int getMultiplier(BetType betType) {
        return switch (betType) {
            case NUMBER -> 35;
            case RED, BLACK, EVEN, ODD -> 1;
        };
    }

    public static String getColor(int number) {
        if (number == 0) return "GREEN";
        return RED_NUMBERS.contains(number) ? "RED" : "BLACK";
    }
}
