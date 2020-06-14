package cn.zhanyiping.test.consume;

import cn.zhanyiping.test.consume.domain.ConsumeCommonDomain;
import cn.zhanyiping.test.consume.domain.ConsumeProductDomain;
import cn.zhanyiping.test.consume.domain.ConsumeUserDomain;
import cn.zhanyiping.test.consume.task.ConsumeUserTask;
import cn.zhanyiping.test.consume.task.ConsumeProductTask;
import cn.zhanyiping.test.query.domain.QueryUserDomain;
import cn.zhanyiping.thread.CustomThreadPool;
import cn.zhanyiping.thread.domian.BusinessResult;
import cn.zhanyiping.thread.task.consume.ConsumeTaskExecute;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConsumeTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ConsumeCommonDomain commonDomain = new ConsumeCommonDomain();
        ConsumeTaskExecute execute = new ConsumeTaskExecute();
        execute.submitTask(new ConsumeUserTask(commonDomain , 100));
        execute.submitTask(new ConsumeProductTask(commonDomain , 50));
        BusinessResult result = execute.getConsumeResult();
        if (result.isSuccess()) {
            System.out.println("用户名称：" + commonDomain.getUserDomain().getUserName() + "，积分：" + commonDomain.getUserDomain().getPoints());
            System.out.println("商品名称：" + commonDomain.getProductDomain().getProductName() + "，库存：" + commonDomain.getProductDomain().getCount());
        } else  {
            System.out.println("资源消费异常，资源会被回滚，给用户返回错误信息");
        }
        CustomThreadPool.getExecutorService().shutdownNow();
        System.out.println("end");
    }

}
