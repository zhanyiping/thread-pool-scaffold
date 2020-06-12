package cn.zhanyiping.test.consume;

import cn.zhanyiping.test.consume.task.ConsumeUser1Task;
import cn.zhanyiping.test.consume.task.ConsumeUser2Task;
import cn.zhanyiping.test.consume.task.ConsumeUser3Task;
import cn.zhanyiping.test.query.domain.QueryUserDomain;
import cn.zhanyiping.thread.CustomThreadPool;
import cn.zhanyiping.thread.domian.BusinessResult;
import cn.zhanyiping.thread.task.consume.ConsumeTaskExecute;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConsumeTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ConsumeTaskExecute execute = new ConsumeTaskExecute();
        execute.submitTask(new ConsumeUser1Task());
        execute.submitTask(new ConsumeUser2Task());
        execute.submitTask(new ConsumeUser3Task());
        BusinessResult result = execute.getConsumeResult();
        if (result.isSuccess()) {
            List<BusinessResult<QueryUserDomain>> resultList = (List<BusinessResult<QueryUserDomain>>)result.getResult();
            for (BusinessResult<QueryUserDomain> businessResult : resultList) {
                System.out.println("用户名：" + businessResult.getResult().getUserName() + " 分数：" + businessResult.getResult().getScore());
            }
        }
        CustomThreadPool.getExecutorService().shutdownNow();
        System.out.println("end");
    }

}
