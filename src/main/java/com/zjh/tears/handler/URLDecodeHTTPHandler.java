package com.zjh.tears.handler;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.exception.ServerException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangjiahao on 2017/2/10.
 */
public class URLDecodeHTTPHandler extends HTTPHandler {
    @Override
    public void doWithRequest(Request req, Response res) throws HTTPException {
        String path = req.getRealPath();
        try {
            path = java.net.URLDecoder.decode(path, Config.DEFAULT_CHARSET);
            req.setRealPath(path);
        } catch (UnsupportedEncodingException e) {
            throw new ServerException(e);
        }
    }
}
