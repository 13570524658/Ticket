package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.GasInfo;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface GasMsgByLabelView extends LoadingView {
    void retGasMsgByLabelView(boolean isSuccess, GasInfo result, String msg);
}
