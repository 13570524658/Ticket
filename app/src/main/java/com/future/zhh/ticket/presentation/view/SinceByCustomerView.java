package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.SinceByCustomerInfo;

/**
 * Created by Administrator on 2017/12/4.
 */

public interface SinceByCustomerView extends LoadingView {
    void  retSinceByCustomerView(boolean isSuccess, SinceByCustomerInfo result, String msg);
}
