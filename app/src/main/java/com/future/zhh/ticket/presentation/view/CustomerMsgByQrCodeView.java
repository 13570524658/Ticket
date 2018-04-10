package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.CustomerInfo;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface CustomerMsgByQrCodeView extends LoadingView {
    void retCustomerMsgByQrCodeView(boolean isSuccess, CustomerInfo result, String msg);
}
