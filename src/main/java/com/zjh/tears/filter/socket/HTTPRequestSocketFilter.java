package com.zjh.tears.filter.socket;

import com.zjh.tears.exception.FilterException;
import com.zjh.tears.model.SocketObject;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class HTTPRequestSocketFilter extends SocketFilter {
    @Override
    public void execute(SocketObject socketObject) throws FilterException {
        if(socketObject.getRequestSource() == null) {
            throw new FilterException();
        }
        String firstLine = socketObject.getRequestSource().split("\n")[0];
        if(firstLine == null) {
            throw new FilterException();
        }
        if(firstLine.split(" ").length != 3) {
            throw new FilterException();
        }
    }
}
