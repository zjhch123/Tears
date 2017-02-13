package com.zjh.tears.process;

import com.zjh.tears.exception.FilterException;
import com.zjh.tears.manager.SocketChainManager;
import com.zjh.tears.model.SocketObject;
import com.zjh.tears.util.Util;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class SocketProcess {

    private Socket socket;
    private HTTPProcess httpProcess;

    private Logger logger = Logger.getLogger(this.getClass());

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
        } catch (java.net.SocketException e) {
            // 这里有时会报Broken pipe错误, 暂时没想好如何解决
        } catch (IOException e){
            logger.warn(Util.stackTraceToString(e));
        } catch (FilterException e) {
            logger.warn(e.getMessage());
        } finally {
            socketObject.destory();
        }
    }

}
