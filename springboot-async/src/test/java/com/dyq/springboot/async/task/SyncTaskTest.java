package com.dyq.springboot.async.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SyncTaskTest {
    @Autowired
    private SyncTask task;

    @Autowired
    private AsyncTask asyncTask;

    @Autowired
    private AsyncCallBackTask asyncCallBackTask;


    /**
     * 同步
     * @throws Exception
     */
    @Test
    public void testSyncTasks() throws Exception {
        task.doTaskOne();
        task.doTaskTwo();
        task.doTaskThree();
    }

    /**
     * 异步
     * @throws Exception
     */
    @Test
    public void testAsyncTasks() throws Exception {
        asyncTask.doTaskOne();
        asyncTask.doTaskTwo();
        asyncTask.doTaskThree();
    }

    /**
     * 异步回调
     * @throws Exception
     */
    @Test
    public void testAsyncCallBackTasks() throws Exception {
        long start = System.currentTimeMillis();
        Future<String> task1 = asyncCallBackTask.doTaskOneCallback();
        Future<String> task2 = asyncCallBackTask.doTaskTwoCallback();
        Future<String> task3 = asyncCallBackTask.doTaskThreeCallback();

        // 三个任务都调用完成，退出循环等待
        while (!task1.isDone() || !task2.isDone() || !task3.isDone()) {
            Thread.sleep(1000);
        }

        long end = System.currentTimeMillis();
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }


    @Autowired
    private AsyncExecutorTask asyncExecutor;

    /**
     * 线程池
     * @throws Exception
     */
    @Test
    public void testAsyncExecutorTask() throws Exception {
        asyncExecutor.doTaskOneCallback();
        asyncExecutor.doTaskTwoCallback();
        asyncExecutor.doTaskThreeCallback();

        Thread.sleep(30 * 1000L);
    }

}