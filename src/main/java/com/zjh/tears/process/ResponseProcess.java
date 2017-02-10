package com.zjh.tears.process;

import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.factory.ErrorResponseFactory;
import com.zjh.tears.manager.HTTPHandlerChainManager;
import com.zjh.tears.model.Response;
import com.zjh.tears.model.Request;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class ResponseProcess {

    private HTTPHandlerChainManager httpHandlerChainManager;
    private ErrorResponseFactory errorResponseFactory;

    public ResponseProcess() {
        this.httpHandlerChainManager = new HTTPHandlerChainManager();
        errorResponseFactory = ErrorResponseFactory.getInstance();
    }

    public Response getResponse(Request req) {
        Response response = new Response();
        try {
            httpHandlerChainManager.doWithRequest(req, response);
        } catch (HTTPException e) {
            response = errorResponseFactory.createErrorPage(e, req);
        } catch (Exception e) {
            response = errorResponseFactory.create500ErrorPage(req);
        }
        return response;
    }

}
