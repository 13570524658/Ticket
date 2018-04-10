package com.future.zhh.ticket.domain.executor;

import rx.Scheduler;

/**
 * Created by Administrator on 2017/11/19.
 */

public interface PostExecutionThread {
    Scheduler getScheduler();
}
