package com.zjh.tears;

import com.zjh.tears.process.SocketProcess;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class Server {

    private Logger logger = Logger.getLogger(this.getClass());

    private int port;
    private int threadPoolSize;
    private ServerSocket serverSocket;
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
            this.serverSocket = new ServerSocket(this.port);
            while (this.flag) {
                Socket socket = serverSocket.accept();
                if(!threadPool.isShutdown())
                    threadPool.execute(new SocketProcess(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("Bye~");
    }

    public void destory() {
        this.flag = false;
        this.threadPool.shutdownNow();
        try {
            Socket socket = new Socket("localhost", this.port);
            this.serverSocket.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
