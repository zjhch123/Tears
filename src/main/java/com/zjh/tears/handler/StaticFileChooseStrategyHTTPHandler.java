package com.zjh.tears.handler;

import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;

/**
 * Created by zhangjiahao on 2017/2/10.
 */
public class StaticFileChooseStrategyHTTPHandler extends HTTPHandler {
    @Override
    public void doWithRequest(Request req, Response res) throws HTTPException {
        if(res.getCode() == 200) {
            req.setHandlerStrategyName("200");
        } else if(res.getCode() == 206) {
            req.setHandlerStrategyName("206");
        } else {
            req.setHandlerStrategyName("200");
        }
    }
}
