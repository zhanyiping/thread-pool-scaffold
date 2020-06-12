package cn.zhanyiping.test.query.task;

import cn.zhanyiping.test.query.domain.QueryCommonDomain;
import cn.zhanyiping.test.query.domain.QueryProductDomain;
import cn.zhanyiping.test.query.domain.QueryStoreDomain;
import cn.zhanyiping.test.query.domain.QueryUserDomain;
import cn.zhanyiping.thread.domian.BusinessResult;
import cn.zhanyiping.thread.task.query.QueryCallableTask;

public class QueryStoreTask extends QueryCallableTask {

    private final QueryCommonDomain commonDomain;

    public QueryStoreTask(QueryCommonDomain commonDomain) {
        this.commonDomain = commonDomain;
    }

    @Override
    public BusinessResult execute() {
        //模拟查询门店信息
        QueryStoreDomain storeDomain = new QueryStoreDomain();
        storeDomain.setAddress("北京");
        storeDomain.setStoreName("杂货铺");
        //将门店信息设置到公共对象中
        commonDomain.setStoreDomain(storeDomain);
        return BusinessResult.getSuccess();
    }

    @Override
    public void afterResult(BusinessResult businessResult) {
        System.out.println("查询门店信息完成");
    }
}
