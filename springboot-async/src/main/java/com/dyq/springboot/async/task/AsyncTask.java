package com.dyq.springboot.async.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步
 */
@Component
public class AsyncTask extends AbstractTask {
    @Async
    public void doTaskOne() throws Exception {
        super.doTaskOne();
    }

    @Async
    public void doTaskTwo() throws Exception {
        super.doTaskTwo();
    }

    @Async
    public void doTaskThree() throws Exception {
        super.doTaskThree();
    }
}