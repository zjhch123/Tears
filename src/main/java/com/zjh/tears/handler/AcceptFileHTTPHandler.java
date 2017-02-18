package com.zjh.tears.handler;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.exception.PermissionException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by zhangjiahao on 2017/2/11.
 */
public class AcceptFileHTTPHandler extends HTTPHandler {
    @Override
    public void doWithRequest(Request req, Response res) throws HTTPException {
        if(Config.ACCEPT_CONFIG_USAGE) {
            boolean accept = false;
            String path = req.getRequestPath();
            for(Pattern p : Config.ACCEPT_FILE) {
                if(p.matcher(path).matches()) {
                    accept = true;
                    break;
                }
            }
            for(Pattern p : Config.EXCEPT_FILE) {
                if(p.matcher(path).matches()) {
                    accept = false;
                    break;
                }
            }
            if(!accept) {
                throw new PermissionException();
            }
        }
    }
}
