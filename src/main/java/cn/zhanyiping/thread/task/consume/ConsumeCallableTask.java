package cn.zhanyiping.thread.task.consume;


import cn.zhanyiping.thread.domian.BusinessResult;

import java.util.concurrent.Callable;

/**
 * 任务的实现类 创建的任务需要继承该类
 * 适用于消费和回滚模型的任务
 * create by zhanyiping
 */
public abstract class ConsumeCallableTask implements Callable<BusinessResult> {

    @Override
    public BusinessResult call() {
        return consume();
    }

    public abstract BusinessResult consume();

    public abstract BusinessResult rollback();
}
