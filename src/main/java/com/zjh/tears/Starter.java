package com.zjh.tears;

import com.zjh.tears.config.Config;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class Starter {
    public static void main(String[] args) {
        Server runServer = new Server(Config.PORT, Config.THREAD_POOL_SIZE);
        Runnable run = () -> runServer.start();

        ShutdownServer shutdownServer = new ShutdownServer(8008, runServer);
        Runnable shutdown = () -> shutdownServer.start();

        new Thread(run).start();
        new Thread(shutdown).start();
    }
}
