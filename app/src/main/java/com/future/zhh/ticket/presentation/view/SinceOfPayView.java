package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.Result;


/**
 * Created by Administrator on 2017/12/4.
 */

public interface SinceOfPayView extends LoadingView {
    void  retSinceOfPayView(boolean isSuccess, Result result, String msg);
}
