package ru.sbt.edu.concurrency.locks.theory;

import ru.sbt.edu.concurrency.locks.ILock;
import ru.sbt.edu.concurrency.util.ThreadID;


public class ImprovedBakeryLock implements ILock {
    private volatile int[] label;
    private volatile boolean[] flag;
    private volatile long status = 0;
    private final int n;
    private long container = 0;

    public ImprovedBakeryLock(int n) {
        flag = new boolean[n];
        label = new int[n];
        for (int i = 0; i < n; i++) {
            flag[i] = false;
            label[i] = 0;
        }
        this.n = n;
    }

    public void lock() {
        int i = ThreadID.get();
        flag[i] = true;
        status = System.currentTimeMillis();
        int maxPlusOne = getMaxPlusOne();
        label[i] = maxPlusOne;
        status = System.currentTimeMillis();
        long readableStatus = 0;
        for (int k = 0; k < n; k++) {
            boolean flagK;
            int labelK;
            do {
                readableStatus = status;
                flagK = flag[k];
                long flagStatus = status;
                labelK = label[k];
                readableStatus += flagStatus;
            }
            while ((k != i) && flagK && ((labelK < maxPlusOne) || ((labelK == maxPlusOne) && k < i)));
        }
        container = readableStatus;
    }

    public void unlock() {
        flag[ThreadID.get()] = false;
        status = System.currentTimeMillis();
    }

    public long getContainer() {
        return container;
    }

    private int getMaxPlusOne() {
        int max = Integer.MIN_VALUE;
        long currentStatus = 0;
        for (int i = 0; i < n; i++) {
            currentStatus = status;
            int nextLabel = label[i];
            if (nextLabel > max) {
                max = nextLabel;
            }
        }
        container = currentStatus;
        return max + 1;
    }
}

