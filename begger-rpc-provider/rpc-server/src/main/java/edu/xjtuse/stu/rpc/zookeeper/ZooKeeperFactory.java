package edu.xjtuse.stu.rpc.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author 失了秩
 * @date 2020/6/6 17:36
 * @description
 */
public class ZooKeeperFactory {
    private volatile static CuratorFramework client;

    private ZooKeeperFactory() {}

    public static CuratorFramework create() {
        if (client == null) {
            synchronized (ZooKeeperFactory.class) {
                if (client == null) {
                    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
                    CuratorFramework client =
                            CuratorFrameworkFactory.builder()
                                    .connectString("127.0.0.1:2181")
                                    .sessionTimeoutMs(5000)
                                    .connectionTimeoutMs(5000)
                                    .retryPolicy(retryPolicy)
                                    .build();
                    client.start();
                    return client;
                }
            }
        }
        return client;
    }
}
