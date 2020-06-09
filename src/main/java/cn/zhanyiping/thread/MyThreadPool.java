package cn.zhanyiping.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 * Created by zhanyiping on 2019/4/1.
 */
public class MyThreadPool {

    /**
     * 自定义线程池
     * 4个核心线程，最大线程数8，空闲线程存活时间5秒，队列大小5，主线程执行策略
     */
    private static final ExecutorService executor = new ThreadPoolExecutor(4, 8, 5l, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(5), new MyThreadFactory("my-thread-pool"), new MyThreadPoolPolicy());

    public static ExecutorService getExecutorService(){
       return executor;
   }

}
