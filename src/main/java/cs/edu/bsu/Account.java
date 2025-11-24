package cs.edu.bsu;

public class Account {
    private final String username;
    private long userBalance;

    public Account(String username, long userBalance) {
        this.username = username;
        this.userBalance = userBalance;
    }

    public String getUsername() {
        return username;
    }

    public long getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(long balance) {
        this.userBalance = balance;
    }

    @Override
    public String toString() {
        return username + "," + userBalance;
    }

    public static Account fromString(String line) {
        String[] parts = line.split(",");
        return new Account(parts[0], Integer.parseInt(parts[1]));
    }
}