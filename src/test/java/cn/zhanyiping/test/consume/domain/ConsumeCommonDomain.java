package cn.zhanyiping.test.consume.domain;

import lombok.Data;

@Data
public class ConsumeCommonDomain {

    private ConsumeUserDomain userDomain = new ConsumeUserDomain("user", 1000);

    private ConsumeProductDomain productDomain = new ConsumeProductDomain("手机", 500);

}
