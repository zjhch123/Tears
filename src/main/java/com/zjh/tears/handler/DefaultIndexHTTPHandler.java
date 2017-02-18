package com.zjh.tears.handler;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import java.io.File;
/**
 * Created by zhangjiahao on 2017/2/9.
 */
public class DefaultIndexHTTPHandler extends HTTPHandler {
    @Override
    public void doWithRequest(Request req, Response res) throws HTTPException {
        File requestFile = new File(req.getRealPath());
        if(requestFile.isDirectory() && req.getRealPath().endsWith("/")) {
            for(String file : Config.DEFAULT_INDEX) {
                String path = req.getRealPath() + File.separator + file;
                if(new File(path).exists()) {
                    req.setRealPath(path);
                    req.setRequestPath(req.getRequestPath() + File.separator + file);
                    break;
                }
            }
        }
    }
}
