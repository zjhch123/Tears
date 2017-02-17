package com.zjh.tears.handler;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.exception.NotFoundException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;

import java.io.File;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class FileExistHTTPHandler extends HTTPHandler {
    @Override
    public void doWithRequest(Request req, Response res) throws HTTPException {
        File file = new File(req.getRealPath());
        if(!file.exists() || file.isDirectory()) {
            if(!Config.REVERSE_PROXY_USAGE)
                throw new NotFoundException();
        }
    }
}
