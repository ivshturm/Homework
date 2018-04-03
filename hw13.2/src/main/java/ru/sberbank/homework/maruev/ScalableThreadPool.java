package ru.sberbank.homework.maruev;

import ru.sberbank.homework.common.ThreadPool;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan.
 */
public class ScalableThreadPool implements ThreadPool {
    private int max;
    private int min;
    public List<Thread> threads;
    public Queue<Runnable> queue;
    private Controller controller;


    public ScalableThreadPool(int min, int max) {
        this.max = max;
        this.min = min;
        queue = new ArrayDeque<>();
        threads = new LinkedList<>();

        for (int i = 0; i < min; i++) {
            threads.add(new PoolHelper());
        }

        controller = new Controller();
    }

    @Override
    public void start() {
        threads.forEach(e -> e.start());
        controller.start();
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (queue) {
            queue.add(runnable);
            queue.notifyAll();
        }
    }

    private class Controller extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    if (queue.size() > min && threads.size() != max) {
                            Thread thread = new PoolHelper();
                            threads.add(thread);
                            thread.start();

                    } else if (queue.size() == 0 && threads.size() > min) {
                            Thread thread = threads.get(threads.size()-1);
                            threads.remove(thread);
                            thread.interrupt();
                    }
                }
            }
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
                            Thread.currentThread().interrupt();
                        }
                    }
                    runnable = queue.poll();
                }

                try {
                    runnable.run();
                    System.out.println(threads.size());
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (RuntimeException | InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
