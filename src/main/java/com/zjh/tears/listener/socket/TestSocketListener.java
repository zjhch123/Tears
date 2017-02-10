package com.zjh.tears.listener.socket;

import com.zjh.tears.model.SocketObject;

/**
 * Created by zhangjiahao on 2017/2/10.
 */
public class TestSocketListener extends SocketListener {
    @Override
    public void preHandler(SocketObject socketObject) {
        System.out.println("test socket listener pre");
    }

    @Override
    public void afterHandler(SocketObject socketObject) {
        System.out.println("test socket listener after");
    }
}
