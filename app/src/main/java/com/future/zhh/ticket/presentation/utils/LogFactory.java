package com.future.zhh.ticket.presentation.utils;

import com.future.zhh.ticket.presentation.ApplicationDatas;

/**
 * Created by Administrator on 2017/11/19.
 */

public class LogFactory {
    public static final String TAG = ApplicationDatas.APP_NAME;

    public static CommonLog createLog() {
        CommonLog log = null;
        if (log == null) {
            log = new CommonLog();
        }

        log.setTag(TAG);
        return log;
    }

    public static CommonLog createLog(String tag) {
        CommonLog log = null;
        if (log == null) {
            log = new CommonLog();
        }

        if (tag == null || tag.length() < 1) {
            log.setTag(TAG);
        } else {
            log.setTag(tag);
        }
        return log;
    }
}

