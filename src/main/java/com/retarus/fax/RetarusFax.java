package com.retarus.fax;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class RetarusFax {
    /**
     * Private constructor to prevent instantiation.
     */
    private RetarusFax() {
    }

    private static ExecutorService executorService;

    public static synchronized ExecutorService getExecutorService() {
        synchronized (RetarusFax.class) {
            if (executorService == null) {
                executorService = newCachedThreadPool();
            }
        }
        return executorService;
    }

    public static synchronized void setExecutorService(ExecutorService executorService) {
        synchronized (RetarusFax.class) {
            RetarusFax.executorService = executorService;
        }
    }
}
