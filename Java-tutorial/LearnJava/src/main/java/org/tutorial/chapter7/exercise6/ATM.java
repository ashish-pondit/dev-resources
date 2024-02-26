package org.tutorial.chapter7.exercise6;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    private boolean loggedIn = false, running = false;
    private long balance = 5000000;

    private ArrayList<Account> accounts=new ArrayList<>();

    public void addAccount(Account account){
        accounts.add(account);
    }

    private Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNo().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    private Account authenticate() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your account no: ");
        String accountNumber = input.next();
        Account accountInstance = getAccount(accountNumber);
        if (accountInstance == null) {
            System.out.println("No such account exits with this account number");
            return null;
        }
        System.out.print("Enter your password: ");
        String password = input.next();

        try {
            if (accountInstance.checkPassword(password)) {
                this.loggedIn = true;
                return accountInstance;
            } else {
                return null;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        running = true;
        Account currentAccount = authenticate();
        int attempt = 1;
        while (currentAccount == null && attempt<5){
            System.out.println("You must provide a valid account information to proceed");
            System.out.println(5-attempt+" attempt left");
            currentAccount = authenticate();
            if(currentAccount != null){
                break;
            }
            attempt++;
        }

        if(currentAccount == null){
            System.out.println("Maximum attempt exceeded. Please try later");
            return;
        }
        Scanner input = new Scanner(System.in);

        while (running) {
            System.out.println("Main Menu\n1: Check Balance\n2: Withdraw\n3: Deposit\n4: Exit\nEnter choice: ");
            int option = input.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Hello, Mr. "+currentAccount.getName());
                    System.out.println("Your account no "+currentAccount.getAccountNo());
                    System.out.println("Your current account balance is "+currentAccount.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    long withdrawAmount = input.nextLong();
                    currentAccount.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    long depositAmount = input.nextLong();
                    currentAccount.deposit(depositAmount);
                    break;
                case 4:
                    System.out.println("Exiting ...");
                    System.out.println("Logging out ...");
                    running = false;
                    break;
                default:
                    System.out.println("No suitable option");
            }
        }
    }
}
