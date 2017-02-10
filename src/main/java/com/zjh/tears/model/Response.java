package com.zjh.tears.model;

import com.zjh.tears.util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class Response {
    private String version;
    private int code;
    private String message;
    private Map<String, String> headers = new HashMap<>();
    private byte[] body;

    public byte[] getBytes() {
        StringBuilder sb = new StringBuilder();
        sb.append(version + " ");
        sb.append(code + " ");
        sb.append(message + "\r\n");
        sb.append(this.headersToString() + "\r\n");
        return Util.byteMerger(sb.toString().getBytes(), this.body);
    }

    private String headersToString() {
        StringBuilder sb = new StringBuilder();
        headers.forEach((key, value) -> sb.append(key + ":" + value + "\r\n"));
        return sb.toString();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
