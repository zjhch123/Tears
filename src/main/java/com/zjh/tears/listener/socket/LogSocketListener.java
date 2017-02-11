package com.zjh.tears.listener.socket;

import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import com.zjh.tears.model.SocketObject;
import org.apache.log4j.Logger;

/**
 * Created by zhangjiahao on 2017/2/10.
 */
public class LogSocketListener extends SocketListener {

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void afterHandler(SocketObject socketObject) {
        Request request = socketObject.getRequest();
        Response response = socketObject.getResponse();
        logger.info(request.getMethod() + " " + request.getPath() + " " + response.getCode() + " " + response.getMessage());
    }
}
