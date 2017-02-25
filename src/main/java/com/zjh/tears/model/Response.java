package com.zjh.tears.model;

import com.zjh.tears.util.Util;

import java.util.ArrayList;
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
    private Map<String, ArrayList<String>> headers = new HashMap<>();
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

    public Map<String, ArrayList<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, ArrayList<String>> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    /**
     * addHeader会在原有的基础上增加
     */
    public void addHeader(String key, String ... values) {
        this.addHeader(key, Arrays.asList(values));
    }

    public void addHeader(String key, List<String> values) {
        this.addHeader(key, new ArrayList<>(values));
    }

    public void addHeader(String key, ArrayList<String> values) {
        if(this.headers.containsKey(key)) {
            this.headers.get(key).addAll(values);
        } else {
            this.setHeader(key, values);
        }
    }

    /**
     * setHeader会覆盖原有的Header
     */
    public void setHeader(String key, String ... values) {
        this.setHeader(key, Arrays.asList(values));
    }


    public void setHeader(String key, List<String> values) {
        this.setHeader(key, new ArrayList<>(values));
    }
    /**
     * setHeader会覆盖原有的Header
     */
    public void setHeader(String key, ArrayList<String> values) {
        if(this.headers.containsKey(key)) {
            this.headers.replace(key, values);
        } else {
            this.headers.put(key, values);
        }
    }

    public void destory() {
        if(headers != null) {
            headers.clear();
            headers = null;
        }
    }

}
