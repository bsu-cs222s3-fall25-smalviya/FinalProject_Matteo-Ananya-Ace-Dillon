package cs.edu.bsu;

import java.util.Random;

public final class WarLogic {
    private static final int MIN_RANK = 2;
    private static final int MAX_RANK = 14;
    private final Random random;

    public WarLogic() {
        this(new Random());
    }
    public WarLogic(Random random) {
        this.random = random;
    }

    public RoundResult playRound(int bet) {
        if (bet <= 0) throw new IllegalArgumentException("Bet must be a number :( ");

        int playerCard = drawCard();
        int dealerCard = drawCard();

        Outcome outcome;
        int payout;

        if(playerCard > dealerCard) {
            outcome = Outcome.PLAYER_WIN;
            payout = bet;
        } else if (playerCard < dealerCard) {
            outcome = Outcome.DEALER_WIN;
            payout = -bet;
        } else {
            outcome = Outcome.PUSH;
            payout = 0;
        }

        return new RoundResult(playerCard, dealerCard, outcome,payout);
    }

    private int drawCard() {
        return random.nextInt((MAX_RANK - MIN_RANK) + 1) + MIN_RANK;
    }

    public static String cardToString(int card) {
        return switch (card) {
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            case 14 -> "A";
            default -> String.valueOf(card);
        };
    }
    public enum Outcome {PLAYER_WIN, DEALER_WIN, PUSH}

    public static final class RoundResult {
        public final int playerCard;
        public final int dealerCard;
        public final Outcome outcome;
        public final int payout;

        public RoundResult(int playerCard, int dealerCard, Outcome outcome, int payout) {
            this.playerCard = playerCard;
            this.dealerCard = dealerCard;
            this.outcome = outcome;
            this.payout = payout;
        }

        public String summary(int bet ) {
            String player = cardToString(playerCard);
            String dealer = cardToString(dealerCard);
            String verdict = switch (outcome) {
                case PLAYER_WIN -> "You win!" + bet + " MAAD coins";
                case DEALER_WIN -> "Dealer wins, your wife is disappointed.\n" + bet + " coins lost to the ether";
                case PUSH -> "Push. That means you need to bet more!";
            };
            return "You: " + player + " vs Dealer: " + dealer + "-" + verdict;
        }
    }
}
//----------------
//RULES OF WAR
//------------
//There are 52 cards in total, which are split between the player and the dealer (computer).
//Both the player and the dealer will have 26 cards. Each round, both the player and dealer
//flip a card over, whichever cards value is highest will win that round.
//----------------
//CARD VALUES
//-----------
//2, 3, 4, 5, 6, 7, 8, 9, 10
//Jack - 11
//Queen - 12
//King - 13
//Ace - 14
//----------------
//
//




//    String[] suit = {
//            "♠", // Spade
//            "♥", // Heart
//            "♦", // Diamond
//            "♣"  // Club
//    };
//    // list of court cards
//    String[] court = {
//            "Jack", // 11 value
//            "Queen", // 12 value
//            "King", // 13 value
//            "Ace" // 14 value
//    };
//
//    Random random = new Random();
//
//    //random cards
//    int lowest = 2;
//    int highest = 14;
//
//    int playerShuffle = random.nextInt(highest - lowest + 1) + lowest;
//    int dealerShuffle = random.nextInt(highest - lowest + 1) + lowest;
//*/
//}
//
