package edu.xjtuse.stu.rpc.client;

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
    public static ConcurrentHashMap<Long, DefaultFuture> map = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private ClientResponse clientResponse;

    public ClientResponse getClientResponse() {
        return clientResponse;
    }

    public void setClientResponse(ClientResponse clientResponse) {
        this.clientResponse = clientResponse;
    }



    public DefaultFuture(ClientRequest request) {
        map.put(request.getId(), this);
    }

    public ClientResponse get() {
        lock.lock();
        try {
            while (!done()) {
                condition.await(1, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return this.clientResponse;
    }

    public static void  receive(ClientResponse clientResponse) {
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
