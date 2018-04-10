package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.CustomerCardNoTaskInfo;
import com.future.zhh.ticket.domain.model.OrderTaskInfo;

/**
 * Created by Administrator on 2017/11/29.
 */

public interface TransportByCustomerCardNoView extends LoadingView {
    void retTransportByCustomerCardNoView(boolean isSuccess, CustomerCardNoTaskInfo result, String msg);
}
