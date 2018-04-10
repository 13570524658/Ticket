package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.OrderTaskInfo;

/**
 * Created by Administrator on 2017/11/29.
 */

public interface TransportByOrderQrCodeView extends LoadingView {
    void retOrderMsgByQrCodeView(boolean isSuccess, OrderTaskInfo result, String msg);
}
