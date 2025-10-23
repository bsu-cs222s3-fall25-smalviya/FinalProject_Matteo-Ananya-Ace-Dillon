package cs.edu.bsu;

/*
---------------
RULES OF SLOTS
--------------
The player bets an amount of money per spin. When the player spins, they have to match symbols to win.
Two matches gives the players money back. All three symbols gives out more money. There will be multiple
different symbols, with symbol being worth different amount of money, if the play gets all three to match.

🍒 Cherry – Lowest payout
🍋 Lemon – Second-lowest payout.
🍊 Orange - Medium-payout.
🔔 Bell – High-payout.
💎 Diamond – Very high payout.
7️⃣ Lucky Seven – Biggest payout.


50 coins max
25
10
5
1 coin min
*/

import java.util.Random;

public class SlotsLogic {
    public String spin() {
        Random rand = new Random();

        // player bet amounts
        int[] moneyAmounts = {1, 5, 10, 25, 50};
        int playerAccount = 500;

        // list of symbols for slots
        String[] symbols = {"🍒", "🍋", "🍊", "🔔", "💎", "7"};
        String randomSymbol1 = symbols[rand.nextInt(symbols.length)];
        String randomSymbol2 = symbols[rand.nextInt(symbols.length)];
        String randomSymbol3 = symbols[rand.nextInt(symbols.length)];

        // action
        //System.out.println("\n");
        //System.out.println(randomSymbol1);
        //System.out.println(randomSymbol2);
        //System.out.println(randomSymbol3);

        // logic
        if (randomSymbol1.equals(randomSymbol2) && randomSymbol2.equals(randomSymbol3)) {
            // all three match
            return randomSymbol1 + " | " + randomSymbol2 + "|" + randomSymbol3 + " | All three match";
        } else if (randomSymbol1.equals(randomSymbol2) || randomSymbol2.equals(randomSymbol3) || randomSymbol1.equals(randomSymbol3)) {
            // two match
            return randomSymbol1 + " | " + randomSymbol2 + " | " + randomSymbol3 + " | Two match";
        } else {
            // no match
            return randomSymbol1 + " | " + randomSymbol2 + " | " + randomSymbol3 + " | None match";
        }
    }
}

