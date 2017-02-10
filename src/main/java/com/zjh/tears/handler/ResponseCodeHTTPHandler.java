package com.zjh.tears.handler;

import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;

/**
 * Created by zhangjiahao on 2017/2/9.
 */
public class ResponseCodeHTTPHandler extends HTTPHandler {
    @Override
    public void doWithRequest(Request req, Response res) throws HTTPException {
        res.setVersion(req.getVersion());
        if(isCode206(req)) {
            res.setCode(206);
            res.setMessage("Partial Content");
        } else {
            res.setCode(200);
            res.setMessage("OK");
        }
    }

    public boolean isCode206(Request req) {
        return req.getHeaders().containsKey("Range");
    }
}
