package ru.sbt.edu.concurrency.locks.theory;

import ru.sbt.edu.concurrency.locks.ILock;
import ru.sbt.edu.concurrency.util.TwoThreadIds;

/**
 *  Данный лок не работает, так как он не блокирует поток в методе lock()
 */
public class LockZero implements ILock {
    private final boolean[] flag = new boolean[2];

    @Override
    public void lock() {
        int me = TwoThreadIds.me();
        System.out.printf("Thread %s, me = %d%n", Thread.currentThread(), me);
        flag[me] = true;
    }


    @Override
    public void unlock() {
        flag[TwoThreadIds.me()] = false;
    }
}