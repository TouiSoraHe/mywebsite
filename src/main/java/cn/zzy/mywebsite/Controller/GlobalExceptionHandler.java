package cn.zzy.mywebsite.Controller;

import cn.zzy.mywebsite.AOP.AnnotationTarget.ExceptionAopTarget;
import cn.zzy.mywebsite.Data.ResponseJson;
import cn.zzy.mywebsite.Exception.AssetNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static cn.zzy.mywebsite.Tools.Util.GetErrorMessage;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ExceptionAopTarget
    public ResponseEntity IllegalArgumentExceptionHandler(HttpServletRequest request,Exception e)
    {
        return ResponseEntity.ok(ResponseJson.CreateError("406", GetErrorMessage(e)));
    }

    @ExceptionHandler(AssetNotFoundException.class)
    @ExceptionAopTarget
    public ResponseEntity AssetNotFoundExceptionHandler(HttpServletRequest request, Exception e)
    {
        return ResponseEntity.ok(ResponseJson.CreateError("404", GetErrorMessage(e)));
    }

    @ExceptionHandler(Exception.class)
    @ExceptionAopTarget
    public ResponseEntity DefaultErrorHandler(HttpServletRequest request, Exception e) {
        return ResponseEntity.ok(ResponseJson.CreateError("500", GetErrorMessage(e)));
    }
}
