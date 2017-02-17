package com.zjh.tears.model;

import com.zjh.tears.config.Config;

import java.util.Map;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class Request {

    private String requestIP;

    private String requestSource;
    private HTTPMethod method;
    private String path; // 这个path是带参数的
    private String realPath; // 这个path是不带参数的
    private String version;
    private Map<String, String> headers;
    private String body;

    private String handlerStrategyName;

    private ReverseProxy reverseProxy;


    @Override
    public String toString() {
        return "Request{" + "\n" +
                "requestSource='" + requestSource + '\'' + "\n" +
                ", method='" + method + '\'' + "\n" +
                ", path='" + path + '\'' + "\n" +
                ", version='" + version + '\'' + "\n" +
                ", headers=" + headers + "\n" +
                ", body='" + body + '\'' + "\n" +
                '}';
    }

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    public HTTPMethod getMethod() {
        return method;
    }

    public void setMethod(HTTPMethod method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        this.setRealPath(Config.STATIC_ROOT_FILE + (this.path.contains("?") ? path.split("\\?")[0] : path));
    }

    public String getRealPath() {return this.realPath;}

    public void setRealPath(String realPath) {this.realPath = realPath;}

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHandlerStrategyName() {
        return handlerStrategyName;
    }

    public void setHandlerStrategyName(String handlerStrategyName) {
        this.handlerStrategyName = handlerStrategyName;
    }


    public String getRequestIP() {
        return requestIP;
    }

    public void setRequestIP(String requestIP) {
        this.requestIP = requestIP;
    }

    public ReverseProxy getReverseProxy() {
        return reverseProxy;
    }

    public void setReverseProxy(ReverseProxy reverseProxy) {
        this.reverseProxy = reverseProxy;
    }

    public void destory() {
        if(headers != null) {
            headers.clear();
            headers = null;
        }
    }
}
