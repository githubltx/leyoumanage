package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

import java.text.DateFormat;
import java.util.Date;

/**
 * @Auther: litaixu
 * @Date: 2021/5/2 - 05 - 02 - 0:22
 * @Description: com.leyou.common.vo
 * @version: 1.0
 */
@Data

public class ExceptionResult {
    private Integer status;
    private String msg;
    private Date time;

    public ExceptionResult(ExceptionEnum exceptionEnum) {
        this.status = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
        Date date = new Date();
        this.time=date ;
    }
}
