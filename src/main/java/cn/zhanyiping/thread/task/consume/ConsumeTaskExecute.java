package cn.zhanyiping.thread.task.consume;

import cn.zhanyiping.thread.MyThreadPool;
import cn.zhanyiping.thread.domian.BusinessResult;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

/**
 * 执行线程池中的任务
 * 适用于消费和回滚模型的任务类型
 * Created by zhanyiping on 2019/4/1.
 */
@SuppressWarnings("all")
@Slf4j
public class ConsumeTaskExecute {

    /**
     * 消费连接池封装
     */
    private final CompletionService<BusinessResult> consumeCompletionService = new ExecutorCompletionService<BusinessResult>(MyThreadPool.getExecutorService());
    /**
     * 保存执行任务的链表
     */
    private final List<ConsumeCallableTask> callableTaskList = new LinkedList<ConsumeCallableTask>();

    /**
     * 提交执行的任务Entry队列
     */
    private final Queue<TaskEntry> submitedTaskEntryQueue = new LinkedList<TaskEntry>();

    /**
     * 是否有某个任务失败或异常
     */
    private boolean isFailOrExcept = false;

    /**
     * 提交任务队列
     * 将任务保存在链表中
     */
    public synchronized void submitTask(ConsumeCallableTask callableTask) {
        callableTaskList.add(callableTask);
        submitedTaskEntryQueue.offer(TaskEntry.create(callableTask, consumeCompletionService.submit(callableTask)));
    }

    /**
     * 获取执行的结果
     * 并对结果进行处理
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public BusinessResult getConsumeResult() throws InterruptedException, ExecutionException {
        //任务执行失败的标识
        try {
            for (int i = 0 , size = callableTaskList.size() ; i < size ; i++) {
                BusinessResult superVipBusinessResult = consumeCompletionService.take().get();
                if (!superVipBusinessResult.isSuccess()){
                    rollback();
                    return superVipBusinessResult;
                }
            }
        } catch (Throwable t){
            log.error("任务消费时执行失败", t);
            rollback();
            return BusinessResult.getFail();
        }
        return BusinessResult.getSuccess();
    }

    /**
     * 回滚所有消费过的任务
     *
     * @return RollbackResult
     */
    public BusinessResult rollback() {
        try {
            if (submitedTaskEntryQueue.isEmpty()){
                log.info("消费模型执行回滚 队列为空");
                return BusinessResult.getFail();
            }
            TaskEntry taskEntry = submitedTaskEntryQueue.poll();
            while (Objects.nonNull(taskEntry)) {
                try {
                    MyThreadPool.getExecutorService().submit(
                        () -> {
                            try {
                                taskEntry.getFutureResult().get();
                            } catch (Throwable t) {
                                log.error("消费模型执行回滚 任务执行出异常", t);
                            }
                            try {
                                return taskEntry.getConsumeTask().rollback();
                            } catch (Throwable t) {
                                log.error("消费模型执行回滚 任务回滚出异常", t);
                            }
                            return BusinessResult.getSuccess();
                        });
                } catch (Throwable t){
                    log.error("消费模型执行回滚 单个任务回滚异常", t);
                }
            }
        } catch (Throwable t) {
            log.error("消费模型执行回滚 所有任务回滚异常", t);
        }
        return BusinessResult.getSuccess();
    }

    /**
     * 过程类，值对<消费任务，消费任务future>
     */
    private static class TaskEntry {
        /**
         * 静态构造
         *
         * @param consumeTask       消费任务
         * @param consumeTaskFuture 消费任务结果future
         * @return Entry
         */
        public static final TaskEntry create(ConsumeCallableTask consumeTask, Future<BusinessResult> futureResult) {
            return new TaskEntry(consumeTask, futureResult);
        }

        /**
         * 消费任务
         */
        private final ConsumeCallableTask consumeTask;

        /**
         * 消费任务结果future
         */
        private final Future<BusinessResult> futureResult;

        /**
         * 构造函数
         *
         * @param consumeTask       消费任务
         * @param consumeTaskFuture 消费任务结果future
         */
        private TaskEntry(ConsumeCallableTask consumeTask, Future<BusinessResult> futureResult) {
            this.consumeTask = consumeTask;
            this.futureResult = futureResult;
        }

        public ConsumeCallableTask getConsumeTask() {
            return consumeTask;
        }

        public Future<BusinessResult> getFutureResult() {
            return futureResult;
        }
    }

}
