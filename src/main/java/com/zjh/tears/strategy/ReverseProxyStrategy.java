package com.zjh.tears.strategy;

import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import com.zjh.tears.util.HTTPRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangjiahao on 2017/2/12.
 */
public class ReverseProxyStrategy implements HTTPStrategy {

    /**
     *  这mapping也太不优雅了  // 其实蛮优雅的啊！
     */
    @Override
    public void doWithStrategy(Request req, Response res) throws HTTPException {
        String target = req.getReverseProxy().getTarget() + req.getPath();
        HTTPRequest request;
        req.getHeaders().put("X-Forwarded-For", req.getRequestIP());
        if("POST".equals(req.getMethod().name())) {
            request = HTTPRequest.post(target, req.getHeaders(), req.getBody().getBytes());
        } else {
            request = HTTPRequest.get(target, req.getHeaders());
        }

        this.setResponseHeaders(req, res, request);
        res.setBody(request.getBodyByte());
    }


    public void initFirstLine(Response res, String line) {
        String[] firstLine = line.split(" ");
        res.setVersion(firstLine[0]);
        res.setCode(Integer.valueOf(firstLine[1]));
        res.setMessage(firstLine[2]);
    }

    public void mappingHeaders(Response res, Map<String, List<String>> headersFromProxy) {
        headersFromProxy.forEach((key, value) -> {
            if (key != null) {
                res.setHeader(key, value);
            }
        });
    }

    private void mappingCookie(Request req, Response res, List<String> cookies) {
        Map<String, String> cookieMapping = req.getReverseProxy().getCookiesMapping();
        for(final String cookie : cookies) {
            if(cookie.contains("Path")) {
                boolean isHTTPOnly = cookie.contains("HttpOnly");
                String cookieStr = cookie.substring(0, cookie.indexOf("Path="));
                String path = "/";
                Pattern p = Pattern.compile("Path=((.*);\\s?|(.*))");
                Matcher m = p.matcher(cookie);
                while(m.find()) {
                    if(m.group(2) == null) {
                        path = m.group(3);
                    } else {
                        path = m.group(2);
                    }
                }
                String mappingPath = cookieMapping.getOrDefault(path, path);
                cookieStr = cookieStr + "Path=" + mappingPath + (isHTTPOnly ? "; HttpOnly" : "");
                res.addHeader("Set-Cookie", cookieStr);
            } else {
                res.addHeader("Set-Cookie", cookie);
            }
        }
    }

    private void setResponseHeaders(Request req, Response res, HTTPRequest request) {
        String firstLine = request.getResponseHeader().get(null).get(0);
        this.initFirstLine(res, firstLine);

        Map<String, List<String>> headersFromProxy = request.getResponseHeader();
        this.mappingHeaders(res, headersFromProxy);

        if(headersFromProxy.containsKey("Set-Cookie")) {
            List<String> cookies = headersFromProxy.get("Set-Cookie");
            this.mappingCookie(req, res, cookies);
        }
    }

}
