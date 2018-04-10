package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.SinceTaskMsgInfo;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface SinceTaskMsgView extends LoadingView {
    void retSinceTaskMsgView(boolean isSuccess, SinceTaskMsgInfo result, String msg);
}
