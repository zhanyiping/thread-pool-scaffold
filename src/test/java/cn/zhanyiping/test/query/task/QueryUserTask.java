package cn.zhanyiping.test.query.task;

import cn.zhanyiping.test.query.domain.QueryCommonDomain;
import cn.zhanyiping.test.query.domain.QueryUserDomain;
import cn.zhanyiping.thread.domian.BusinessResult;
import cn.zhanyiping.thread.task.query.QueryCallableTask;

public class QueryUserTask extends QueryCallableTask {

    private QueryCommonDomain commonDomain;

    public QueryUserTask(QueryCommonDomain commonDomain) {
        this.commonDomain = commonDomain;
    }

    @Override
    public BusinessResult execute() {
        //模拟查询用户的操作
        QueryUserDomain userDomain = new QueryUserDomain();
        userDomain.setUserName("user");
        userDomain.setScore(80);
        //将用户信息设置到公共对象中
        commonDomain.setUserDomain(userDomain);
        return BusinessResult.getSuccess();
    }

    @Override
    public void afterResult(BusinessResult businessResult) {
        System.out.println("查询用户信息完成");
    }
}
