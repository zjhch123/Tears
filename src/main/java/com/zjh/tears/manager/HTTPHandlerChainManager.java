package com.zjh.tears.manager;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.handler.HTTPHandlerChain;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class HTTPHandlerChainManager {

    private HTTPHandlerChain httpHandlerChain;

    public HTTPHandlerChainManager() {
        this.httpHandlerChain = new HTTPHandlerChain(Config.HTTP_HANDLER_HEADER);
    }

    public void doWithRequest(Request req, Response res) throws HTTPException {
        this.httpHandlerChain.doWithRequest(req, res);
    }

    public void destory() {
        if(httpHandlerChain != null) {
            httpHandlerChain.destory();
            httpHandlerChain = null;
        }
    }

}