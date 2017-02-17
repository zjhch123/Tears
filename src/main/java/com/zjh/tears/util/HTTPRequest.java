package com.zjh.tears.util;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.exception.NotFoundException;
import com.zjh.tears.exception.ServerException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjiahao on 2017/2/17.
 */
public class HTTPRequest {

    private int status;
    private Map<String, List<String>> responseHeader;
    private String body;
    private byte[] bodyByte;

    private HTTPRequest() {
    }

    private static HTTPRequest sendRequest(String method, String target, Map<String, String> headers, byte[] data) throws HTTPException {
        HttpURLConnection conn = null;
        BufferedReader br = null;
        OutputStream os = null;
        HTTPRequest request = new HTTPRequest();
        try {
            URL url = new URL(target);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod(method.toUpperCase());

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    conn.setRequestProperty(key, value);
                }
            }

            if (method.toUpperCase().equals("POST")) {
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                os = conn.getOutputStream();
                if (data != null) {
                    os.write(data);
                    os.flush();
                }
            }

            conn.connect();
            request = new HTTPRequest();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Config.DEFAULT_CHARSET));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            request.status = conn.getResponseCode();
            request.responseHeader = conn.getHeaderFields();
            request.body = sb.toString();
            request.bodyByte = sb.toString().getBytes();

            return request;
        } catch (MalformedURLException e) {
            throw new ServerException();
        } catch(FileNotFoundException e) {
            throw new NotFoundException();
        } catch (IOException e) {
            throw new ServerException();
        } finally {
            HTTPRequest.closeBufferedReader(br);
            HTTPRequest.closeConn(conn);
            HTTPRequest.closeOutputStream(os);
        }
    }

    public static HTTPRequest get(String target, Map<String, String> headers) throws HTTPException {
        return HTTPRequest.sendRequest("get", target, headers, null);
    }

    public static HTTPRequest get(String target) throws HTTPException {
        return HTTPRequest.get(target, null);
    }

    public static HTTPRequest post(String target, Map<String, String> headers, byte[] data) throws HTTPException {
        return HTTPRequest.sendRequest("post", target, headers, data);
    }

    public static HTTPRequest post(String target, byte[] data) throws HTTPException {
        return HTTPRequest.post(target, null, data);
    }

    public static HTTPRequest post(String target) throws HTTPException {
        return HTTPRequest.post(target, null, null);
    }

    private static void closeConn(HttpURLConnection conn) {
        if (conn != null) {
            conn.disconnect();
        }
    }

    private static void closeBufferedReader(BufferedReader br) {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeOutputStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, List<String>> getResponseHeader() {
        return responseHeader;
    }

    public String getBody() {
        return body;
    }

    public byte[] getBodyByte() {
        return bodyByte;
    }

    public int getStatus() {
        return status;
    }

}
