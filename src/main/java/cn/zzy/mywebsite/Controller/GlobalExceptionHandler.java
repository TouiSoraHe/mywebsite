package cn.zzy.mywebsite.Controller;

import cn.zzy.mywebsite.Data.ResponseJson;
import cn.zzy.mywebsite.Exception.AssetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseJson IllegalArgumentExceptionHandler(Exception e)
    {
        String error = GetErrorMessage(e);
        log.error(error);
        return ResponseJson.CreateError("406",error);
    }

    @ExceptionHandler(AssetNotFoundException.class)
    @ResponseBody
    public ResponseJson AssetNotFoundExceptionHandler(Exception e)
    {
        String error = GetErrorMessage(e);
        log.error(error);
        return ResponseJson.CreateError("404",error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseJson DefaultErrorHandler(Exception e) {
        String error = GetErrorMessage(e);
        log.error(error);
        return ResponseJson.CreateError("500",error);
    }

    private static String GetErrorMessage(Exception e)
    {
        String message = "";
        if(e.getCause()!=null && e.getCause().getMessage()!=null && !"".equals(e.getCause().getMessage()))
        {
            message = e.getCause().getMessage();
        }
        else if(e.getMessage()!=null && !"".equals(e.getMessage()))
        {
            message = e.getMessage();
        }
        return  message;
    }
}
