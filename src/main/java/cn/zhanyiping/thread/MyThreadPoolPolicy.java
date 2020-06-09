package cn.zhanyiping.thread;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池策略，基于ThreadPoolExecutor.CallerRunsPolicy策略增加执行者线程的描述日志
 * Created by zhanyiping on 2019/4/1.
 */
@Slf4j
public class MyThreadPoolPolicy implements RejectedExecutionHandler {
    /**
     * 调用者运行策略(线程池提供) 当前线程执行
     */
    private ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        desc(executor);
        callerRunsPolicy.rejectedExecution(r, executor);
    }

    /**
     * 线程池的使用情况
     *
     * @param executor 执行者
     */
    private void desc(ThreadPoolExecutor executor) {
        String threadPoolDesc = "CurrentPoolSize 当前线程池大小: " + executor.getPoolSize() +
                " - CorePoolSize 核心线程数: " + executor.getCorePoolSize() +
                " - MaximumPoolSize 最大线程数: " + executor.getMaximumPoolSize() +
                " - ActiveCount 活动线程数: " + executor.getActiveCount() +
                " - CompletedTaskCount 执行完的任务数量: " + executor.getCompletedTaskCount() +
                " - TotalTaskCount 任务总数: " + executor.getTaskCount() +
                " - isTerminated 是否停止: " + executor.isTerminated() +
                " - queueTaskCount 队列中的任务数量: " + executor.getQueue().size();
        log.info(threadPoolDesc);
    }
}
