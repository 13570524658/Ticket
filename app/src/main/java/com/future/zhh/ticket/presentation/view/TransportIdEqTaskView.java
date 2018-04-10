package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.TransportTaskInfo;
import com.future.zhh.ticket.domain.model.TransportTaskListInfo;

/**
 * Created by Administrator on 2017/11/29.
 */

public interface TransportIdEqTaskView extends LoadingView {
    void retTransportIdEqTaskView(boolean isSuccess, ListResult<TransportTaskInfo> result, String msg);
}
