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
    public static void main(String[] args) {
        Random rand = new Random();

        // player bet amounts
        int[] moneyAmount = {1, 5, 10, 25, 50};

        // list of symbols for slots
        String[] symbols1 = {"Cherry", "Lemon", "Orange", "Bell", "Diamond", "Lucky Seven"};
        String[] symbols2 = {"Cherry", "Lemon", "Orange", "Bell", "Diamond", "Lucky Seven"};
        String[] symbols3 = {"Cherry", "Lemon", "Orange", "Bell", "Diamond", "Lucky Seven"};
        String randomSymbol1 = symbols1[rand.nextInt(symbols1.length)];
        String randomSymbol2 = symbols2[rand.nextInt(symbols2.length)];
        String randomSymbol3 = symbols3[rand.nextInt(symbols3.length)];


        // action
        System.out.println("\n");
        System.out.println(randomSymbol1);
        System.out.println(randomSymbol2);
        System.out.println(randomSymbol3);

        // logic
        //if (randomSymbol1 = "Cherry") {
        //    int moneyAmount *= 1;
        //}


    }
}

