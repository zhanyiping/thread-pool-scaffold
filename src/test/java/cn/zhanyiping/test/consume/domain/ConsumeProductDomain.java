package cn.zhanyiping.test.consume.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsumeProductDomain {

    /**
     * 用户名
     */
    private String  productName;
    /**
     * 商品库存数量
     */
    private Integer count;


}
