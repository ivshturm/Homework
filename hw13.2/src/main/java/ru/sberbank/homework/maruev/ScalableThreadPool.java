package ru.sberbank.homework.maruev;

import ru.sberbank.homework.common.ThreadPool;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Ivan.
 */
public class ScalableThreadPool implements ThreadPool {
    private int max;
    private int min;
    public boolean isRunning = true;
    public List<Thread> threads;
    public Queue<Runnable> taskQueue;

    public ScalableThreadPool(int min, int max) {
        this.max = max;
        this.min = min;
        taskQueue = new ArrayDeque<>();
        threads = new LinkedList<>();

        for (int i = 0; i < min; i++) {
            threads.add(new PoolHelper());
        }
    }

    @Override
    public void start() {
        threads.forEach(e -> e.start());
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (taskQueue) {
            addNewTask(runnable);
            taskQueue.notifyAll();
        }
    }

    public boolean isEmpty(Queue<Runnable> queue) {
        boolean result = queue.isEmpty();

        if (result && threads.size() > min) {
            Thread thread = threads.get(threads.size() - 1);
            threads.remove(thread);
            thread.interrupt();
        }
        return result;
    }

    public void addNewTask(Runnable newTask) {
        taskQueue.add(newTask);

        if(taskQueue.size() > 0 && threads.size() < max) {
            Thread thread = new PoolHelper();
            threads.add(thread);
            thread.start();
        }
    }

    public void shoutdown() {
        isRunning = false;
    }


    private class PoolHelper extends Thread {
        @Override
        public void run() {
            Runnable runnable;

            while (isRunning) {
                synchronized (taskQueue) {
                    while (isEmpty(taskQueue)) {
                        try {
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    runnable = taskQueue.poll();
                }

                try {
                    runnable.run();
                } catch (RuntimeException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
