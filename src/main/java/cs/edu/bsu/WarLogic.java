package cs.edu.bsu;

import java.util.Random;

public final class WarLogic {
    private static final int MIN_RANK = 2;   // 2..14 (Aces high)
    private static final int MAX_RANK = 14;
    private final Random random;

    public WarLogic() { this(new Random()); }
    public WarLogic(Random random) { this.random = random; }

    public enum Outcome { PLAYER_WIN, DEALER_WIN, PUSH }

    public static String cardToString(int rank) {
        return switch (rank) {
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            case 14 -> "Ace";
            default -> String.valueOf(rank);
        };
    }

    public RoundResult playRound(int bet) {
        int player = draw();
        int dealer = draw();

        Outcome o = (player > dealer) ? Outcome.PLAYER_WIN
                : (player < dealer) ? Outcome.DEALER_WIN
                : Outcome.PUSH;

        return new RoundResult(player, dealer, o);
    }

    private int draw() {
        return random.nextInt(MAX_RANK - MIN_RANK + 1) + MIN_RANK;
    }

    public static final class RoundResult {
        public final int playerCard;
        public final int dealerCard;
        public final Outcome outcome;

        public RoundResult(int p, int d, Outcome o) {
            this.playerCard = p;
            this.dealerCard = d;
            this.outcome = o;
        }

        public String summary(int bet) {
            String verdict = switch (outcome) {
                case PLAYER_WIN -> "You win " + bet + " MAAD Coins!";
                case DEALER_WIN -> "Dealer wins. You lose " + bet + ".";
                case PUSH -> "Push. No one wins.";
            };
            return verdict;
        }
    }
}