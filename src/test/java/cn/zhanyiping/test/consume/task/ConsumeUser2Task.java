package cn.zhanyiping.test.consume.task;

import cn.zhanyiping.test.consume.domain.ConsumeUserDomain;
import cn.zhanyiping.thread.domian.BusinessResult;
import cn.zhanyiping.thread.task.consume.ConsumeCallableTask;

public class ConsumeUser2Task extends ConsumeCallableTask {

    /**
     * 创建一个用户名为 user2 初始化积分为 500 的用户
     */
    private final static ConsumeUserDomain userDomain = new ConsumeUserDomain("user2", 500);

    /**
     * 消费是用掉用户的50积分
     */
    @Override
    public BusinessResult consume() {
        userDomain.setPoints(userDomain.getPoints() - 50);
        return BusinessResult.createBusinessResult(userDomain);
    }

    /**
     * 当消费出现异常时，需要把50积分给用户加回来
     */
    @Override
    public BusinessResult rollback() {
        userDomain.setPoints(userDomain.getPoints() + 50);
        return BusinessResult.createBusinessResult(userDomain);
    }

}
