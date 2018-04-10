package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.TransportTaskDetailInfo;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface TransportTaskDetailView extends LoadingView {
    void  retTransportTaskDetailView(boolean isSuccess, TransportTaskDetailInfo result, String msg);
}
