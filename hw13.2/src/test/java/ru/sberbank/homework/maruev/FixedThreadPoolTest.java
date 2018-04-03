package ru.sberbank.homework.maruev;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan.
 */
public class FixedThreadPoolTest {

    private int nTreads = 3;
    private int nTasks = 4;
    private FixedThreadPool threadPool = new FixedThreadPool(nTreads);


    @Test
    public void queueTest () throws InterruptedException{
        threadPool.start();

        for (int i = 0; i < nTasks; i++) {
            threadPool.execute(new TestTask());
        }

        TimeUnit.MILLISECONDS.sleep(1000);
        Assert.assertEquals(0, threadPool.queue.size());
    }

    @Test
    public void threadsTest() throws InterruptedException{
        threadPool.start();

        for (int i = 0; i < nTasks; i++) {
            threadPool.execute(new TestTask());
        }

        TimeUnit.MILLISECONDS.sleep(1000);
        Assert.assertEquals(nTreads, threadPool.threads.size());
    }

    class TestTask implements Runnable {
        @Override
        public void run() {
            System.out.println("say Hello");
        }
    }
}