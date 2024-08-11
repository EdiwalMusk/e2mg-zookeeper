package com.e2gm.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

public class ZkTest {

    @Test
    public void testNormal_when_connect() throws Exception {
        // 连接ZooKeeper的字符串
        String connectionString = "192.168.56.1:2181";

        // 重试策略：初试时间为1秒，最大重试次数为3
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        // 创建CuratorFramework实例
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);

        // 启动客户端
        client.start();

        // 检查ZooKeeper服务是否可用，需要异步检查
        boolean isZkRunning = client.getZookeeperClient().isConnected();
        System.out.println("ZooKeeper is " + (isZkRunning ? "running" : "not running"));

        client.create().forPath("/user_query");

        // 关闭客户端
        client.close();
    }
}
