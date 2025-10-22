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
*/

import java.util.Random;

public class SlotsLogic {
    Random rand = new Random();

    // list of symbols for slots
    String[] symbols = {"Cherry", "Lemon", "Orange", "Bell", "Diamond", "Lucky Seven"};
    String randomSymbol = symbols[rand.nextInt(symbols.length)];

    // action
    System.out.println("\n");
    System.out.println(randomSymbol);



}
