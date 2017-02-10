package com.zjh.tears.strategy;

import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;

/**
 * Created by zhangjiahao on 2017/2/9.
 */
public interface HTTPStrategy {
    public void doWithStrategy(Request req, Response res) throws HTTPException;
}
