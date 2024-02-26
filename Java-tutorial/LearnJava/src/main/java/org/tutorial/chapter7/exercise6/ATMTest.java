package org.tutorial.chapter7.exercise6;

public class ATMTest {
    public static void main(String[] args){
        Account account1 = new Account("Ashish Pondit", "123456", 500);
        Account account2 = new Account("Linkon Sharma", "11223344", 3000);

        ATM testATM = new ATM();
        testATM.addAccount(account1);
        testATM.addAccount(account2);

        testATM.start();
    }
}
