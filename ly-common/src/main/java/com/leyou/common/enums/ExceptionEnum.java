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

    NOT_FOUND(404,"结果为空"),
    PRICE_COUNT_NOT_NULL(400,"😄😄，商品价格不能为空！！！"),
    CATEGORY_NOT_FOUND(404,"商品分类没查到"),
    SPEC_GROUP_NOT_FOUND(404,"商品属性分组没查到"),
    SPEC_GROUP_FAIL(404,"商品属性分组修改失败"),
    UPLOAD_ERROR(404,"文件上传失败"),
    GOODS_SAVE_ERROR(500,"商品新增失败"),
    ;
    private Integer code;
    private String msg;

}
