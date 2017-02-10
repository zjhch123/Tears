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

    public Server(int port, int threadPoolSize) {
        this.port = port;
        this.threadPoolSize = threadPoolSize;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            ExecutorService threadPool = Executors.newFixedThreadPool(this.threadPoolSize);
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(() -> new SocketProcess(socket).process());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
