package com.zjh.tears.strategy;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.exception.NotFoundException;
import com.zjh.tears.exception.ServerException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import com.zjh.tears.util.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by zhangjiahao on 2017/2/9.
 */
public class DefaultCode200Strategy implements HTTPStrategy {
    @Override
    public void doWithStrategy(Request req, Response res) throws HTTPException {
        try {
            File file = new File(req.getRealPath());
            if (!file.exists()) {
                throw new NotFoundException(null);
            }
            if(req.getHeaders().containsKey("If-Modified-Since")) {
                this.modified(req, res, file);
            } else {
                this.normal(req, res, file);
            }
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

    public void modified(Request req, Response res, File file) throws IOException {
        String modifiedSince = req.getHeaders().get("If-Modified-Since");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date date = sdf.parse(modifiedSince);
            long lastModify = file.lastModified();
            if(date.getTime() == lastModify) {
                res.setCode(304);
                res.setMessage("Not Modified");
                res.setVersion(req.getVersion());
                res.setHeader("Content-Type", Util.getContentType(file) + "; charset=" + Config.DEFAULT_CHARSET);
                res.setHeader("Connection", "close");
                res.setHeader("Last-Modified", Util.getGMTString(new Date(file.lastModified())));
            }
        } catch (ParseException e) {
            this.normal(req, res, file);
        }
    }

    public void normal(Request req, Response res, File file) throws IOException {
        res.setVersion(req.getVersion());
        res.setMessage("OK");
        res.setCode(200);
        res.setBody(Files.readAllBytes(file.toPath()));
        res.setHeader("Cache-Control", "max-age=3600");
        res.setHeader("Content-Type", Util.getContentType(file) + "; charset=" + Config.DEFAULT_CHARSET);
        res.setHeader("Last-Modified", Util.getGMTString(new Date(file.lastModified())));
        res.setHeader("Connection", "close");
    }

}
