package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.GasLabeReplaceInfo;

/**
 * Created by Administrator on 2017/12/2.
 */

public interface GasLabeReplaceView extends LoadingView {
    void retGasLabeReplaceView(boolean isSuccess, GasLabeReplaceInfo result, String msg);
}
