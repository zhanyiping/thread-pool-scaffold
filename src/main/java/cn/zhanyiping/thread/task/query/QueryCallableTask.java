package cn.zhanyiping.thread.task.query;


import cn.zhanyiping.thread.domian.BusinessResult;

import java.util.concurrent.Callable;

/**
 * 任务的实现类 创建的任务需要继承该类
 * 适用于查询类的任务
 * create by zhanyiping on 2019/4/1
 */
public abstract class QueryCallableTask implements Callable<BusinessResult> {

    @Override
    public BusinessResult call() throws Exception {
        return execute();
    }

    public abstract BusinessResult execute();

    public abstract void afterResult(BusinessResult businessResult);
}
