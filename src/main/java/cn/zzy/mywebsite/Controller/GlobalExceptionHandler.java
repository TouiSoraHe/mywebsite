package cn.zzy.mywebsite.Controller;

import cn.zzy.mywebsite.Data.ResponseJson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseJson DefaultErrorHandler(Exception e) {
        ResponseJson ret = new ResponseJson("500",e.getCause().getMessage(),null);
        return ret;
    }
}
