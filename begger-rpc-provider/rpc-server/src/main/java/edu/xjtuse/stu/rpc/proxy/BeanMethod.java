package edu.xjtuse.stu.rpc.proxy;

import java.lang.reflect.Method;

/**
 * @author 失了秩
 * @date 2020/6/7 23:25
 * @description
 */
public class BeanMethod {
    private Object bean;
    private Method method;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

}
