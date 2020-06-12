package cn.zhanyiping.thread.task.query;

import cn.zhanyiping.thread.CustomThreadPool;
import cn.zhanyiping.thread.domian.BusinessResult;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;

/**
 * 执行线程池中的任务
 * 适用于查询类的任务
 * Created by zhanyiping
 */
public class QueryTaskExecute {

    /**
     * 连接池
     */
    private final CompletionService<BusinessResult> completionService = new ExecutorCompletionService<>(CustomThreadPool.getExecutorService());

    /**
     * 保存执行任务的链表
     */
    private final List<QueryCallableTask> callableTaskList = new LinkedList<>();

    /**
     * 提交任务队列
     * 将任务保存在链表中
     */
    public void submitTask(QueryCallableTask callableTask) {
        callableTaskList.add(callableTask);
        completionService.submit(callableTask);
    }

    /**
     * 获取执行的结果
     * 并对结果进行处理
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public BusinessResult getResult() throws InterruptedException, ExecutionException {
        for (QueryCallableTask callableTask : callableTaskList) {
            BusinessResult businessResult = completionService.take().get();
            if (!businessResult.isSuccess()){
                return businessResult;
            }
            callableTask.afterResult(businessResult);
        }
        return BusinessResult.getSuccess();
    }

}
