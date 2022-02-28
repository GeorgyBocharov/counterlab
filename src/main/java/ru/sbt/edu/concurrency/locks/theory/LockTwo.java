package ru.sbt.edu.concurrency.locks.theory;

import ru.sbt.edu.concurrency.locks.ILock;
import ru.sbt.edu.concurrency.util.TwoThreadIds;

/**
 * Данный лок обладает свойствами:
 *
 *  Mutual exclusion - в критическую секцию заходит только один процесс
 *  Starvation free - оба процесса заходят в критическую секцию одинаковое количество раз (при бесконечной работе)
 *
 *  Для данного лок имеет значительный недостаток: если один поток заканчиает свою работу, то второй попадает в
 *  бесконечный цикл лжидания, так как больше никто не будет менять поле victim. Так же этот недостаток проявляется,
 *  если один поток стартует значительного раньше (позже) воторого.
 *
 *  Если убрать ключевое слово volatile перед victim, лок будет работать некорректно, так как 1й поток не будет обязан
 *  видеть изменения вносимые другим потоком: JIT компилятор после нескольких раундов оптимизации заменит значение
 *  переменной victim на константу в кажом потоке, а потом и вовсе удалит ее, оставив в функции lock() код while(true);
 *
 *  Из-за свойства volatile Java гарантирует, что запись в переменную happens before считывания из нее. Тут уже JIT
 *  компилятор не будет заменять victim на константу.
 *
 *  По тем же самым причинам надо ставить volatile перед массивом flag в LockOne
 */
public class LockTwo implements ILock {
    private volatile int victim;

    @Override
    public void lock() {
        int me = TwoThreadIds.me();
        victim = me;
        while (victim == me) {}
    }


    @Override
    public void unlock() {
    }
}
