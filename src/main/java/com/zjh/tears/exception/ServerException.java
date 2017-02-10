package com.zjh.tears.exception;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class ServerException extends HTTPException {
    public ServerException() {
        super(500, "Server Error");
    }
}
