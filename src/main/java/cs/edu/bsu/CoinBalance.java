package cs.edu.bsu;

public class CoinBalance {
    public static long balance = 1000;
    public static long gameBalance = 0;

    public static long getBalance() {
        return balance;
    }

    public static void setBalance(long amount) {
        balance = amount;
    }

    public static long getGameBalance() {
        return gameBalance;
    }

    public static void setGameBalance(long amount) {
        gameBalance = amount;
    }
}

