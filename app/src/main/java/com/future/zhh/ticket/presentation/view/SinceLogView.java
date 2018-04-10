package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.SinceLogInfo;
import com.future.zhh.ticket.domain.model.SinceLogListInfo;



/**
 * Created by Administrator on 2017/12/5.
 */

public interface SinceLogView extends LoadingView {
    void  retSinceLogView(boolean isSuccess, ListResult< SinceLogListInfo> result, String msg);
}
