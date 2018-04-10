package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.CheckByCustomercardNoInfo;

/**
 * Created by Administrator on 2017/12/7.
 */

public interface CheckByCustomercardNoView extends LoadingView {
    void retCheckByCustomercardNoView(boolean isSuccess, CheckByCustomercardNoInfo result, String msg);
}
