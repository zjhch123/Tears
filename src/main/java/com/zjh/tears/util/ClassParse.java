package com.zjh.tears.util;

import com.zjh.tears.exception.ServerException;

/**
 * Created by zhangjiahao on 2017/2/9.
 */
public class ClassParse {

    private ClassParse() {
    }

    public static ClassParse getInstance() {
        return ClassHandler.instance;
    }

    private final static class ClassHandler {
        private static final ClassParse instance = new ClassParse();
    }

    public Object getObject(String name) throws Exception {
        Object obj = null;
        Class c = Class.forName(name);
        obj = c.newInstance();
        return obj;
    }

}
