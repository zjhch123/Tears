package com.zjh.tears.handler;

import com.zjh.tears.exception.HTTPException;
import com.zjh.tears.exception.NotFoundException;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public abstract class HTTPHandler {

    public HTTPHandler next;

    public void setNext(HTTPHandler next) {
        this.next = next;
    }

    public HTTPHandler getNext() {
        return this.next;
    }

    public void execute(Request req, Response res) throws HTTPException {
        this.doWithRequest(req, res);
        if(this.getNext() != null) {
            this.getNext().execute(req, res);
        }
    }

    public abstract void doWithRequest(Request req, Response res) throws HTTPException;
}
