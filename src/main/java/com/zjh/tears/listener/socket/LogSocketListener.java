package com.zjh.tears.listener.socket;

import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import com.zjh.tears.model.SocketObject;

/**
 * Created by zhangjiahao on 2017/2/10.
 */
public class LogSocketListener extends SocketListener {
    @Override
    public void afterHandler(SocketObject socketObject) {
        Request request = socketObject.getRequest();
        Response response = socketObject.getResponse();
        System.out.println(request.getMethod() + " " + request.getPath() + " " + response.getCode() + " " + response.getMessage());
    }
}
