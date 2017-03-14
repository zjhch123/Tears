package com.zjh.tears.listener.socket;

import com.zjh.tears.model.SocketObject;

import java.net.Socket;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class DebugSocketListener extends SocketListener {
    @Override
    public void preHandler(SocketObject socketObject) {
        Socket socket = socketObject.getSocket();


    }

}
