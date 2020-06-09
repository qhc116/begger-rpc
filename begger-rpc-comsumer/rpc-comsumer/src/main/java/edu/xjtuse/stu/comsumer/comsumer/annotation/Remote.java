package edu.xjtuse.stu.comsumer.comsumer.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author 失了秩
 * @date 2020/6/8 14:31
 * @description
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Remote {
    String value() default "";
}
