package cn.zhanyiping.test.query.domain;

import lombok.Data;

@Data
public class QueryProductDomain {

    /**
     * 商品名称
     */
    private String  productName;
    /**
     * 商品价格 单位分
     */
    private Long price;


}
