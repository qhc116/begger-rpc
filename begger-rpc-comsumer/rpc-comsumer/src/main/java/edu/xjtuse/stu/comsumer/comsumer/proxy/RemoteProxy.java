package edu.xjtuse.stu.comsumer.comsumer.proxy;

import edu.xjtuse.stu.comsumer.comsumer.annotation.RemoteMethodCall;
import edu.xjtuse.stu.comsumer.comsumer.client.ClientRequest;
import edu.xjtuse.stu.comsumer.comsumer.client.Response;
import edu.xjtuse.stu.comsumer.comsumer.client.TcpClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 失了秩
 * @date 2020/6/8 17:24
 * @description
 */
@Component
public class RemoteProxy implements BeanPostProcessor {
    final Map<Method, Class> methodClassMap = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(RemoteMethodCall.class)) {
                field.setAccessible(true);
                putMethodClass(methodClassMap, field);
                Enhancer enhancer = new Enhancer();
                enhancer.setInterfaces(new Class[]{field.getType()});
                enhancer.setCallback(new MethodInterceptor() {
                    // 拦截方法并使用 netty 客户端调用服务器
                    @Override
                    public Object intercept(Object instance, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                        ClientRequest request = new ClientRequest();
                        request.setCommand(methodClassMap.get(method).getName() + "." +method.getName());
                        request.setMsg(args[0]);
                        Response res = TcpClient.send(request);
                        return res;
                    }
                });
                try {
                    field.set(bean, enhancer.create());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }

    /**
     * 功能描述: 将标记了 @RemoteMethodCall 的属性的接口，和接口的方法对应起来，加入map中
     */
    private void putMethodClass(Map<Method, Class> methodClassMap, Field field) {
        Method[] methods = field.getType().getDeclaredMethods();
        for (Method method : methods) {
            methodClassMap.put(method, field.getType());
        }

    }
}
