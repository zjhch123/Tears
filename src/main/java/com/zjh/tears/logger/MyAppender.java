package com.zjh.tears.logger;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**
 * Created by zhangjiahao on 2017/2/11.
 */
public class MyAppender extends DailyRollingFileAppender {
    public boolean isAsSevereAsThreshold(Priority priority) {
        //只判断是否相等，而不判断优先级
        return this.getThreshold().equals(priority);
    }
}
