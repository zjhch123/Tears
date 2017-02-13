package com.zjh.tears.strategy;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import com.zjh.tears.util.Util;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjiahao on 2017/2/12.
 */
public class ReverseProxyStrategy implements HTTPStrategy {

    String proxyTarget = "http://localhost:8888/temp"; // 假装是从配置文件里读来的

    @Override
    public void doWithStrategy(Request req, Response res) throws HTTPException {
        String target = proxyTarget + req.getPath();
        HttpURLConnection conn = null;
        OutputStream os = null;
        BufferedReader br = null;
        try {
            URL url = new URL(target);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            os = conn.getOutputStream();
            os.write(req.getRequestSource().getBytes());
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Config.DEFAULT_CHARSET));
            String line;
            StringBuilder sb = new StringBuilder();
            byte[] body;
            while((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            body = sb.toString().getBytes();
            res.setBody(body);
            this.setResponseHeaders(req, res, conn);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.closeBr(br);
            this.closeOs(os);
            this.closeConn(conn);
        }
    }

    private void setResponseHeaders(Request req, Response res, HttpURLConnection conn) {
        Map<String, List<String>> headersFromPorxy = conn.getHeaderFields();
        headersFromPorxy.forEach((key, value) -> {
            if (key != null) {
                res.setHeader(key, value);
            }
        });

        if(headersFromPorxy.containsKey("Content-Type")) {
            String contentType = conn.getHeaderField("Content-Type");
            if (contentType.contains("charset")) {
                contentType = contentType.substring(0, contentType.indexOf(";charset"));
            }
            res.setHeader("Content-Type", contentType + "; charset=UTF-8");
        } else {
            res.setHeader("Content-Type", "text/html; charset=UTF-8");
        }

        if(headersFromPorxy.containsKey("Set-Cookie")) {
            List<String> cookies = conn.getHeaderFields().get("Set-Cookie");
            res.setHeader("Set-Cookie", cookies);
        }
    }

    private void closeConn(HttpURLConnection conn) {
        if(conn != null) {
            conn.disconnect();
        }
    }

    private void closeBr(BufferedReader br) {
        if(br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeOs(OutputStream os) {
        if(os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
