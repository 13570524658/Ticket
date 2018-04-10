package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.TransportTaskInfo;
import com.future.zhh.ticket.domain.model.TransportTaskListInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface TransportTaskListView extends LoadingView{
    void retTransportTaskListView(boolean isSuccess, ListResult<TransportTaskInfo> result, String msg);
}
