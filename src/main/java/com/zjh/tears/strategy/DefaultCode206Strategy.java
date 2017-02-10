package com.zjh.tears.strategy;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.exception.ServerException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import com.zjh.tears.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by zhangjiahao on 2017/2/9.
 */
public class DefaultCode206Strategy implements HTTPStrategy {
    @Override
    public void doWithStrategy(Request req, Response res) throws HTTPException {
        File file = new File(req.getRealPath());
        FileInputStream fis = null;
        try {
            String[] range = req.getHeaders().get("Range").split("=")[1].split("-");
            long start = Long.valueOf(range[0]);
            long end;
            try {
                end = Long.valueOf(range[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                end = file.length() - 1;
            }
            int size = (int) (end - start + 1);
            byte[] body = new byte[size];
            fis = new FileInputStream(file);
            fis.skip(start);
            fis.read(body);
            res.setBody(body);
            res.getHeaders().put("Content-Range", "bytes " + start + "-" + end + "/" + file.length());
            res.getHeaders().put("Content-Type", Util.getContentType(file));
            res.getHeaders().put("Last-Modified", Util.getGMTString(new Date(file.lastModified())));
        } catch (IOException e) {
            throw new ServerException();
        } finally {
            Util.closeFileInputStream(fis);
        }
    }
}
