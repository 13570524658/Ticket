package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.Result;

/**
 * Created by Administrator on 2017/12/4.
 */

public interface SinceOfReclaimView extends LoadingView {
    void  retSinceOfReclaimView(boolean isSuccess, Result result, String msg);
}
