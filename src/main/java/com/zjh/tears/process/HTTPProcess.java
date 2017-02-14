package com.zjh.tears.process;

import com.zjh.tears.exception.FilterException;
import com.zjh.tears.factory.RequestFactory;
import com.zjh.tears.manager.SocketChainManager;
import com.zjh.tears.model.Request;
import com.zjh.tears.model.Response;
import com.zjh.tears.model.SocketObject;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class HTTPProcess {

    private SocketChainManager socketChainManager;
    private ResponseProcess responseProcess;

    public HTTPProcess() {
        this.responseProcess = new ResponseProcess();
    }

    public void setSocketChainManager(SocketChainManager socketChainManager) {
        this.socketChainManager = socketChainManager;
    }

    public byte[] getResponse(SocketObject socketObject) throws FilterException {
        return socketChainManager.execute(socketObject);
    }

    public byte[] process(SocketObject socketObject) {
        Request request = RequestFactory.getInstance().create(socketObject);
        Response response = this.responseProcess.getResponse(request);
        socketObject.setResponse(response);
        return socketObject.getResponse().getBytes();
    }

    public void destory() {
        if(socketChainManager != null) {
            socketChainManager.destory();
            socketChainManager = null;
        }
        if(responseProcess != null) {
            responseProcess.destory();
            responseProcess = null;
        }
    }

}
