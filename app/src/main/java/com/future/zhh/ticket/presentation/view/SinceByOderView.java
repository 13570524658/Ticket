package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.SinceByOrderInfo;

/**
 * Created by Administrator on 2017/12/4.
 */

public interface SinceByOderView extends LoadingView {
    void  retSinceByOderView(boolean isSuccess, SinceByOrderInfo result, String msg);
}
