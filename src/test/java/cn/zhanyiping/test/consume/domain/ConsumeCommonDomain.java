package cn.zhanyiping.test.consume.domain;

import cn.zhanyiping.test.query.domain.QueryProductDomain;
import cn.zhanyiping.test.query.domain.QueryStoreDomain;
import cn.zhanyiping.test.query.domain.QueryUserDomain;
import lombok.Data;

@Data
public class ConsumeCommonDomain {

    private QueryUserDomain userDomain;

    private QueryProductDomain productDomain;

    private QueryStoreDomain storeDomain;
}
