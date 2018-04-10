package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.SetUpLabelInfo;

/**
 * Created by Administrator on 2017/12/29.
 */

public interface SetUpScanView extends LoadingView {
    void  retSetUpScanView(boolean isSuccess, SetUpLabelInfo result, String msg);
}
