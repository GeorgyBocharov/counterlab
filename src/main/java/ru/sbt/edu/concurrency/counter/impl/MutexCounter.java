package ru.sbt.edu.concurrency.counter.impl;

import ru.sbt.edu.concurrency.counter.Counter;

public class MutexCounter implements Counter {

    private int counter = 0;

    @Override
    public void increment() {
        synchronized (this) {
            counter++;
        }
    }

    @Override
    public long getValue() {
        synchronized (this) {
            return counter;
        }
    }
}
