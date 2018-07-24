package com.optimus.common;

import com.optimus.utils.ConfigUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExector {

    private static final int MIN_CORE_SIZE = 10;
    private static ThreadPoolExecutor threadPoolExecutor;

    static {
        int coreSize = ConfigUtil.getIntegerConfig("thread.pool.coreSize");
        int maxSize = ConfigUtil.getIntegerConfig("thread.pool.maxSize");
        long keepAliveTime = ConfigUtil.getLongConfig("thread.pool.keepAliveTime");
        int maxQueueSize = ConfigUtil.getIntegerConfig("thread.pool.maxQueueSize");
        coreSize = Math.max(coreSize,MIN_CORE_SIZE);
        maxSize = Math.min(maxSize,Integer.MAX_VALUE);
        maxQueueSize = Math.min(maxQueueSize,Integer.MAX_VALUE);

        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(maxQueueSize);
        threadPoolExecutor = new ThreadPoolExecutor(coreSize,maxSize,keepAliveTime,TimeUnit.MILLISECONDS,blockingQueue);
    }

    public static void submit(Runnable task){
        threadPoolExecutor.execute(task);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return threadPoolExecutor.submit(task);
    }

    public static <T> Future<T> submit(Runnable task,T result) {
        return threadPoolExecutor.submit(task,result);
    }
}
