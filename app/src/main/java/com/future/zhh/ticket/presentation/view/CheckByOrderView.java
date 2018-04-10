package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.CheckByOrderInfo;

/**
 * Created by Administrator on 2017/12/8.
 */

public interface CheckByOrderView extends LoadingView {
    void retCheckByOrderView(boolean isSuccess, CheckByOrderInfo result, String msg);
}
