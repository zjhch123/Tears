package com.zjh.tears.filter.socket;

import com.zjh.tears.exception.FilterException;
import com.zjh.tears.model.SocketObject;
import com.zjh.tears.process.HTTPProcess;


/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class SocketFilterChain {

    private SocketFilter header;

    public SocketFilterChain(SocketFilter header) {
        this.header = header;
    }

    public SocketFilter getHeader() {
        return this.header;
    }

    public void execute(SocketObject socketObject) throws FilterException {
        header.doFilter(socketObject);
    }

    public void destory() {
        this.header = null;
    }

}
