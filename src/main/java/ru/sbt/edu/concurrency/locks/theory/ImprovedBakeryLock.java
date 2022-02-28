package ru.sbt.edu.concurrency.locks.theory;

import ru.sbt.edu.concurrency.locks.ILock;
import ru.sbt.edu.concurrency.util.ThreadID;

import static java.lang.Long.MIN_VALUE;

/**
 * Простите, что отправляю это...
 * Не успел придумать ничего лучше
 */
public class ImprovedBakeryLock implements ILock {
    private volatile long[] label;
    private volatile boolean[] flag;

    public ImprovedBakeryLock() {
        int n = 1000;
        flag = new boolean[n];
        label = new long[n];
        for (int i = 0; i < n; i++) {
            flag[i] = false;
            label[i] = 0;
        }
    }

    public void lock() {
        int i = ThreadID.get();
        int currentLength = flag.length;
        label[i] = getMaxPlusOne(currentLength);
        for (int k = 0; k < currentLength; k++) {
            while ((k != i) && flag[k] && ((label[k] < label[i]) || ((label[k] == label[i]) && k < i))) {
            }
        }
    }

    public void unlock() {
        flag[ThreadID.get()] = false;
    }

    private long getMaxPlusOne(int currentLength) {
        long max = MIN_VALUE;
        for (int i = 0; i < currentLength; i++) {
            if (label[i] > max) {
                max = label[i];
            }
        }
        return max + 1;
    }
}

