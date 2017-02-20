package com.zjh.tears;

import com.zjh.tears.process.SocketProcess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class Server {

    private int port;
    private int threadPoolSize;
    private ExecutorService threadPool;

    private boolean flag = true;

    public Server(int port) {
        this.port = port;
    }

    public Server(int port, int threadPoolSize) {
        this.port = port;
        this.threadPoolSize = threadPoolSize;
        this.threadPool = Executors.newFixedThreadPool(this.threadPoolSize);
    }

    public int getPort() {
        return port;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            while (this.flag) {
                Socket socket = serverSocket.accept();
                threadPool.execute(() -> new SocketProcess(socket).process());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destory() {
        this.flag = false;
        this.threadPool.shutdownNow();
    }

}
