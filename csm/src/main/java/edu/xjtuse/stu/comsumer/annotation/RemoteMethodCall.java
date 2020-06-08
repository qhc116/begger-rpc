package edu.xjtuse.stu.comsumer.annotation;

import java.lang.annotation.*;

/**
 * @author 失了秩
 * @date 2020/6/8 14:57
 * @description
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoteMethodCall {
}
