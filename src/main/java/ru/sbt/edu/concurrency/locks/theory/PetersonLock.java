package ru.sbt.edu.concurrency.locks.theory;

import ru.sbt.edu.concurrency.locks.ILock;
import ru.sbt.edu.concurrency.util.TwoThreadIds;

/**
 * Данные лок удовлетворяет свойствам:
 * - Mutual exclusion, так как в критическую секцию не могут попасть 2 потока одновременно
 * - Starvation free, так как после выхода из критической секции, в нее сразу заходит ожиающий поток
 * - Deadlock free, так как всегда будет выполняться код в критической секции.
 */
public class PetersonLock implements ILock {

    private volatile boolean[] flag = new boolean[2];
    private volatile int victim;

    @Override
    public void lock() {
        int me = TwoThreadIds.me();
        int other = 1 - me;
        flag[me] = true;
        victim = me;
        while (flag[other] && victim == me) {
        }
    }


    @Override
    public void unlock() {
        flag[TwoThreadIds.me()] = false;
    }

}
