package ru.sberbank.homework.maruev;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan.
 */
public class ScalableThreadPoolTest {
    private int min = 1;
    private int max = 5;
    private int nTasks = 100;
    private ScalableThreadPool scalableThreadPool = new ScalableThreadPool(min, max);

    @Test
    public void endIncrementTest() throws InterruptedException {


        for (int i = 0; i < nTasks; i++) {
            scalableThreadPool.execute(new TestTask());
        }
        scalableThreadPool.start();
        Assert.assertEquals(min, scalableThreadPool.threads.size());
    }

    @Test
    public void queueTest() throws InterruptedException {
        scalableThreadPool.start();

        for (int i = 0; i < nTasks; i++) {
            scalableThreadPool.execute(new TestTask());
        }

        TimeUnit.MILLISECONDS.sleep(3000);
        Assert.assertEquals(0, scalableThreadPool.taskQueue.size());
    }

    class TestTask implements Runnable {
        @Override
        public void run() {
            System.out.println("say Hello");
        }
    }

}