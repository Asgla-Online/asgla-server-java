package com.asgla.requests.threadfactory;

import java.util.concurrent.ThreadFactory;

public class RequestThreadFactory implements ThreadFactory {

    private int counter = 1;

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "RequestHandler-" + counter++);
    }

}