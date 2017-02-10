package com.zjh.tears.filter.socket;

import com.zjh.tears.exception.FilterException;
import com.zjh.tears.filter.Filter;
import com.zjh.tears.model.SocketObject;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public abstract class SocketFilter implements Filter {

    private SocketFilter next;

    public SocketFilter getNext() {
        return this.next;
    }

    public void setNext(SocketFilter next) {
        this.next = next;
    }

    public void doFilter(SocketObject socketObject) throws FilterException {
        this.execute(socketObject);
        if(this.getNext() != null) {
            this.getNext().doFilter(socketObject);
        }
    }

    public abstract void execute(SocketObject socketObject) throws FilterException;
}
