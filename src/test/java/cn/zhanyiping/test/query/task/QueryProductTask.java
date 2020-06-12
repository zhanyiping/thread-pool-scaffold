package cn.zhanyiping.test.query.task;

import cn.zhanyiping.test.query.domain.QueryCommonDomain;
import cn.zhanyiping.test.query.domain.QueryProductDomain;
import cn.zhanyiping.thread.domian.BusinessResult;
import cn.zhanyiping.thread.task.query.QueryCallableTask;

public class QueryProductTask extends QueryCallableTask {

    private final QueryCommonDomain commonDomain;

    public QueryProductTask(QueryCommonDomain commonDomain) {
        this.commonDomain = commonDomain;
    }

    @Override
    public BusinessResult execute() {
        //模拟查询商品信息
        QueryProductDomain productDomain = new QueryProductDomain();
        productDomain.setProductName("手机");
        productDomain.setPrice(300000L);
        //将查询到的商品信息，设置到公共对象中
        commonDomain.setProductDomain(productDomain);
        return BusinessResult.getSuccess();
    }

    @Override
    public void afterResult(BusinessResult businessResult) {
        System.out.println("查询商品信息完成");
    }
}
