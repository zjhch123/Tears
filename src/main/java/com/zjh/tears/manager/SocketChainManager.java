package com.zjh.tears.manager;

import com.zjh.tears.config.Config;
import com.zjh.tears.exception.FilterException;
import com.zjh.tears.filter.socket.SocketFilterChain;
import com.zjh.tears.listener.socket.SocketListenerChain;
import com.zjh.tears.model.SocketObject;
import com.zjh.tears.process.HTTPProcess;

/**
 * Created by zhangjiahao on 2017/2/8.
 */
public class SocketChainManager {

    private HTTPProcess target;
    private SocketFilterChain socketFilterChain;
    private SocketListenerChain socketListenerChain;

    public SocketChainManager(HTTPProcess target) {
        this.target = target;
        this.socketFilterChain = new SocketFilterChain(Config.SOCKET_FILTER_HEADER);
        this.socketListenerChain = new SocketListenerChain(Config.SOCKET_LISTENER_HEADER);
    }

    public byte[] execute(SocketObject socketObject) throws FilterException {
        this.socketFilterChain.execute(socketObject);
        return this.socketListenerChain.execute(socketObject, this.target);
    }
}
