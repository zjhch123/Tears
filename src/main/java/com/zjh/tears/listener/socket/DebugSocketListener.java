package com.zjh.tears.listener.socket;

import com.zjh.tears.model.SocketObject;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class DebugSocketListener extends SocketListener {
    @Override
    public void preHandler(SocketObject socketObject) {
        System.out.println("*** request ***\n" + socketObject.getRequest().getRequestSource());
    }

    @Override
    public void afterHandler(SocketObject socketObject) {
        System.out.println("*** response ***\n" + new String(socketObject.getResponseSource()));
    }
}
