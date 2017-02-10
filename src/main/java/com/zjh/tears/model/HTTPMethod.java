package com.zjh.tears.model;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public enum HTTPMethod {
    GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, CONNECT;

    public static HTTPMethod parse(String method) {
        switch (method.toUpperCase()) {
            case "GET":
                return GET;
            case "POST":
                return POST;
            case "PUT":
                return PUT;
            case "DELETE":
                return DELETE;
            case "HEAD":
                return HEAD;
            case "OPTIONS":
                return OPTIONS;
            case "TRACE":
                return TRACE;
            case "CONNECT":
                return CONNECT;
            default:
                return GET;
        }
    }

}
