package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.Result;



/**
 * Created by Administrator on 2017/12/11.
 */

public interface CheckSubmitView extends LoadingView {
    void retCheckSubmitView(boolean isSuccess, Result result, String msg);
}
