package cn.vonce.common.annotation;

import java.lang.annotation.*;

/**
 * 日志内容
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface LogContent {

    String value();

}
