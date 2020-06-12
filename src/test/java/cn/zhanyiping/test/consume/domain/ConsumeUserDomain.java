package cn.zhanyiping.test.consume.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsumeUserDomain {

    /**
     * 用户名
     */
    private String  userName;
    /**
     * 用户积分
     */
    private Integer points;


}
