package cn.zhanyiping.thread.domian;

/**
 * 业务结果信息类
 * @param <T> 返回信息中的数据信息
 */
public class BusinessResult<T> {

    /**
     * 失败或成功的状态
     * true 成功，false 失败
     */
    private boolean status;
    /**
     * 消息
     */
    private String msg;
    /**
     * 结果
     */
    private T result;

    public BusinessResult(boolean status, String msg , T result){
        this.status = status;
        this.msg = msg;
        this.result = result;
    }

    public BusinessResult(boolean status , String msg){
        this.status = status;
        this.msg = msg;
    }

    public boolean isSuccess(){
        return this.status;
    }

    /**
     * 成功
     */
    private static final BusinessResult success = new BusinessResult(true ,"成功");
    /**
     * 失败
     */
    private static final BusinessResult fail = new BusinessResult(false, "失败");

    /**
     * 成功时，只返回有状态的值
     * @return
     */
    public static BusinessResult getSuccess(){
        return success;
    }

    /**
     * 失败的返回
     * @return
     */
    public static BusinessResult getFail(){
        return fail;
    }
}
