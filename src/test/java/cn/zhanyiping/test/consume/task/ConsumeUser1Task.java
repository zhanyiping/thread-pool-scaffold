package cn.zhanyiping.test.consume.task;

import cn.zhanyiping.test.consume.domain.ConsumeUserDomain;
import cn.zhanyiping.thread.domian.BusinessResult;
import cn.zhanyiping.thread.task.consume.ConsumeCallableTask;

public class ConsumeUser1Task extends ConsumeCallableTask {

    /**
     * 创建一个用户名为 user1 初始化积分为 100 的用户
     */
    private final static ConsumeUserDomain userDomain = new ConsumeUserDomain("user1", 100);

    /**
     * 消费是用掉用户的10积分
     */
    @Override
    public BusinessResult consume() {
        userDomain.setPoints(userDomain.getPoints() - 10);
        return BusinessResult.createBusinessResult(userDomain);
    }

    /**
     * 当消费出现异常时，需要把10积分给用户加回来
     */
    @Override
    public BusinessResult rollback() {
        userDomain.setPoints(userDomain.getPoints() + 10);
        return BusinessResult.createBusinessResult(userDomain);
    }

}
