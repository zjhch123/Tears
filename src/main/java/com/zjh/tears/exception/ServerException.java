package com.zjh.tears.exception;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class ServerException extends HTTPException {
    public ServerException(Exception source) {
        super(source, 500, "Server Error");
    }
}
