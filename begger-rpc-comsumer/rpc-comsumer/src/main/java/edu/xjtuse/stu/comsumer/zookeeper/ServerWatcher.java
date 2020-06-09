package edu.xjtuse.stu.comsumer.zookeeper;

import edu.xjtuse.stu.comsumer.comsumer.client.TcpClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

import java.util.List;

/**
 * @author 失了秩
 * @date 2020/6/9 18:40
 * @description
 */
public class ServerWatcher implements CuratorWatcher {
    CuratorFramework client = ZooKeeperFactory.create();

    @Override
    public void process(WatchedEvent watchedEvent) throws Exception {
        client.getChildren().usingWatcher(this);
        String path = watchedEvent.getPath();
        List<String> serverPaths = client.getChildren().forPath(path);
        TcpClient.updateServerPaths(serverPaths);
    }
}

