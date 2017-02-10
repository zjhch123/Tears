package com.zjh.tears.process;

import com.zjh.tears.exception.FilterException;
import com.zjh.tears.manager.SocketChainManager;
import com.zjh.tears.model.SocketObject;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class SocketProcess {

    private Socket socket;
    private HTTPProcess httpProcess;

    public SocketProcess(Socket socket) {
        this.socket = socket;
        this.httpProcess = new HTTPProcess();
        this.httpProcess.setSocketChainManager(new SocketChainManager(httpProcess));
    }

    public void process() {
        SocketObject socketObject = new SocketObject(this.socket);
        try {
            socketObject.read();
            byte[] res = httpProcess.getResponse(socketObject);
            socketObject.response(res);
        } catch (IOException e){
            e.printStackTrace();
        } catch (FilterException e) {
            socketObject.destory();
        } finally {
            socketObject.destory();
        }
    }

}
