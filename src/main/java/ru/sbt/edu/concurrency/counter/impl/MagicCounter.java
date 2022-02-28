package ru.sbt.edu.concurrency.counter.impl;

import ru.sbt.edu.concurrency.counter.Counter;
import ru.sbt.edu.concurrency.locks.theory.ImprovedBakeryLock;


public class MagicCounter implements Counter {

    private int counter = 0;

    private final ImprovedBakeryLock improvedBakeryLock = new ImprovedBakeryLock();


    @Override
    public void increment() {
        try {
            improvedBakeryLock.lock();
            counter++;
        } finally {
            improvedBakeryLock.unlock();
        }

    }

    @Override
    public long getValue() {
        return counter;
    }
}
