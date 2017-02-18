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
import java.util.Date;

/**
 * Created by zhangjiahao on 2017/2/9.
 */
public class DefaultCode200Strategy implements HTTPStrategy {
    @Override
    public void doWithStrategy(Request req, Response res) throws HTTPException {
        try {
            File file = new File(req.getRealPath());
            res.setBody(Files.readAllBytes(file.toPath()));
            res.setHeader("Content-Type", Util.getContentType(file) + "; charset=" + Config.DEFAULT_CHARSET);
            res.setHeader("Last-Modified", Util.getGMTString(new Date(file.lastModified())));
            res.setHeader("Connection", "close");
        } catch(java.nio.file.NoSuchFileException|java.io.FileNotFoundException e) {
            throw new NotFoundException();
        } catch (IOException e) {
            throw new ServerException();
        }
    }
}
