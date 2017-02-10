package com.zjh.tears.handler;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import com.zjh.tears.util.Util;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class ResponseHeaderHTTPHandler extends HTTPHandler {
    @Override
    public void doWithRequest(Request req, Response res) throws HTTPException {
        res.getHeaders().put("Content-Length", String.valueOf(res.getBody().length));
        res.getHeaders().put("Server", Config.SERVER_NAME);
        res.getHeaders().put("Date", Util.getGMTString());
        res.getHeaders().put("Accept-Ranges", "bytes");
        res.getHeaders().put("Connection", "close");
    }
}
