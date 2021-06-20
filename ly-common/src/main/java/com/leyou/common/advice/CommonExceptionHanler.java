package com.leyou.common.advice;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Auther: litaixu
 * @Date: 2021/5/1 - 05 - 01 - 23:46
 * @Description: com.leyou.common.utils
 * @version: 1.0
 */
@ControllerAdvice
public class CommonExceptionHanler {
    @ExceptionHandler(LyException.class)
    public ResponseEntity<ExceptionResult> handException(LyException e){
        ExceptionEnum exceptionEnum = e.getExceptionEnum();
        return  ResponseEntity.status(exceptionEnum.getCode()).body(new ExceptionResult(exceptionEnum));
    }
}
