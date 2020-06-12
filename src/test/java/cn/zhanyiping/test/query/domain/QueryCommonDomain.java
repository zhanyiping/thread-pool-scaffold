package cn.zhanyiping.test.query.domain;

import lombok.Data;

@Data
public class QueryCommonDomain {

    private QueryUserDomain userDomain;

    private QueryProductDomain productDomain;

    private QueryStoreDomain storeDomain;
}
