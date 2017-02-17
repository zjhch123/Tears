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
     *  这mapping也太不优雅了
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


    private void setResponseHeaders(Request req, Response res, HTTPRequest request) {
        Map<String, List<String>> headersFromPorxy = request.getResponseHeader();
        headersFromPorxy.forEach((key, value) -> {
            if (key != null) {
                res.setHeader(key, value);
            }
        });

        if(headersFromPorxy.containsKey("Set-Cookie")) {
            List<String> cookies = headersFromPorxy.get("Set-Cookie");
            this.mappingCookie(req, res, cookies);
        }
    }

    private void mappingCookie(Request req, Response res, List<String> cookies) {
        List<String> mappingCookie = new ArrayList<>();
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
                mappingCookie.add(cookieStr);
            } else {
                mappingCookie.add(cookie);
            }
        }
        res.setHeader("Set-Cookie", mappingCookie);
    }

}
