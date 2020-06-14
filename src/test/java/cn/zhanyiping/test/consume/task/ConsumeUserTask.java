package cn.zhanyiping.test.consume.task;

import cn.zhanyiping.test.consume.domain.ConsumeCommonDomain;
import cn.zhanyiping.test.consume.domain.ConsumeUserDomain;
import cn.zhanyiping.thread.domian.BusinessResult;
import cn.zhanyiping.thread.task.consume.ConsumeCallableTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumeUserTask extends ConsumeCallableTask {

    private final ConsumeCommonDomain commonDomain;

    private final Integer count;

    public ConsumeUserTask(ConsumeCommonDomain commonDomain , Integer count) {
        this.commonDomain = commonDomain;
        this.count = count;
    }

    /**
     * 消费是用掉用户的积分
     */
    @Override
    public BusinessResult consume() {
        try {
            commonDomain.getUserDomain().setPoints(commonDomain.getUserDomain().getPoints() - count);
            return BusinessResult.getSuccess();
        } catch (Throwable t) {
            log.error("消费积分异常" , t);
            return BusinessResult.getFail();
        }
    }

    /**
     * 当消费出现异常时，需要把积分给用户加回来
     */
    @Override
    public BusinessResult rollback() {
        try {
            commonDomain.getUserDomain().setPoints(commonDomain.getUserDomain().getPoints() + count);
        } catch (Throwable t) {
            log.error("回滚积分异常" , t);
        }
        //回滚失败后发送MQ补偿
        //sendMQ()
        return BusinessResult.getSuccess();
    }

}
