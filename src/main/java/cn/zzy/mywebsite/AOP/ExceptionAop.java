package cn.zzy.mywebsite.AOP;

import cn.zzy.mywebsite.Data.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static cn.zzy.mywebsite.Tools.Util.EncodeURIComponent;

@Aspect
@Component
@Slf4j
public class ExceptionAop {

    @Pointcut(value = "@annotation(cn.zzy.mywebsite.AOP.AnnotationTarget.ExceptionAopTarget) && args(request,e)")
    public void ExceptionAopPointCut(Exception e, HttpServletRequest request){ }

    @Around("ExceptionAopPointCut(e,request)")
    @ResponseBody
    public Object ExceptionAround(ProceedingJoinPoint joinPoint,Exception e,HttpServletRequest request) throws Throwable {
        ResponseEntity responseEntity = (ResponseEntity) joinPoint.proceed();
        ResponseJson responseJson = (ResponseJson)responseEntity.getBody();
        log.info("IP:"+request.getRemoteAddr()+"  URI:"+request.getRequestURI());
        log.error(responseJson.getMessage());
        if("GET".equals(request.getMethod())){
            MultiValueMap multiValueMap = new LinkedMultiValueMap();
            multiValueMap.add("Location","/Error?errorMessage="+EncodeURIComponent(responseJson.getMessage())+"&code="+responseJson.getCode());
            multiValueMap.add("charset", "utf-8");
            return new ResponseEntity(responseEntity.getBody(), multiValueMap,HttpStatus.TEMPORARY_REDIRECT);
        }
        return responseEntity;
    }


}
