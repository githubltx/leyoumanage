package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Auther: litaixu
 * @Date: 2021/5/2 - 05 - 02 - 0:08
 * @Description: com.leyou.common.enums
 * @version: 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    PRICE_COUNT_NOT_NULL(400,"😄😄，商品价格不能为空！！！"),
    CATEGORY_NOT_FOUND(404,"商品分类没查到")
    ;
    private Integer code;
    private String msg;

}
