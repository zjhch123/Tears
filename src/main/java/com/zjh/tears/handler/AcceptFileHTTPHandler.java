package com.zjh.tears.handler;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.exception.PermissionException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import java.io.File;

/**
 * Created by zhangjiahao on 2017/2/11.
 */
public class AcceptFileHTTPHandler extends HTTPHandler {
    @Override
    public void doWithRequest(Request req, Response res) throws HTTPException {
        if(Config.ACCEPT_CONFIG_USAGE) {
            File requestFile = new File(req.getRealPath());
            if(!Config.ACCEPT_FILE.contains(requestFile) || Config.EXCEPT_FILE.contains(requestFile)) {
                throw new PermissionException();
            }
        }
    }
}
