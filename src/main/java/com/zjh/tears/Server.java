package com.zjh.tears;

import com.zjh.tears.config.Config;
import com.zjh.tears.process.SocketProcess;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
                socket.setSoTimeout(Config.TIMEOUT);
                if(!threadPool.isShutdown())
                    threadPool.execute(new SocketProcess(socket));
            }
        } catch (java.net.SocketException e) {
            // Todo 没想好怎么处理
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("Bye~");
    }

    public void destory() {
        this.flag = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.threadPool.shutdown();
        try {
            if(!this.threadPool.awaitTermination(10, TimeUnit.MILLISECONDS)) {
                this.threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            this.threadPool.shutdownNow();
        }
    }
}
