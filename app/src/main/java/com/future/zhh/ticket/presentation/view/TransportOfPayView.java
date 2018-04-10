package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.Result;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface TransportOfPayView extends LoadingView {
    void retTransportOfPayView(boolean isSuccess, Result result, String msg);
}
