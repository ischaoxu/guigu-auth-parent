package com.wiselzx.system.exception;

import com.wiselzx.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandller {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result globalException() {
        return Result.fail().message("服务器繁忙请稍后再试");
    }
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result myException(Exception e) {
        return Result.fail().message(e.getMessage());
    }


}
