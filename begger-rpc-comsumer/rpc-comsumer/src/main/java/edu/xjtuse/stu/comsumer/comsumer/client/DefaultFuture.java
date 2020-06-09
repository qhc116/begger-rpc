package edu.xjtuse.stu.comsumer.comsumer.client;

import java.sql.Time;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 失了秩
 * @date 2020/6/7 9:25
 * @description
 */
public class DefaultFuture {
    public static ConcurrentHashMap<Long, DefaultFuture> map;
    private final ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Response clientResponse;
    private long startTime = System.currentTimeMillis();
    private long timeout = 5000;

    static {
        map = new ConcurrentHashMap<>();
        // 清除map中存储的无效数据
        Thread thread = new Thread(() ->{
            DefaultFuture mapClear;
            Set<Long> ids = map.keySet();
            for (Long id : ids) {
                mapClear = map.get(id);
                if (mapClear == null) {
                    map.clear();
                } else if (System.currentTimeMillis() - mapClear.startTime > mapClear.timeout) {
                    Response response = new Response();
                    response.setCode(5001);
                    response.setFailReason("链路超时，服务器无响应");
                    receive(response);
                }
            }
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Response getClientResponse() {
        return clientResponse;
    }

    public void setClientResponse(Response clientResponse) {
        this.clientResponse = clientResponse;
    }



    public DefaultFuture(ClientRequest request) {
        map.put(request.getId(), this);
    }

    public Response get() {
        lock.lock();
        try {
            while (!done()) {
                condition.await(timeout, TimeUnit.MILLISECONDS);
                if (System.currentTimeMillis() - startTime > timeout) {
                    clientResponse.setCode(5001);
                    clientResponse.setFailReason("链路超时，服务器无响应");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return this.clientResponse;
    }

    public static void  receive(Response clientResponse) {
        DefaultFuture defaultFuture = map.get(clientResponse.getId());
        if (defaultFuture != null) {
            ReentrantLock lock = defaultFuture.lock;
            try{
                lock.lock();
                defaultFuture.setClientResponse(clientResponse);
                defaultFuture.condition.signal();
                // remove from map after signal
                map.remove(clientResponse.getId());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }

    private boolean done() {
        if (this.clientResponse != null) {
            return true;
        }
        return false;
    }
}
