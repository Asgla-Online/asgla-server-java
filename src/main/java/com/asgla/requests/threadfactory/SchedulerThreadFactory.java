package com.asgla.requests.threadfactory;

import java.util.concurrent.ThreadFactory;

public class SchedulerThreadFactory implements ThreadFactory {

    private int count = 1;

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "Scheduler-" + count++);
    }

}
