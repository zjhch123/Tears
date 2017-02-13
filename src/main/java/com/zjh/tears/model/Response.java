package com.zjh.tears.model;

import com.zjh.tears.util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class Response {
    private String version;
    private int code;
    private String message;
    private Map<String, List<String>> headers = new HashMap<>();
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
        headers.forEach((key, value) -> {
            if(value.size() == 1) {
                sb.append(key + ":" + value.get(0) + "\r\n");
            } else {
                for(String val : value) {
                    sb.append(key + ":" + val + "\r\n");
                }
            }
        });
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

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    /**
     * setHeader会覆盖原有的Header
     */
    public void setHeader(String key, String ... values) {
        this.setHeader(key, Arrays.asList(values));
    }

    /**
     * setHeader会覆盖原有的Header
     */
    public void setHeader(String key, List<String> values) {
        if(this.headers.containsKey(key)) {
            this.headers.replace(key, values);
        } else {
            this.headers.put(key, values);
        }
    }

    /**
     * addHeader会在已有Header的基础上增加
     */
    public void addHeader(String key, String ... values) {
        this.addHeader(key, Arrays.asList(values));
    }

    /**
     * addHeader会在已有Header的基础上增加
     */
    public void addHeader(String key, List<String> values) {
        if(this.headers.containsKey(key)) {
            this.headers.get(key).addAll(values);
        } else {
            this.headers.put(key, values);
        }
    }

}
