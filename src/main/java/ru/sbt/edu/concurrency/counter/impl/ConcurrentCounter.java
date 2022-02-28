package ru.sbt.edu.concurrency.counter.impl;

import ru.sbt.edu.concurrency.counter.Counter;

import java.util.concurrent.Semaphore;

/**
 * Данный счетчик удовлетворяет всем свойствам:
 *
 * Mutual exclusion - в критическую секцию попадает только один поток, так как semaphore.acquire();  пропускает
 * только один поток дальше, а следующий поток может войти только после вызова  semaphore.release();, который будет
 * выполнен всегда благодаря ключевому слову finally.
 *
 * Fairness обеспечен за счет аргумента конструктора и внутренней очереди в fair реализации семафора
 *
 * Deadlock free - в любом случае будет вызван semaphore.release();, поэтому хотя бы один процесс всегда будет работать
 */
public class ConcurrentCounter implements Counter {

    private int counter = 0;
    private final Semaphore semaphore = new Semaphore(1, true);

    @Override
    public void increment() {
        try {
            semaphore.acquire();
            counter++;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    @Override
    public long getValue() {
        return counter;
    }
}
