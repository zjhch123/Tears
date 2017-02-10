package com.zjh.tears.handler;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;

/**
 * Created by zhangjiahao on 2017/2/9.
 */
public class DefaultIndexHTTPHandler extends HTTPHandler {
    @Override
    public void doWithRequest(Request req, Response res) throws HTTPException {
        if(req.getRealPath().equals(Config.STATIC_ROOT_FILE + "/")) {
            req.setRealPath(Config.STATIC_ROOT_FILE + "/" + Config.DEFAULT_INDEX);
        }
    }
}
