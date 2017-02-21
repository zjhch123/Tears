package com.zjh.tears;

import com.zjh.tears.config.Config;
import com.zjh.tears.model.SocketObject;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by zhangjiahao on 2017/2/20.
 */
public class ShutdownServer extends Server {

    private Server server;

    public ShutdownServer(int port, Server server) {
        super(port);
        this.server = server;
    }

    @Override
    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.getPort());
            boolean flag = true;
            while(flag) {
                SocketObject shutdown = new SocketObject(serverSocket.accept());
                shutdown.read();
                if("shutdown".equals(shutdown.getRequestSource().replaceAll("\n|\r|\t",""))) {
                    server.destory();
                    shutdown.destory();
                    flag = false;
                    serverSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
