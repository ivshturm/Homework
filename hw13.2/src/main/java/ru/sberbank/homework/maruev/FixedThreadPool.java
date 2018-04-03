package ru.sberbank.homework.maruev;

import ru.sberbank.homework.common.ThreadPool;

import java.util.*;

/**
 * Created by Ivan.
 */
public class FixedThreadPool implements ThreadPool {

    private int number;
    public Deque<Runnable> queue;
    public List<Thread> threads;

    public FixedThreadPool(int number) {
        this.number = number;
        queue = new ArrayDeque<>();
        threads = new LinkedList<>();

        for (int i = 0; i < number; i++) {
            Thread thread = new PoolHelper();
            threads.add(thread);
        }
    }

    @Override
    public void start() {
        threads.forEach(e -> e.start());
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (queue) {
            queue.addFirst(runnable);
            queue.notifyAll();
        }
    }

    private class PoolHelper extends Thread {
        @Override
        public void run() {
            Runnable runnable;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    runnable = queue.pollFirst();
                }

                try {
                    runnable.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
