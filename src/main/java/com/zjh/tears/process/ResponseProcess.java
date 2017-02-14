package com.zjh.tears.process;

import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.factory.ErrorResponseFactory;
import com.zjh.tears.manager.HTTPHandlerChainManager;
import com.zjh.tears.model.Response;
import com.zjh.tears.model.Request;
import com.zjh.tears.util.Util;
import org.apache.log4j.Logger;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class ResponseProcess {

    private Logger logger = Logger.getLogger(this.getClass());
    private HTTPHandlerChainManager httpHandlerChainManager;

    public ResponseProcess() {
        this.httpHandlerChainManager = new HTTPHandlerChainManager();
    }

    public Response getResponse(Request req) {
        Response response = new Response();
        try {
            httpHandlerChainManager.doWithRequest(req, response);
        } catch (HTTPException e) {
            response = ErrorResponseFactory.getInstance().createErrorPage(e, req);
            logger.warn(Util.stackTraceToString(e));
        } catch (Exception e) {
            response = ErrorResponseFactory.getInstance().create500ErrorPage(req);
            logger.warn(Util.stackTraceToString(e));
        }
        return response;
    }

    public void destory() {
        if(httpHandlerChainManager != null) {
            httpHandlerChainManager.destory();
            httpHandlerChainManager = null;
        }
    }

}
