package cn.zhanyiping.thread.task.consume;

import cn.zhanyiping.thread.CustomThreadPool;
import cn.zhanyiping.thread.domian.BusinessResult;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

/**
 * 执行线程池中的任务
 * 适用于消费和回滚模型的任务类型
 * Created by zhanyiping
 */
@Slf4j
public class ConsumeTaskExecute {

    /**
     * 消费连接池封装
     */
    private final CompletionService<BusinessResult> consumeCompletionService = new ExecutorCompletionService<>(CustomThreadPool.getExecutorService());

    /**
     * 提交执行的任务Entry队列
     */
    private final List<TaskEntry> submittedTaskEntryList = new LinkedList<>();

    /**
     * 提交任务队列
     * 将任务保存在链表中
     */
    public void submitTask(ConsumeCallableTask callableTask) {
        submittedTaskEntryList.add(new TaskEntry(callableTask, consumeCompletionService.submit(callableTask)));
    }

    /**
     * 获取执行的结果
     * 并对结果进行处理
     */
    public BusinessResult getConsumeResult() {
        try {
            for (int i = 0 , size = submittedTaskEntryList.size() ; i < size ; i++) {
                BusinessResult businessResult = consumeCompletionService.take().get();
                //任务执行失败，执行回滚操作，然后返回失败信息
                if (!businessResult.isSuccess()){
                    rollback();
                    return businessResult;
                }
            }
        } catch (Throwable t){
            log.error("任务消费时执行失败", t);
            //出现异常后回滚，然后返回失败
            rollback();
            return BusinessResult.getFail();
        }
        return BusinessResult.getSuccess();
    }

    /**
     * 回滚所有消费过的任务
     */
    private void rollback() {
        try {
            if (submittedTaskEntryList.isEmpty()){
                log.info("消费模型执行回滚 队列为空");
                return;
            }
            submittedTaskEntryList.forEach(this::executeRollback);
        } catch (Throwable t) {
            log.error("消费模型执行回滚 所有任务回滚异常", t);
        }
    }

    /**
     * 执行任务回滚操作，先保证任务执行完，然后再回滚
     * @param taskEntry 任务实体
     */
    private void executeRollback(TaskEntry taskEntry) {
        try {
            CustomThreadPool.getExecutorService().submit(
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

    /**
     * 过程类，值对<消费任务，消费任务future>
     */
    private static class TaskEntry {

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
         * @param consumeTask       消费任务
         */
        private TaskEntry(ConsumeCallableTask consumeTask, Future<BusinessResult> futureResult) {
            this.consumeTask = consumeTask;
            this.futureResult = futureResult;
        }

        private ConsumeCallableTask getConsumeTask() {
            return consumeTask;
        }

        private Future<BusinessResult> getFutureResult() {
            return futureResult;
        }
    }

}
