package com.leyou.common.exception;

import com.leyou.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Auther: litaixu
 * @Date: 2021/5/2 - 05 - 02 - 0:07
 * @Description: com.leyou.common.exception
 * @version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LyException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
