package com.zjh.tears;

import com.zjh.tears.config.Config;

/**
 * Created by zhangjiahao on 2017/2/7.
 */
public class Starter {
    public static void main(String[] args) {
        new Server(Config.PORT, Config.THREAD_POOL_SIZE).start();
    }
}
