package com.zjh.tears.listener.socket;

import com.zjh.tears.exception.FilterException;
import com.zjh.tears.model.SocketObject;
import com.zjh.tears.process.HTTPProcess;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class SocketListenerChain {

    private SocketListener header;

    public SocketListenerChain(SocketListener header) {
        this.header = header;
    }

    public SocketListener getHeader() {
        return this.header;
    }

    public byte[] execute(SocketObject socketObject, HTTPProcess target) throws FilterException {
        return header.doHandler(socketObject, target);
    }

    public void destory() {
        this.header = null;
    }

}
