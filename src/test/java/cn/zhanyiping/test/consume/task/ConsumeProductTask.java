package cn.zhanyiping.test.consume.task;

import cn.zhanyiping.test.consume.domain.ConsumeCommonDomain;
import cn.zhanyiping.test.consume.domain.ConsumeProductDomain;
import cn.zhanyiping.thread.domian.BusinessResult;
import cn.zhanyiping.thread.task.consume.ConsumeCallableTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumeProductTask extends ConsumeCallableTask {

    private final ConsumeCommonDomain commonDomain;

    private final Integer count;

    public ConsumeProductTask(ConsumeCommonDomain commonDomain, Integer count) {
        this.commonDomain = commonDomain;
        this.count = count;
    }

    /**
     * 消费是用掉用户的50积分
     */
    @Override
    public BusinessResult consume() {
        try {
            commonDomain.getProductDomain().setCount(commonDomain.getProductDomain().getCount() - count);
//            int i = 1/0;
            return BusinessResult.getSuccess();
        } catch (Throwable t) {
            log.error("商品消费异常" , t);
            return BusinessResult.getFail();
        }
    }

    /**
     * 当消费出现异常时，需要把50积分给用户加回来
     */
    @Override
    public BusinessResult rollback() {
        try {
            commonDomain.getProductDomain().setCount(commonDomain.getProductDomain().getCount() + count);
        } catch (Throwable t) {
            log.error("商品回滚异常" , t);
        }
        //回滚失败后发送MQ补偿
        //sendMQ();
        return BusinessResult.getSuccess();
    }

}
