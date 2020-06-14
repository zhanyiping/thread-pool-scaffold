package cn.zhanyiping.test.query;

import cn.zhanyiping.test.query.domain.QueryCommonDomain;
import cn.zhanyiping.test.query.task.QueryUserTask;
import cn.zhanyiping.test.query.task.QueryProductTask;
import cn.zhanyiping.test.query.task.QueryStoreTask;
import cn.zhanyiping.thread.CustomThreadPool;
import cn.zhanyiping.thread.domian.BusinessResult;
import cn.zhanyiping.thread.task.query.QueryTaskExecute;

import java.util.concurrent.ExecutionException;

public class QueryTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        QueryCommonDomain commonDomain = new QueryCommonDomain();
        QueryTaskExecute execute = new QueryTaskExecute();
        execute.submitTask(new QueryUserTask(commonDomain));
        execute.submitTask(new QueryProductTask(commonDomain));
        execute.submitTask(new QueryStoreTask(commonDomain));
        BusinessResult result = execute.getResult();
        System.out.println("用户名称：" + commonDomain.getUserDomain().getUserName() + " 分数：" + commonDomain.getUserDomain().getScore());
        System.out.println("商品名称：" + commonDomain.getProductDomain().getProductName() + " 价格：" + commonDomain.getProductDomain().getPrice());
        System.out.println("门店名称：" + commonDomain.getStoreDomain().getStoreName() + " 地址：" + commonDomain.getStoreDomain().getAddress());
        CustomThreadPool.getExecutorService().shutdownNow();
        System.out.println("end");
    }

}
