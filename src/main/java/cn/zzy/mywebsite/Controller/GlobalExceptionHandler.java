package cn.zzy.mywebsite.Controller;

import cn.zzy.mywebsite.Data.ResponseJson;
import cn.zzy.mywebsite.Exception.AssetNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseJson IllegalArgumentExceptionHandler(Exception e)
    {
        return ResponseJson.CreateError("406",GetErrorMessage(e));
    }

    @ExceptionHandler(AssetNotFoundException.class)
    @ResponseBody
    public ResponseJson AssetNotFoundExceptionHandler(Exception e)
    {
        return ResponseJson.CreateError("404",GetErrorMessage(e));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseJson DefaultErrorHandler(Exception e) {
        return ResponseJson.CreateError("500",GetErrorMessage(e));
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
