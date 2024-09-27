package ru.sbt.edu.concurrency.counter.impl;

import ru.sbt.edu.concurrency.counter.Counter;

public class SeqCounter implements Counter {
    private long value;
    @Override
    public void increment() {
        value++;
    }

    @Override
    public long getValue() {
        return value;
    }
}
