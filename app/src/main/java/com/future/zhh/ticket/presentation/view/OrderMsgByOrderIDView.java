package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.OrderInfo;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface OrderMsgByOrderIDView extends LoadingView {
    void retOrderMsgByOrderIDView(boolean isSuccess, OrderInfo result, String msg);
}
