package com.zjh.tears.handler;

import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class HTTPHandlerChain {

    private HTTPHandler header;

    public HTTPHandlerChain(HTTPHandler header) {
        this.header = header;
    }

    public HTTPHandler getHeader() {
        return this.header;
    }

    public void doWithRequest(Request req, Response res) throws HTTPException {
        header.execute(req, res);
    }

}
