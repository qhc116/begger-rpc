package edu.xjtuse.stu.rpc.proxy;

import edu.xjtuse.stu.rpc.annotation.Remote;
import edu.xjtuse.stu.rpc.annotation.RemoteMethodCall;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author 失了秩
 * @date 2020/6/7 23:12
 * @description get and save class and methods for post processor
 */
@Component
public class ProxyInitializer implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Remote.class)) {
            System.out.println(bean.getClass().getName());
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                String key = bean.getClass().getInterfaces()[0].getName() + "." + method.getName();
                BeanMethod bm = new BeanMethod();
                bm.setBean(bean);
                bm.setMethod(method);
                Proxy.put(key, bm);
            }

        }
        return bean;
    }
}
