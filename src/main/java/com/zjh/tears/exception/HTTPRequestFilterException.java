package com.zjh.tears.exception;

/**
 * Created by zhangjiahao on 2017/2/12.
 */
public class HTTPRequestFilterException extends FilterException {
    public HTTPRequestFilterException() {
        // 这里日志记的是message，不是stackTrace
        super("Get a request but it is not a HTTP request");
    }
}
