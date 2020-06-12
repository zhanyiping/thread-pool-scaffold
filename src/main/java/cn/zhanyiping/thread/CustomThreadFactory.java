package cn.zhanyiping.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建线程的工程类
 * 构造线程的名称，并且在异常时进行打印
 * create by zhanyiping
 */
@Slf4j
public class CustomThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private  String threadNamePrefix;

    CustomThreadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix ;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, threadNamePrefix + "_" + threadNumber.getAndIncrement());
        thread.setUncaughtExceptionHandler((t , e) -> log.error("UNCAUGHT in thread 出现异常，请留意 " + t.getName(), e));
        return thread;
    }
}
