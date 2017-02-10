package com.zjh.tears.exception;


/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class NotFoundException extends HTTPException {
    public NotFoundException() {
        super(404, "Not Found");
    }
}
