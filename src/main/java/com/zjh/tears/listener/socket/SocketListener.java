package com.zjh.tears.listener.socket;

import com.zjh.tears.exception.FilterException;
import com.zjh.tears.model.SocketObject;
import com.zjh.tears.process.HTTPProcess;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public abstract class SocketListener {

    private SocketListener next;

    public void setNext(SocketListener next) {
        this.next = next;
    }

    public SocketListener getNext() {
        return this.next;
    }

    public void preHandler(SocketObject socketObject) {}

    public void afterHandler(SocketObject socketObject) {}

    public byte[] doHandler(SocketObject socketObject, HTTPProcess target) throws FilterException {
        this.preHandler(socketObject);
        byte[] res;
        if(this.getNext() != null) {
            res = this.getNext().doHandler(socketObject, target);
        } else {
            res = target.process(socketObject);
        }
        this.afterHandler(socketObject);
        return res;
    }

}
