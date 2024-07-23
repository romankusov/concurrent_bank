package org.example;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentBank {
    private final List<BankAccount> allAccounts = new CopyOnWriteArrayList<>();
    ReentrantLock lock = new ReentrantLock();

    public BankAccount createAccount(int initialBalance) {
        BankAccount newAccount = new BankAccount(initialBalance);
        allAccounts.add(newAccount);
        return newAccount;
    }
    public void transfer(BankAccount fromAcc, BankAccount toAcc, int amount) {
        lock.lock();
        try {
            if (fromAcc.withdraw(amount)) {
                toAcc.deposit(amount);
            }
        } finally {
            lock.unlock();
        }
    }

    public int getTotalBalance() {
        return allAccounts.stream().mapToInt(BankAccount::getBalance).sum();
    }
}
