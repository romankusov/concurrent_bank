package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private int balance;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(int amount) {
        lock.lock();
        try {
            if (balance < amount || amount < 0) {
                return false;
            }
            balance -= amount;
            return true;
        } finally {
            lock.unlock();
        }
    }

    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
