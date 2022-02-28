package ru.sbt.edu.concurrency.counter.impl;

import ru.sbt.edu.concurrency.counter.Counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockCounter implements Counter {

    private int counter = 0;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();



    @Override
    public void increment() {
        try {
            writeLock.lock();
            counter++;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public long getValue() {
        try {
            readLock.lock();
            return counter;
        } finally {
            readLock.unlock();
        }
    }
}
