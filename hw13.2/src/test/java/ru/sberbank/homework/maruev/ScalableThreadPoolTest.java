package ru.sberbank.homework.maruev;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Ivan.
 */
public class ScalableThreadPoolTest {
    private int min = 1;
    private int max = 6;
    private int nTasks = 150;
    private ScalableThreadPool scalableThreadPool = new ScalableThreadPool(min, max);


    @Test
    public void maxIncrementTest() throws InterruptedException {
        scalableThreadPool.start();

        for (int i = 0; i < nTasks; i++) {
            scalableThreadPool.execute(new TestTask());
        }

        TimeUnit.MILLISECONDS.sleep(1000);
        Assert.assertEquals(max, scalableThreadPool.threads.size());
    }

    @Test
    public void endIncrementTest() throws InterruptedException{
        scalableThreadPool.start();

        for (int i = 0; i < nTasks; i++) {
            scalableThreadPool.execute(new TestTask());
        }
        Assert.assertEquals(min, scalableThreadPool.threads.size());
    }

    @Test
    public void queueTest() throws InterruptedException{
        scalableThreadPool.start();

        for (int i = 0; i < nTasks; i++) {
            scalableThreadPool.execute(new TestTask());
        }

        TimeUnit.MILLISECONDS.sleep(5000);
        Assert.assertEquals(0, scalableThreadPool.queue.size());
    }

    class TestTask implements Runnable {
        @Override
        public void run() {
            System.out.println("say Hello");
        }
    }

}