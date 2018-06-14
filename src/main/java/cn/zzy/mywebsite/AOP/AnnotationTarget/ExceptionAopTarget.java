package cn.zzy.mywebsite.AOP.AnnotationTarget;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionAopTarget {
}
