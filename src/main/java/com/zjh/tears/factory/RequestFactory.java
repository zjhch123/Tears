package com.zjh.tears.factory;


import com.zjh.tears.model.HTTPMethod;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.SocketObject;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class RequestFactory {

    private RequestFactory(){}

    private final static class Handler {
        public static final RequestFactory instance = new RequestFactory();
    }

    public static RequestFactory getInstance() {
        return Handler.instance;
    }

    private void initFirstLine(String line, Request req) {
        String[] args = line.split(" ");
        req.setMethod(HTTPMethod.parse(args[0]));
        req.setPath(args[1]);
        req.setVersion(args[2]);
    }

    private void initHeaders(String line, Request req) {
        Map<String, String> headers = new HashMap<>();
        Stream.of(line.split("\n"))
                .map(str -> str.split(":"))
                .forEach(arr -> headers.put(arr[0].trim(), arr[1].trim()));
        req.setHeaders(headers);
    }

    private void initBody(String line, Request req) {
        req.setBody(line);
    }

    public Request create(SocketObject socketObject) {
        Request request = socketObject.getRequest();
        String[] analyStringArray = request.getRequestSource().split("\n");
        String firstLine = analyStringArray[0];
        StringBuilder headersBuilder = new StringBuilder();
        StringBuilder bodyBuilder = new StringBuilder();
        int index;
        for(index = 1; index < analyStringArray.length && analyStringArray[index].length() != 0; index++) {
            headersBuilder.append(analyStringArray[index] + "\n");
        }
        for(;index < analyStringArray.length;index++) {
            bodyBuilder.append(analyStringArray[index]);
        }
        String header = headersBuilder.toString();
        String body = bodyBuilder.toString();
        this.initFirstLine(firstLine, request);
        this.initHeaders(header, request);
        this.initBody(body, request);

        return request;
    }

}
