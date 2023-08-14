package task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Drop {
    final Lock lock = new ReentrantLock();
    final Condition notEmpty = lock.newCondition();
    final Condition notFull = lock.newCondition();
    private int[] buffer;
    private int count, putIndex, takeIndex;

    public Drop(int bufferSize) {
        buffer = new int[bufferSize];
        count = 0;
        putIndex = 0;
        takeIndex = 0;
    }

    public int take() throws InterruptedException{
        lock.lock();
        try {
            while (count == 0) {
                System.out.println("empty");
                notEmpty.await();
            }

            int item = buffer[takeIndex];
            if(++takeIndex == buffer.length){
                takeIndex = 0;
            }
            --count;

            notFull.signal();
            return item;

        } finally {
            lock.unlock();
        }
    }

    public void put(int item) throws InterruptedException {
        lock.lock();
        try {
            while (count == buffer.length) {
                System.out.println("full");
                notFull.await();
            }

            buffer[putIndex] = item;
            if(++putIndex == buffer.length){
                putIndex = 0;
            }
            ++count;

            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}
