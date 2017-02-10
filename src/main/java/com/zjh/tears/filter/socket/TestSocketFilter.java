package com.zjh.tears.filter.socket;

import com.zjh.tears.exception.FilterException;
import com.zjh.tears.model.SocketObject;

/**
 * Created by zhangjiahao on 2017/2/10.
 */
public class TestSocketFilter extends SocketFilter {
    @Override
    public void execute(SocketObject socketObject) throws FilterException {
        System.out.println("testSocketFilter");
    }
}
