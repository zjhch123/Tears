package com.zjh.tears.factory;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import com.zjh.tears.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.io.File;


/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class ErrorResponseFactory {

    private ErrorResponseFactory() {}

    private static final class Holder {
        public static ErrorResponseFactory instance = new ErrorResponseFactory();
    }

    public static ErrorResponseFactory getInstance() {
        return Holder.instance;
    }

    private void setDefaultHeaders(Request req, Response res) {
        res.getHeaders().put("Content-Length", String.valueOf(res.getBody().length) + "; charset=" + Config.DEFAULT_CHARSET);
        res.getHeaders().put("Server", Config.SERVER_NAME);
        res.getHeaders().put("Content-Type", "text/html; charset=" + Config.DEFAULT_CHARSET);
        res.getHeaders().put("Date", Util.getGMTString());
        res.getHeaders().put("Accept-Ranges", "bytes");
        res.getHeaders().put("Connection", "close");
    }

    public Response createErrorPage(Request req, int code, String message, String file) {
        Response res = new Response();
        res.setVersion(req.getVersion());
        res.setMessage(message);
        res.setCode(code);
        byte[] resByte;
        try {
            resByte = Files.readAllBytes(new File(Config.STATIC_ROOT_FILE + File.separator + file).toPath());
        } catch (IOException e) {
            resByte = ("This is default " + code + " Error Page, Please set your custom Error Page").getBytes();
        }
        res.setBody(resByte);
        this.setDefaultHeaders(req, res);
        return res;
    }

    public Response createErrorPage(HTTPException e, Request req) {
        return createErrorPage(req, e.getCode(), e.getMessage(), Config.ERR_PAGES.getOrDefault(e.getCode(), e.getCode() + ".html"));
    }

    public Response create500ErrorPage(Request req) {
        return createErrorPage(req, 500, "Server Error", Config.ERR_PAGES.getOrDefault(500, "500.html"));
    }

}
