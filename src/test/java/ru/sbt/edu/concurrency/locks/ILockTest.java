package ru.sbt.edu.concurrency.locks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.sbt.edu.concurrency.counter.Counter;
import ru.sbt.edu.concurrency.counter.impl.ConcurrentCounter;
import ru.sbt.edu.concurrency.counter.impl.ILockCounter;
import ru.sbt.edu.concurrency.counter.impl.LockCounter;
import ru.sbt.edu.concurrency.counter.impl.MagicCounter;
import ru.sbt.edu.concurrency.counter.impl.MutexCounter;
import ru.sbt.edu.concurrency.counter.impl.SeqCounter;
import ru.sbt.edu.concurrency.locks.theory.LockOne;
import ru.sbt.edu.concurrency.locks.theory.LockTwo;
import ru.sbt.edu.concurrency.locks.theory.LockZero;
import ru.sbt.edu.concurrency.locks.theory.PetersonLock;
import ru.sbt.edu.concurrency.util.ThreadID;
import ru.sbt.edu.concurrency.util.TwoThreadIds;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class ILockTest {

    @BeforeEach
    public void resetThreadIds() {
        ThreadID.reset();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 100, 1000})
    public void testZeroLock(int iterations) {
        testLock(new LockZero(), iterations);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 100, 1000})
    public void testLockOne(int iterations) {
        testLock(new LockOne(), iterations);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 100, 1000})
    public void testLockTwo(int iterations) {
        testLock(new LockTwo(), iterations);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 100, 1000})
    public void testPetersonLock(int iterations) {
        testLock(new PetersonLock(), iterations);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 100, 1000})
    public void testMutexCounter(int iterations) {
        testCounter(new MutexCounter(), iterations, 5);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 100, 1000})
    public void testLockCounter(int iterations) {
        testCounter(new LockCounter(), iterations, 5);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 100, 1000})
    public void  testConcurrentLock(int iterations) {
        testCounter(new ConcurrentCounter(), iterations, 5);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 100, 1000})
    public void  testMagicLock(int iterations) {
        testCounter(new MagicCounter(), iterations, 10);
    }

    @Test
    public void testNaiveCounter() {
        Counter counter = new SeqCounter();
        testCounter(counter, 1000, 1);
    }
    
    private void testLock(ILock lock, int iterations) {
        Counter counter = new ILockCounter(lock);
        testCounter(counter, iterations, 2);
    }

    private void testCounter(Counter counter, int iterations, int threadNumber) {
        Runnable increment = () -> {
            System.out.println(TwoThreadIds.me());
            for (int i = 0; i < iterations; i++) {
                counter.increment();
            }
        };

        List<Thread> threads = new ArrayList<>(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            Thread thread = new Thread(increment);
            threads.add(thread);
            thread.start();;
        }

        try {
            for (Thread thread: threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long count = counter.getValue();
        System.out.println(count);
        Assertions.assertEquals(((long) iterations) * threadNumber, count, "Oops! Unexpected Behaviour!");
    }
}