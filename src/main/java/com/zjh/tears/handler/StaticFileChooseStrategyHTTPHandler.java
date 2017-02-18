package com.zjh.tears.handler;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangjiahao on 2017/2/10.
 */
public class StaticFileChooseStrategyHTTPHandler extends HTTPHandler {
    @Override
    public void doWithRequest(Request req, Response res) throws HTTPException {
        if(Config.REVERSE_PROXY_USAGE) {
            for(String key : Config.REVERSE_PROXY_MAPPING.keySet()) {
                Pattern p = Pattern.compile(key);
                Matcher m = p.matcher(req.getRequestPath());
                System.out.println(key);
                System.out.println(req.getRequestPath());
                if(m.matches()) {
                    req.setReverseProxy(Config.REVERSE_PROXY_MAPPING.get(key));
                    req.setHandlerStrategyName("ReverseProxy");
                    return;
                }
            }
        }
        if(res.getCode() == 200) {
            req.setHandlerStrategyName("200");
        } else if(res.getCode() == 206) {
            req.setHandlerStrategyName("206");
        } else {
            req.setHandlerStrategyName("200");
        }
    }
}
