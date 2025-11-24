package cs.edu.bsu;

import java.io.*;
import java.util.*;

public class AccountManager {

    private static final String DIR_PATH = "UserAccounts";
    private static final String FILE_PATH = DIR_PATH + "/accounts.txt";
    private static final ArrayList<Account> accounts = new ArrayList<>();

    public static Account getAccount(String username) {
        for (Account acc : accounts) {
            if (acc.getUsername().equalsIgnoreCase(username)) {
                return acc;
            }
        }
        return null;
    }

    public static void addAccount(Account acc) {
        accounts.add(acc);
    }

    public static void updateBalance(String username, long newBalance) {
        Account acc = getAccount(username);
        if (acc != null) {
            acc.setUserBalance(newBalance);
        }
    }

    public static void saveAccounts() {
        try {
            File dir = new File(DIR_PATH);

            if (dir.exists() && dir.isFile()) {
                dir.delete();
            }

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(FILE_PATH);

            try (PrintWriter writer = new PrintWriter(file)) {
                for (Account acc : accounts) {
                    writer.println(acc);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadAccounts() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                accounts.add(Account.fromString(scanner.nextLine()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}