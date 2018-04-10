package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.CheckLogDetailInfo;

/**
 * Created by Administrator on 2017/12/12.
 */

public interface CheckLogDetailView extends LoadingView {
    void retCheckLogDetailView(boolean isSuccess, CheckLogDetailInfo result, String msg);
}
