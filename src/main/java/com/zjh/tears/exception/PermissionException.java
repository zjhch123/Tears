package com.zjh.tears.exception;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class PermissionException extends HTTPException {
    public PermissionException() {
        super(403, "Forbidden");
    }
}
