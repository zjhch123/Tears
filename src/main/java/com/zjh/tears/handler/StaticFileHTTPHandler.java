package com.zjh.tears.handler;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.exception.ServerException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import com.zjh.tears.strategy.HTTPStrategy;
import com.zjh.tears.util.ClassParse;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class StaticFileHTTPHandler extends HTTPHandler {

    private HTTPStrategy staticFileStrategy;
    private ClassParse parse = ClassParse.getInstance();

    @Override
    public void doWithRequest(Request req, Response res) throws HTTPException {
        try {
            staticFileStrategy = (HTTPStrategy)parse.getObject(Config.STATIC_FILE_STRATEGYS.get(req.getHandlerStrategyName()));
            staticFileStrategy.doWithStrategy(req, res);
        } catch (HTTPException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerException();
        }
    }

}
