package org.tutorial.chapter7.exercise6;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class Account {
    public static final long minimumBalance = 500;
    private String name, hashedPassword;
    private final String accountNo;
    private long balance = 0;
    private final Random random = new Random();

    public Account(String name, String password, long balance) {
        this.name = name;
        this.hashedPassword = getHash(password);
        this.accountNo = generateNewAccountNumber();
        this.balance = balance;
        this.getAccountInformation();
    }

    public Account(String name, String accountNo, String password) {
        this.name = name;
        this.hashedPassword = getHash(password);
        this.accountNo = accountNo;
        this.getAccountInformation();
    }

    public Account(String name, String accountNo, String password, long balance) {
        this.name = name;
        this.hashedPassword = getHash(password);
        this.accountNo = accountNo;
        this.balance = balance;
        this.getAccountInformation();
    }

    public static String getHash(String message) {
        MessageDigest messageDigest;
        String hashedString;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hashByte = messageDigest.digest(message.getBytes(StandardCharsets.UTF_8));
        hashedString = Base64.getEncoder().encodeToString(hashByte);
        return hashedString;
    }

    private String generateNewAccountNumber() {
        String accountPrefix = "107";
        StringBuilder newAccount = new StringBuilder(accountPrefix);
        int min = 48, max = 57;
        int accountNoLength = 12;
        for (int i = newAccount.length(); i < accountNoLength; i++) {
            int randomDigit = random.nextInt(max - min + 1) + min;
            newAccount.append((char) randomDigit);
        }

        return newAccount.toString();
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNo(){
        return this.accountNo;
    }

    public void setHashedPassword(String password) throws NoSuchAlgorithmException {
        this.hashedPassword = getHash(password);
    }

    public boolean checkPassword(String password) throws NoSuchAlgorithmException {
        return this.hashedPassword.equals(getHash(password));
    }

    public void showBalance(){
        System.out.println("Your current account balance is "+this.balance+"tk");
    }
    public void withdraw(long amount){
        if(this.balance < amount || this.balance-amount < minimumBalance){
            System.out.println("Your account do not have sufficient balance to withdraw");
        }else {
            this.balance -=amount;
            System.out.println("You've withdrew "+amount+" tk from the account "
                    +this.accountNo+"\n Your new account balance is "+this.balance);
        }
    }

    public void deposit(long amount){
        this.balance += amount;
        System.out.println("You've deposited "+amount+" tk into the account "
                +this.accountNo+"\n Your new account balance is "+this.balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", balance=" + balance +
                '}';
    }

    public void getAccountInformation(){
        System.out.println("New account information");
        System.out.println(this);
    }
}
