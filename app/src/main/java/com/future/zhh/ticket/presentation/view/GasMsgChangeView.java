package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.GasMsgChangeInfo;
import com.future.zhh.ticket.domain.model.Result;

/**
 * Created by Administrator on 2017/12/1.
 */

public interface GasMsgChangeView extends LoadingView {
    void retGasMsgChangeView(boolean isSuccess, Result result, String msg);
}
