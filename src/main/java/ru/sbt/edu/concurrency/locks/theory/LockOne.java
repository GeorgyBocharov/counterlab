package ru.sbt.edu.concurrency.locks.theory;

import ru.sbt.edu.concurrency.locks.ILock;
import ru.sbt.edu.concurrency.util.TwoThreadIds;

/**
 * Данный лок обладает следующими свойствами:
 * Mutual Exclusion - в критическую секцию заходит только один поток
 * Starvation free - оба процесса заходят в критическую секцию одинаковое количество раз
 *
 * У данного лока возможна ситуация дедлока, когда оба потока одновременно вызывают flag[me] = true, и потом будут
 * заблокированы в цикле while
 */
public class LockOne implements ILock {
    private volatile boolean[] flag = new boolean[2];

    @Override
    public void lock() {
        int me = TwoThreadIds.me();
        int other = 1 - me;
        flag[me] = true;
        while (flag[other]) {}
    }


    @Override
    public void unlock() {
        flag[TwoThreadIds.me()] = false;
    }
}