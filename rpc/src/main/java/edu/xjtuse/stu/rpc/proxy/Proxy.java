package edu.xjtuse.stu.rpc.proxy;

import com.alibaba.fastjson.JSONObject;
import edu.xjtuse.stu.rpc.server.ServerRequest;
import edu.xjtuse.stu.rpc.server.ServerResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 失了秩
 * @date 2020/6/7 23:22
 * @description
 */
public class Proxy {
    private static Map<String, BeanMethod> map;

    static {
        map = new HashMap<>();
    }

    public static void put(String s, BeanMethod bm) {
        map.put(s, bm);
    }

    public static ServerResponse execute(ServerRequest request) {
        String command = request.getCommand();
        if (command == null) {
            return null;
        }

        BeanMethod bm = map.get(command);
        Object bean = bm.getBean();
        Method method = bm.getMethod();
        Class[] paramsType = method.getParameterTypes();
        Object[] params = new Object[paramsType.length];
        Object msg = request.getMsg();

        // parse params
        for (int i = 0; i < params.length; i++) {
            params[i] = JSONObject.parseObject(JSONObject.toJSONString(msg), paramsType[i]);
        }

        try {
            ServerResponse res = (ServerResponse) method.invoke(bean, params);
            res.setId(request.getId());
            return res;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
